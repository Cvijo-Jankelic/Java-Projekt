package org.projekt.controllers.adminControllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.projekt.Enum.Role;
import org.projekt.Enum.Status;
import org.projekt.builders.AdminBuilder;
import org.projekt.builders.CommonUserBuilder;
import org.projekt.controllers.commonControllers.LoginController;
import org.projekt.entity.AppUser;
import org.projekt.exceptions.NotAllowedNumberInThisSection;
import org.projekt.exceptions.SameNameException;
import org.projekt.runner.HelloApplication;
import org.projekt.services.LoginService;
import org.projekt.utils.DatabaseUtils;
import org.projekt.utils.FileUtils;
import org.projekt.utils.Promjene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

public class RegisterPageController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ComboBox<Role> roleComboBox;

    private static final Logger logger = LoggerFactory.getLogger(RegisterPageController.class);


    public void initialize(){

        roleComboBox.getItems().addAll(Role.values());

    }

    public void userRegistering() throws NoSuchAlgorithmException {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        Role role = roleComboBox.getValue();
        boolean provjera = false;

        try{
            if (username.matches(".*\\d.*")) {
                throw new NotAllowedNumberInThisSection("Korisničko ime ne smije sadržavati brojeve.");
            }

            LoginService.registerUser(username);
        }catch (SameNameException | IOException | SQLException ex){
            provjera = true;
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }catch (NotAllowedNumberInThisSection ex){
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }


        if(!provjera){
            if(role == Role.COMMON){
                Optional<AppUser> commonUser = Optional.ofNullable(new CommonUserBuilder()
                        .setUsername(username)
                        .setPassword(password)
                        .setRole(role)
                        .createCommonUser());

                if(commonUser.isPresent()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda registriranja");
                    alert.setHeaderText("Registriranje korisnika");
                    alert.setContentText("Jeste li sigurni da se zelite registrirati?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && result.get() == ButtonType.OK){
                        DatabaseUtils.saveUsersToDataBase(commonUser.get());
                        FileUtils.saveUserIntoTextFile(commonUser.get());

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
                    alert.setTitle("Potvrda registriranja");
                    alert.setHeaderText("Registriranje korisnika");
                    alert.setContentText("Jeste li sigurni da se zelite registrirati");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && result.get() == ButtonType.OK){
                        DatabaseUtils.saveUsersToDataBase(adminUser.get());
                        FileUtils.saveUserIntoTextFile(adminUser.get());
                    }
                }

            }
            try{
                HelloApplication m = new HelloApplication();
                m.changeScene("dashboard.fxml");
            }catch (IOException ex){
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }

        }
    }
}
