package org.projekt.controllers.commonControllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.projekt.Enum.Role;
import org.projekt.controllers.adminControllers.DashboardManagingController;
import org.projekt.entity.AppUser;
import org.projekt.entity.Campaign;
import org.projekt.services.Session;
import org.projekt.sort.Top5Campaigns;
import org.projekt.threads.DashboardStageTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController {
    @FXML
    private AnchorPane menuBarCommonContainer;
    @FXML
    private AnchorPane menuBarAdminContainer;
    @FXML
    private BarChart<String, Number> campaignBarChart;

    @FXML
    private void initialize() {

        updateMenuBarVisibility();
        loadBarChartData();

    }

    private void updateMenuBarVisibility() {
        AppUser currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            boolean isAdmin = currentUser.getRole() == Role.ADMIN;
            menuBarAdminContainer.setVisible(isAdmin);
            menuBarAdminContainer.setManaged(isAdmin);
            menuBarCommonContainer.setVisible(!isAdmin);
            menuBarCommonContainer.setManaged(!isAdmin);
        }
    }
    private void loadBarChartData() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Budget");

        List<Campaign> topCampaigns = Top5Campaigns.getTop5Campaigns();

        for (Campaign campaign : topCampaigns) {
            series.getData().add(new XYChart.Data<>(campaign.getName(), campaign.getBudget()));
        }

        campaignBarChart.getData().add(series);
    }

}
