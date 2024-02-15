package org.projekt.controllers.commonControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.projekt.runner.HelloApplication;

import java.io.IOException;

public class StartPageController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;



    public void userLogin(ActionEvent event) throws IOException {
        Stage stage = HelloApplication.mainStage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Company management system");
        stage.setScene(scene);
        stage.show();
    }

    public void userRegister(ActionEvent event) throws IOException {
        Stage stage = HelloApplication.mainStage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registerPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }

}
