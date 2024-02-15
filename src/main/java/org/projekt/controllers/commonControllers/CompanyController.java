package org.projekt.controllers.commonControllers;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.projekt.Enum.Role;
import org.projekt.entity.AppUser;
import org.projekt.entity.Campaign;
import org.projekt.entity.Company;
import org.projekt.services.FindCampingByIdForCompanies;
import org.projekt.services.Session;
import org.projekt.utils.DatabaseUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompanyController {
    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextField companyAddressTextField;
    @FXML
    private TableView<Company> companyTableView;
    @FXML
    private TableColumn<Company, String> companyNameTableColumn;
    @FXML
    private TableColumn<Company, String> companyAddressTableColumn;
    @FXML
    private TableColumn<Company, String> companyContactTableColumn;
    @FXML
    private TableColumn<Company, String> companyCampaignsTableColumn;
    @FXML
    private AnchorPane menuBarCommonContainer;
    @FXML
    private AnchorPane menuBarAdminContainer;


    @FXML
    public void initialize(){

        updateMenuBarVisibility();

        companyNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Company, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getCompanyName());
            }
        });
        companyAddressTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Company, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getCompanyAddress());
            }
        });
        companyContactTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Company, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getCompanyContact());
            }
        });
        companyCampaignsTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Company, String> param) {
                Integer companyId = param.getValue().getCompanyId();

                Optional<List<Campaign>> campaigns = FindCampingByIdForCompanies.findCampaignsByCompanyId(companyId);

                String displayText = campaigns.map(list -> list.stream()
                                .map(Campaign::getName)
                                .collect(Collectors.joining(", ")))
                        .orElse("Nema kampanja");

                return new ReadOnlyStringWrapper(displayText);
            }
        });


        List<Company> companyList = DatabaseUtils.getCompaniesFromDataBase();
        ObservableList<Company> companyObservableList = FXCollections.observableArrayList(companyList);
        companyTableView.setItems(companyObservableList);
        searchCompany();
    }

    public void searchCompany(){
        List<Company> companyList = DatabaseUtils.getCompaniesFromDataBase();

        String nameCompany = companyNameTextField.getText();
        String addressCompany = companyAddressTextField.getText();

        List<Company> filteredCompanyList = companyList.stream()
                .filter(company -> nameCompany == null || company.getCompanyName().contains(nameCompany))
                .filter(company -> addressCompany == null || company.getCompanyAddress().contains(addressCompany))
                .collect(Collectors.toList());

        ObservableList<Company> companyObservableList = FXCollections.observableArrayList(filteredCompanyList);

        Platform.runLater(()->{
            companyTableView.setItems(companyObservableList);
        });

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

}
