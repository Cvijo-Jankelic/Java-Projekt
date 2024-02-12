package org.projekt.controllers.commonControllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.projekt.runner.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {
    @FXML
    private ImageView exit;
    @FXML
    private Label menu;
    @FXML
    private Label menuBack;
    @FXML
    private AnchorPane slider;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        slider.setTranslateX(-176);
        menu.setOnMouseClicked(event ->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) ->{
                menu.setVisible(false);
                menuBack.setVisible(true);
            });

        });

        menuBack.setOnMouseClicked(event ->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) ->{
                menu.setVisible(true);
                menuBack.setVisible(false);
            });

        });
    }

    public void setVisible(boolean visible) {
        slider.setVisible(visible);
    }
    public void showAdsScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ad-screen.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            HelloApplication.getMainStage().setTitle("Ad");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showCampaign(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("campaign.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            HelloApplication.mainStage.setTitle("Campaign");
            HelloApplication.mainStage.setScene(scene);
            HelloApplication.mainStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCompany(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("company.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            HelloApplication.mainStage.setTitle("Company");
            HelloApplication.mainStage.setScene(scene);
            HelloApplication.mainStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showDashboard(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            HelloApplication.mainStage.setTitle("Dashboard");
            HelloApplication.mainStage.setScene(scene);
            HelloApplication.mainStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
