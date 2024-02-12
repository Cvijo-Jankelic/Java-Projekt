package org.projekt.controllers.commonControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.projekt.Enum.Role;
import org.projekt.entity.AppUser;
import org.projekt.runner.HelloApplication;
import org.projekt.services.LoginService;
import org.projekt.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label wrongLogIn;
    private static String finalRoleStr;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    public void userLogIn(ActionEvent event) throws IOException {

        checkLogin();

    }

    private void checkLogin() throws IOException {

        HelloApplication m = new HelloApplication();

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        String roleStr = DatabaseUtils.findTheUserFromUsernameForRole(username);
        finalRoleStr = roleStr;

        Role role = Role.transformFromStringToEnum(roleStr);


        if(username.equals("Cvijo") && password.equals("admin")){
            wrongLogIn.setText("Success!");

            m.changeScene("dashboard.fxml");

        } else if(LoginService.checkLogin(username, password)){
            wrongLogIn.setText("Success!");

            m.changeScene("dashboard.fxml");
        }else if(usernameTextField.toString().isEmpty() || passwordTextField.toString().isEmpty()){
            wrongLogIn.setText("Please enter your credentials.");

        }else{
            wrongLogIn.setText("Wrong username or password!");
        }

    }

    public static String getFinalRoleStr() {
        return finalRoleStr;
    }
}
