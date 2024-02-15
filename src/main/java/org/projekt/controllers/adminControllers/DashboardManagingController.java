package org.projekt.controllers.adminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.projekt.Enum.Role;
import org.projekt.entity.AppUser;
import org.projekt.records.SessionUser;
import org.projekt.services.LoginService;
import org.projekt.services.Session;
import org.projekt.utils.Promjene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class DashboardManagingController {
    @FXML
    private ListView<String> listViewPromjene;

    @FXML
    public void initialize() {
        ucitajPromjene();
    }

    private void ucitajPromjene() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("campaignChanges.ser"))) {
            List<Promjene> promjene = (List<Promjene>) ois.readObject();
            for (Promjene promjena : promjene) {
                listViewPromjene.getItems().add(promjena.toString()); // Pretpostavka da Promjena klasa ima override toString metode
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
