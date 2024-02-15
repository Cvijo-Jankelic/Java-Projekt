package org.projekt.controllers.adminControllers;

import eu.hansolo.tilesfx.events.BoundsEventListener;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.projekt.Enum.Role;
import org.projekt.builders.AdminBuilder;
import org.projekt.builders.CommonUserBuilder;
import org.projekt.entity.AppUser;
import org.projekt.entity.CommonUser;
import org.projekt.exceptions.SameNameException;
import org.projekt.runner.HelloApplication;
import org.projekt.services.LoginService;
import org.projekt.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsersManagingController {
    @FXML
    private ComboBox<CommonUser> userComboBox;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ComboBox<Role> roleComboBox;
    @FXML
    private TableView<AppUser> appUserTableView;
    @FXML
    private TableColumn<AppUser, String> usernameTableColumn;
    @FXML
    private TableColumn<AppUser, String> passwordTableColumn;
    @FXML
    private TableColumn<AppUser, String> roleTableColumn;
    @FXML
    private Label wrongUserNameLabel;

    private static final Logger logger = LoggerFactory.getLogger(RegisterPageController.class);
    private final String userDat = "serializedDat/userSerialized.ser";




    public void initialize(){
        usernameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AppUser, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AppUser, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getUsername());
            }
        });

        passwordTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AppUser, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AppUser, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getPassword());
            }
        });

        roleTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AppUser, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AppUser, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getRole().toString());
            }
        });

        List<AppUser> usersList = DatabaseUtils.getAppUsersFromDataBase();


        List<CommonUser> commonList = usersList.stream()
                .filter(user -> user instanceof CommonUser)
                .map(user -> (CommonUser) user)
                .collect(Collectors.toList());

        ObservableList<AppUser> UserObservableList = FXCollections.observableArrayList(usersList);


        appUserTableView.setItems(UserObservableList);

        ObservableList<CommonUser> commonUsersList = FXCollections.observableArrayList(commonList);

        userComboBox.setItems(commonUsersList);

        roleComboBox.getItems().addAll(Role.values());



    }

    @FXML
    private void deleteUser(ActionEvent event){
        AppUser selectedUser = this.userComboBox.getValue();

        List<AppUser> usersList = DatabaseUtils.getAppUsersFromDataBase();


        if(selectedUser != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda brisanja");
            alert.setHeaderText("Brisanje korisnika");
            alert.setContentText("Jeste li sigurni da želite obrisati odabranog korisnika?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                Optional<AppUser> findUserById = usersList.stream()
                        .filter(user -> user.getId().equals(selectedUser))
                        .findFirst();

                DatabaseUtils.removeUsersFromDataBase(selectedUser);

                List<AppUser> updatedList = DatabaseUtils.getAppUsersFromDataBase();

                ObservableList<AppUser> appUserObservableList = FXCollections.observableArrayList(updatedList);

                appUserTableView.setItems(appUserObservableList);

                Platform.runLater(()->{
                    ObservableList<AppUser> items = appUserTableView.getItems();
                });

            }


        }


    }

    @FXML
    private void addUser(ActionEvent event) throws NoSuchAlgorithmException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        Role role = roleComboBox.getValue();
        Boolean provjera = false;

        try{
            LoginService.registerUser(username);
        }catch (SameNameException | IOException | SQLException ex){
            provjera = true;
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        if(provjera){
            wrongUserNameLabel.setText("Korisnicko ime vec postoji");
        }

        if(!provjera){
            wrongUserNameLabel.setText("");
            if(role == Role.COMMON){
                Optional<AppUser> commonUser = Optional.ofNullable(new CommonUserBuilder()
                        .setUsername(username)
                        .setPassword(password)
                        .setRole(role)
                        .createCommonUser());

                if(commonUser.isPresent()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda dodavanja");
                    alert.setHeaderText("Dodavanje korisnika");
                    alert.setContentText("Jeste li sigurni da želite dodati odabranog korisnika?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && result.get() == ButtonType.OK){
                        DatabaseUtils.saveUsersToDataBase(commonUser.get());
                        Platform.runLater(() -> {
                            ObservableList<AppUser> items = appUserTableView.getItems();
                            items.add(commonUser.get());
                            // commonUserTableView.setItems(items); // Ovo je suvišno ako items već referencira listu unutar TableView-a
                        });
                    }
                }

            }else if(role == Role.ADMIN){
                Optional<AppUser> adminUser = Optional.ofNullable(new AdminBuilder()
                        .setUsername(username)
                        .setPassword(password)
                        .setRole(role)
                        .createAdmin());
                if(adminUser.isPresent()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda dodavanja");
                    alert.setHeaderText("Dodavanje korisnika");
                    alert.setContentText("Jeste li sigurni da želite dodati odabranog korisnika?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && result.get() == ButtonType.OK){
                        DatabaseUtils.saveUsersToDataBase(adminUser.get());
                        Platform.runLater(() -> {
                            ObservableList<AppUser> items = appUserTableView.getItems();
                            items.add(adminUser.get()); // Dodajte korisnika bez upotrebe Optional
                            // commonUserTableView.setItems(items); // Ovo je suvišno ako items već referencira listu unutar TableView-a
                        });
                    }
                }

            }
        }


        List<AppUser> updatedListForComboBox = DatabaseUtils.getAppUsersFromDataBase();


        List<CommonUser> filterCommonUsers = updatedListForComboBox.stream()
                .filter(user -> user instanceof CommonUser)
                .map(user -> (CommonUser) user)
                .collect(Collectors.toList());

        ObservableList<CommonUser> updatedCommonUsersList = FXCollections.observableArrayList(filterCommonUsers);

        Platform.runLater(()->{
            userComboBox.setItems(updatedCommonUsersList);
        });

    }

    @FXML
    private void showUpdateUserScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateUserScreen.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            HelloApplication.getMainStage().setTitle("Update user");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
