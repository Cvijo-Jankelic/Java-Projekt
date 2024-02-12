package org.projekt.controllers.commonControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.projekt.Enum.Status;
import org.projekt.entity.Campaign;
import org.projekt.entity.Company;
import org.projekt.services.FindCompanyByIdForCampaign;
import org.projekt.utils.DatabaseUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CampaignController {
    @FXML
    private TextField campaignNameTextField;
    @FXML
    private ComboBox<Status> statusComboBox;
    @FXML
    private TableView<Campaign> campaignTableView;
    @FXML
    private TableColumn<Campaign, String> nameCampaignTableColumn;
    @FXML
    private TableColumn<Campaign, String> statusTableColumn;
    @FXML
    private TableColumn<Campaign, String> budgetCampaignTableColumn;
    @FXML
    private TableColumn<Campaign, String> startDateTableColumn;
    @FXML
    private TableColumn<Campaign, String> endDateTableColumn;
    @FXML
    private TableColumn<Campaign, String> companyTableColumn;


    public void initialize(){
        nameCampaignTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });
        statusTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getStatus().toString());
            }
        });
        budgetCampaignTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getBudget().toString());
            }
        });
        startDateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getStartDate().toString());
            }
        });
        endDateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getEndDate().toString());
            }
        });

        // ovaj je malo posebniji radi nekih konfiguracija!!!!!!!!!*********************************
        companyTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Campaign, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Campaign, String> param) {
                Optional<Company> company = FindCompanyByIdForCampaign.findCompanyByCampaignId(param.getValue().getCampaignId());
                    return new ReadOnlyStringWrapper(company.get().getCompanyName());
            }
        });

        List<Campaign> campaignList = DatabaseUtils.getCampaignsFromDataBase();
        ObservableList<Campaign> campaignObservableList = FXCollections.observableArrayList(campaignList);

        campaignTableView.setItems(campaignObservableList);

        statusComboBox.getItems().addAll(Status.values());

        searchCampaign();

    }

    public void searchCampaign() {

        List<Campaign> campaignList = DatabaseUtils.getCampaignsFromDataBase();


        String campaignName = campaignNameTextField.getText();
        Status status = statusComboBox.getValue();

        List<Campaign> filteredList = campaignList.stream()
                .filter(campaign -> campaign.getName().contains(campaignName))
                .filter(campaign -> campaign.getStatus() == status)
                .collect(Collectors.toList());

        ObservableList<Campaign> campaignObservableList = FXCollections.observableArrayList(filteredList);
        campaignTableView.setItems(campaignObservableList);


    }

}
