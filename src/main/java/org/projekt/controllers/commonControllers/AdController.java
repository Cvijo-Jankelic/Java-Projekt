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
import org.projekt.entity.Ad;
import org.projekt.entity.Campaign;
import org.projekt.utils.DatabaseUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AdController {
    @FXML
    private TextField adNameTextField;
    @FXML
    private ComboBox<Status> adStatusComboBox;
    @FXML
    private TableView<Ad> adTableView;
    @FXML
    private TableColumn<Ad, String> adNameTableColumn;
    @FXML
    private TableColumn<Ad, String> adStatusTableColumn;
    @FXML
    private TableColumn<Ad, String> adImpressionsTableColumn;
    @FXML
    private TableColumn<Ad, String> adClicksTableColumn;
    @FXML
    private TableColumn<Ad, String> adConversionsTableColumn;
    @FXML
    private ComboBox<Campaign> campaignComboBox;


    public void initialize(){
        adNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ad, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });
        adStatusTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ad, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getStatus().toString());
            }
        });
        adImpressionsTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ad, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getImpressions().toString());
            }
        });
        adClicksTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ad, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getClicks().toString());
            }
        });
        adConversionsTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ad, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getConversions().toString());
            }
        });

        List<Ad> adList = DatabaseUtils.getAdsFromDataBase();
        ObservableList<Ad> adObservableList = FXCollections.observableArrayList(adList);

        adTableView.setItems(adObservableList);

        List<Campaign> campaignList = DatabaseUtils.getCampaignsFromDataBase();
        ObservableList<Campaign> campaignObservableList = FXCollections.observableArrayList(campaignList);

        campaignComboBox.setItems(campaignObservableList);
        adStatusComboBox.getItems().addAll(Status.values());

    }

    public void searchAd(ActionEvent event){

        List<Ad> adList = DatabaseUtils.getAdsFromDataBase();

        String adName = adNameTextField.getText();
        Status status = adStatusComboBox.getValue();
        Integer campaignId = campaignComboBox.getValue().getCampaignId();

        List<Ad> adFilteredList = adList.stream()
                .filter(ad -> ad.getName().contains(adName))
                .filter(ad -> ad.getStatus() == status)
                .filter(ad -> ad.getCampaignId().equals(campaignId))
                .collect(Collectors.toList());

        ObservableList<Ad> adObservableList = FXCollections.observableArrayList(adFilteredList);

        adTableView.setItems(adObservableList);

    }

}
