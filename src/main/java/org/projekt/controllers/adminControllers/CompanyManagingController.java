package org.projekt.controllers.adminControllers;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.projekt.builders.CompanyBuilder;
import org.projekt.entity.Campaign;
import org.projekt.entity.Company;
import org.projekt.runner.HelloApplication;
import org.projekt.utils.DatabaseUtils;
import org.projekt.utils.Promjene;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CompanyManagingController {
    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private ComboBox<Company> deleteCompanyComboBox;
    @FXML
    private ComboBox<Company> updateCompanyComboBox;
    @FXML
    private TableView<Company> listCompanyDetails;
    @FXML
    private TableColumn<Company, String> companyNameTableColumn;
    @FXML
    private TableColumn<Company, String> companyAddressTableColumn;
    @FXML
    private TableColumn<Company, String> companyEmailTableColumn;
    private final String companyDat = "serializedDat/companySerialized.ser";


    public void initialize(){
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
        companyEmailTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Company, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Company, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getCompanyContact());
            }
        });

        List<Company> companyList = DatabaseUtils.getCompaniesFromDataBase();

        ObservableList<Company> companyObservableList = FXCollections.observableArrayList(companyList);

        listCompanyDetails.setItems(companyObservableList);

        deleteCompanyComboBox.setItems(companyObservableList);


        Platform.runLater(()->{
            updateCompanyComboBox.setItems(companyObservableList);
        });

        listCompanyDetails.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFormWithCompanyData(newSelection);
            }
        });


    }


    @FXML
    public void addCompany(ActionEvent event){
        Optional<Company> newCompany;

        List<Company> companyList = DatabaseUtils.getCompaniesFromDataBase();

        String companyName = companyNameTextField.getText();
        String companyAddress = addressTextField.getText();
        String emailCompany = emailTextField.getText();

        newCompany = Optional.ofNullable(new CompanyBuilder()
                .setCompanyName(companyName)
                .setCompanyAddress(companyAddress)
                .setCompanyContact(emailCompany)
                .createCompany());

        if(newCompany.isPresent()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda dodavanja");
            alert.setHeaderText("Dodavanje kompanije");
            alert.setContentText("Jeste li sigurni da želite dodati odabranu kompaniju?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                DatabaseUtils.saveCompanyToDataBase(newCompany.get());

                newCompany.ifPresent(company -> {
                    Platform.runLater(() -> {
                        ObservableList<Company> items = listCompanyDetails.getItems();
                        items.add(company); // Dodajte samu kompaniju, ne Optional omotnicu
                        listCompanyDetails.setItems(items); // Ovo možda nije potrebno ako items već referencira listu unutar TableView-a
                    });
                });
            }else{
                // Ako je stisnut cancel ignoriramo jednostavno cijelu akciju i zato je else prazan
            }

        }

    }

    @FXML
    public void deleteCompany(ActionEvent event){

        Company selectedCompany = this.deleteCompanyComboBox.getValue();
        List<Company> companyList = DatabaseUtils.getCompaniesFromDataBase();

        if(selectedCompany != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda brisanja");
            alert.setHeaderText("Brisanje kompanije");
            alert.setContentText("Jeste li sigurni da želite obrisati odabranu kompaniju?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){

                DatabaseUtils.removeCompanyFromDataBase(selectedCompany);

                List<Company> listCompany = DatabaseUtils.getCompaniesFromDataBase();
                ObservableList<Company> companyObservableList = FXCollections.observableArrayList(listCompany);

                listCompanyDetails.setItems(companyObservableList);


                Platform.runLater(() -> {
                    ObservableList<Company> items = listCompanyDetails.getItems();
                    listCompanyDetails.setItems(items); // Ovo možda nije potrebno ako items već referencira listu unutar TableView-a
                });

                Platform.runLater(()->{
                    deleteCompanyComboBox.setItems(companyObservableList);
                });


            }else{
                // Ne dogadja se nista ignoriramo akciju
            }
        }

    }

    public void updateCompany(){

        Company selectedCompany = updateCompanyComboBox.getSelectionModel().getSelectedItem();


        String newName = companyNameTextField.getText();
        String newAddress = addressTextField.getText();
        String newEmailAddress = emailTextField.getText();

        selectedCompany.setCompanyName(newName);
        selectedCompany.setCompanyAddress(newAddress);
        selectedCompany.setCompanyContact(newEmailAddress);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);


        alert.setTitle("Potvrda azuriranja");
        alert.setHeaderText("Azuriranje kampanje");
        alert.setContentText("Jeste li sigurni da želite azurirati ovu kampanju?");
        Optional<ButtonType> result = alert.showAndWait();


        if(result.isPresent() && result.get() == ButtonType.OK){
            DatabaseUtils.updateCompanyIntoDataBase(selectedCompany);

            Platform.runLater(()->{
                listCompanyDetails.refresh();
            });
        }

        Company newCompany = new CompanyBuilder().setCompanyName(newName).setCompanyAddress(newAddress).setCompanyContact(newEmailAddress).createCompany();
        Promjene.serializeObjects(selectedCompany, newCompany, companyDat);


    }

    private void fillFormWithCompanyData (Company company){
        companyNameTextField.setText(company.getCompanyName());
        addressTextField.setText(company.getCompanyAddress());
        emailTextField.setText(company.getCompanyContact());

    }

}
