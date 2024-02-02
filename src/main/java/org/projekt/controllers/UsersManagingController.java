package org.projekt.controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.projekt.Enum.Role;
import org.projekt.builders.AdminBuilder;
import org.projekt.builders.CommonUserBuilder;
import org.projekt.entity.AppUser;
import org.projekt.entity.CommonUser;
import org.projekt.utils.DatabaseUtils;

import java.security.NoSuchAlgorithmException;
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
    private TableView<AppUser> commonUserTableView;
    @FXML
    private TableColumn<AppUser, String> usernameTableColumn;
    @FXML
    private TableColumn<AppUser, String> passwordTableColumn;


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

        List<AppUser> usersList = DatabaseUtils.getAppUsersFromDataBase();

        ObservableList<AppUser> userObservableList = FXCollections.observableArrayList(usersList);

        List<CommonUser> commonList = usersList.stream()
                .filter(user -> user instanceof CommonUser)
                .map(user -> (CommonUser) user)
                .collect(Collectors.toList());

        ObservableList<AppUser> UserObservableList = FXCollections.observableArrayList(usersList);


        commonUserTableView.setItems(UserObservableList);

        ObservableList<CommonUser> commonUsersList = FXCollections.observableArrayList(commonList);

        userComboBox.setItems(commonUsersList);

        roleComboBox.getItems().addAll(Role.values());



    }

    @FXML
    private void deleteUser(ActionEvent event){
        String selectedUser = this.userComboBox.getValue().toString();
        List<AppUser> usersList = DatabaseUtils.getAppUsersFromDataBase();

        Optional<AppUser> findUserById = usersList.stream()
                .filter(user -> user.getId().equals(selectedUser))
                .findFirst();


        DatabaseUtils.removeUsersFromDataBase(selectedUser);

    }

    @FXML
    private void addUser(ActionEvent event) throws NoSuchAlgorithmException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        Role role = roleComboBox.getValue();

        if(role == Role.COMMON){
            AppUser commonUser = new CommonUserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setRole(role)
                    .createCommonUser();
            DatabaseUtils.saveUsersToDataBase(commonUser);

        }else if(role == Role.ADMIN){
            AppUser adminUser = new AdminBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setRole(role)
                    .createAdmin();
            DatabaseUtils.saveUsersToDataBase(adminUser);

        }

    }


}
