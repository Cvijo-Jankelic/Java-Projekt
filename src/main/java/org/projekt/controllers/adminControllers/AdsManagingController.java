package org.projekt.controllers.adminControllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.projekt.Enum.Status;
import org.projekt.builders.AdBuilder;
import org.projekt.entity.Ad;
import org.projekt.entity.Campaign;
import org.projekt.entity.Company;
import org.projekt.utils.DatabaseUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AdsManagingController {
    @FXML
    private TextField adNameTextField;
    @FXML
    private TextField adContentTextField;
    @FXML
    private TextField adTypeTextField;
    @FXML
    private ComboBox<Status> adStatusComboBox;
    @FXML
    private TextField adTargetAudienceTextField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<Campaign> adCampaignComboBox;
    @FXML
    private ComboBox<Ad> adDeleteComboBox;

    private final String adsDat = "serializedDat/adsSerialized.ser";

    public void initialize(){

        adStatusComboBox.getItems().addAll((Status.values()));

        List<Ad> adList = DatabaseUtils.getAdsFromDataBase();
        ObservableList<Ad> adObservableList = FXCollections.observableArrayList(adList);

        List<Campaign> campaignList = DatabaseUtils.getCampaignsFromDataBase();
        ObservableList<Campaign> campaignObservableList = FXCollections.observableArrayList(campaignList);
        adCampaignComboBox.setItems(campaignObservableList);

        adDeleteComboBox.setItems(adObservableList);

    }

    public void addAds(ActionEvent event){


        String adName = adNameTextField.getText();
        String adContent = adContentTextField.getText();
        String type = adTypeTextField.getTypeSelector();
        Status status = adStatusComboBox.getValue();
        String adTargetAudience = adTargetAudienceTextField.getText();
        LocalDateTime adStartDate = startDatePicker.getValue().atStartOfDay();
        LocalDateTime adEndDate = endDatePicker.getValue().atStartOfDay();
        Integer adCampaignId = adCampaignComboBox.getValue().getCampaignId();

        Optional<Ad> newAd = Optional.ofNullable(new AdBuilder()
                .setName(adName)
                .setContent(adContent)
                .setType(type)
                .setStatus(status)
                .setTargetAudience(adTargetAudience)
                .setStartDate(adStartDate)
                .setEndDate(adEndDate)
                .setCampaignId(adCampaignId)
                .createAd());


        if(newAd.isPresent()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda dodavanja");
            alert.setHeaderText("Dodavanje reklame");
            alert.setContentText("Jeste li sigurni da želite dodati odabranu reklamu?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                DatabaseUtils.saveAdsToDataBase(newAd.get());

                List<Ad> updatedList = DatabaseUtils.getAdsFromDataBase();
                ObservableList<Ad> adObservableList = FXCollections.observableArrayList(updatedList);

                Platform.runLater(()->{
                    adDeleteComboBox.setItems(adObservableList);
                });
            }

        }

    }

    public void deleteAd(ActionEvent event){
        Ad selectedAd = this.adDeleteComboBox.getValue();

        if(selectedAd != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda brisanja");
            alert.setHeaderText("Brisanje kampanje");
            alert.setContentText("Jeste li sigurni da želite obrisati odabranu reklamu?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                DatabaseUtils.removeAdFromDataBase(selectedAd);

                List<Ad> updatedAdList = DatabaseUtils.getAdsFromDataBase();
                ObservableList<Ad> adObservableList = FXCollections.observableArrayList(updatedAdList);
                Platform.runLater(()->{
                    adDeleteComboBox.setItems(adObservableList);
                });

            }

        }
    }
}
