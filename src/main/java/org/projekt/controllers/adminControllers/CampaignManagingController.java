package org.projekt.controllers.adminControllers;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.projekt.Enum.Status;
import org.projekt.builders.CampaignBuilder;
import org.projekt.entity.AppUser;
import org.projekt.entity.Campaign;
import org.projekt.entity.Company;
import org.projekt.utils.DatabaseUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CampaignManagingController  {
    @FXML
    private TextField campaignNameTextField;
    @FXML
    private TextField campaignDescriptionTextField;
    @FXML
    private ComboBox<Status> campaignStatusComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField budgetTextField;
    @FXML
    private TextField targetAudienceCampaignTextField;
    @FXML
    private TextField channelsTextField;
    @FXML
    private ComboBox<Company> companyComboBox;
    @FXML
    private ComboBox<Campaign> deleteCampaignComboBox;
    @FXML
    private TableView<Campaign> campaignTableView;
    @FXML
    private TableColumn<Campaign, String> campaignNameTableColumn;
    @FXML
    private TableColumn<Campaign, String> campaignStatusTableColumn;
    @FXML
    private TableColumn<Campaign, String> campaignBudgetTableColumn;
    @FXML
    private TableColumn<Campaign, String> companyCampaignTableColumn;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");


    public void initialize(){
        campaignNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });
        campaignStatusTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getStatus().toString());
            }
        });
        campaignBudgetTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getBudget().toString());
            }
        });
        companyCampaignTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });

        List<Campaign> campaignList = DatabaseUtils.getCampaignsFromDataBase();

        ObservableList<Campaign> campaignObservableList = FXCollections.observableArrayList(campaignList);
        campaignTableView.setItems(campaignObservableList);

        List<Company> companyList = DatabaseUtils.getCompaniesFromDataBase();
        ObservableList<Company> companyObservableList = FXCollections.observableArrayList(companyList);
        companyComboBox.setItems(companyObservableList);

        campaignStatusComboBox.getItems().addAll((Status.values()));

        deleteCampaignComboBox.setItems(campaignObservableList);


    }

    @FXML
    public void addCampaign(ActionEvent event){

        String campaignName = campaignNameTextField.getText();
        String description = campaignDescriptionTextField.getText();
        Status status = campaignStatusComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        BigDecimal budget = new BigDecimal(budgetTextField.getText());
        String targetAudience = targetAudienceCampaignTextField.getText();
        String channels = channelsTextField.getText();
        Integer companyId = companyComboBox.getValue().getCompanyId();

        Optional<Campaign> newCampaign = Optional.ofNullable(new CampaignBuilder()
                .setName(campaignName)
                .setDescription(description)
                .setStatus(status)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setBudget(budget)
                .setTargetAudience(targetAudience)
                .setChannels(channels)
                .setCompanyId(companyId)
                .createCampaign());

        if(newCampaign.isPresent()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda dodavanja");
            alert.setHeaderText("Dodavanje kampanje");
            alert.setContentText("Jeste li sigurni da želite dodati odabranu kampanju?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                DatabaseUtils.saveCampaignToDataBase(newCampaign.get());
                Platform.runLater(()->{
                    ObservableList<Campaign> items = campaignTableView.getItems();
                    items.add(newCampaign.get());
                });
            }
        }

    }

    public void deleteCampaign(ActionEvent action){
        Campaign selectedCampaign = this.deleteCampaignComboBox.getValue();
        List<Campaign> campaignList = DatabaseUtils.getCampaignsFromDataBase();

        if(selectedCampaign != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda brisanja");
            alert.setHeaderText("Brisanje kampanje");
            alert.setContentText("Jeste li sigurni da želite obrisati odabranu kampanju?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                DatabaseUtils.removeCampaignFromDataBase(selectedCampaign);

                List<Campaign> updatedListCampaign = DatabaseUtils.getCampaignsFromDataBase();
                ObservableList<Campaign> campaignObservableList = FXCollections.observableArrayList(updatedListCampaign);

                campaignTableView.setItems(campaignObservableList);

                Platform.runLater(() -> {
                    ObservableList<Campaign> items = campaignTableView.getItems();
                    campaignTableView.setItems(items); // Ovo možda nije potrebno ako items već referencira listu unutar TableView-a
                });

                Platform.runLater(()->{
                    deleteCampaignComboBox.setItems(campaignObservableList);
                });

            }

        }

    }



}
