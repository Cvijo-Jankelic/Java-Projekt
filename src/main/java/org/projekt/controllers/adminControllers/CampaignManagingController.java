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
import org.projekt.services.FindCompanyByIdForCampaign;
import org.projekt.services.Session;
import org.projekt.utils.DatabaseUtils;
import org.projekt.utils.Promjene;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private ComboBox<Campaign> updateCampaignComboBox;
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
    private static final String campaignDat = "serializedDat/campaignSerialized.dat";


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
                Optional<Company> company = FindCompanyByIdForCampaign.findCompanyByCampaignId(param.getValue().getCompanyId());
                return new ReadOnlyStringWrapper(company.map(Company::getCompanyName).orElse("Nepoznato"));
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

        Platform.runLater(()->{
            updateCampaignComboBox.setItems(campaignObservableList);
        });

        campaignTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFormWithCampaignData(newSelection);
            }
        });


    }

    @FXML
    public void addCampaign(ActionEvent event){

        AppUser currentUser = Session.getCurrentUser();

        String campaignName = campaignNameTextField.getText();
        String description = campaignDescriptionTextField.getText();
        Status status = campaignStatusComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        BigDecimal budget = new BigDecimal(budgetTextField.getText());
        String targetAudience = targetAudienceCampaignTextField.getText();
        String channels = channelsTextField.getText();
        Integer companyId = companyComboBox.getValue().getCompanyId();
        Integer idUser = null;
        if(currentUser != null){
            idUser = currentUser.getId();
        }


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
                .setCreatedBy(idUser)
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

    public void updateCampaign() {

        Campaign selectedCampaign = updateCampaignComboBox.getSelectionModel().getSelectedItem();
        Campaign oldCampaign = DatabaseUtils.findCampaignFromDataBase(selectedCampaign.getName());

        if (selectedCampaign != null) {
            // Preuzimanje novih vrijednosti iz formularnih kontrola
            String newName = campaignNameTextField.getText();
            String newDescription = campaignDescriptionTextField.getText();
            Status newStatus = campaignStatusComboBox.getValue();
            LocalDate newStartDate = startDatePicker.getValue();
            LocalDate newEndDate = endDatePicker.getValue();
            BigDecimal newBudget = new BigDecimal(budgetTextField.getText());
            String newTargetAudience = targetAudienceCampaignTextField.getText();
            String newChannels = channelsTextField.getText();
            Company newCompany = companyComboBox.getValue();

            settingNewValues(selectedCampaign, newName, newDescription, newStatus, newStartDate, newEndDate, newBudget, newTargetAudience, newChannels);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda azuriranja");
            alert.setHeaderText("Azuriranje kampanje");
            alert.setContentText("Jeste li sigurni da želite azurirati ovu kampanju?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                LocalDateTime now = LocalDateTime.now();
                String fileName = "serializedDat/campaignChanges.ser";
                Promjene promjena = new Promjene(selectedCampaign.getName(), newName, now, Session.getCurrentUser().getRole());

                        Promjene.serializePromjena(promjena, fileName);
                Campaign newCampaign = new CampaignBuilder().setName(newName)
                        .setCampaignId(selectedCampaign.getCampaignId())
                        .setDescription(newDescription).setStatus(newStatus).setStartDate(newStartDate).setEndDate(newEndDate)
                        .setBudget(newBudget).setTargetAudience(newTargetAudience).setChannels(newChannels).setCompanyId(newCompany.getCompanyId())
                        .createCampaign();
                Promjene.serializeObjects(oldCampaign, newCampaign, campaignDat);

                DatabaseUtils.updateCampaignIntoDatabase(selectedCampaign);

                Platform.runLater(()->{
                    campaignTableView.refresh();
                });

            }

        }
    }

    private static void settingNewValues(Campaign selectedCampaign, String newName, String newDescription, Status newStatus, LocalDate newStartDate, LocalDate newEndDate, BigDecimal newBudget, String newTargetAudience, String newChannels) {
        selectedCampaign.setName(newName);
        selectedCampaign.setDescription(newDescription);
        selectedCampaign.setStatus(newStatus);
        selectedCampaign.setStartDate(newStartDate);
        selectedCampaign.setEndDate(newEndDate);
        selectedCampaign.setBudget(newBudget);
        selectedCampaign.setTargetAudience(newTargetAudience);
        selectedCampaign.setChannels(newChannels);
    }

    private void fillFormWithCampaignData (Campaign campaign){
        campaignNameTextField.setText(campaign.getName());
        campaignDescriptionTextField.setText(campaign.getDescription());
        campaignStatusComboBox.setValue(campaign.getStatus());
        startDatePicker.setValue(campaign.getStartDate());
        endDatePicker.setValue(campaign.getEndDate());
        budgetTextField.setText(campaign.getBudget().toString());
        targetAudienceCampaignTextField.setText(campaign.getTargetAudience());
        channelsTextField.setText(campaign.getChannels());

        companyComboBox.getSelectionModel().select(
                companyComboBox.getItems().stream()
                        .filter(company -> company.getCompanyId().equals(campaign.getCompanyId()))
                        .findFirst()
                        .orElse(null)
        );
    }

}
