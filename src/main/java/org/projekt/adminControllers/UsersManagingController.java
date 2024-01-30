package org.projekt.adminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.projekt.entity.AppUser;
import org.projekt.entity.CommonUser;
import org.projekt.utils.DatabaseUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsersManagingController {
    @FXML
    private ComboBox<CommonUser> userComboBox;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<AppUser> commonUserTableView;
    @FXML
    private TextField username;
    @FXML
    private TextField password;


    public void initialize(){
        List<AppUser> usersList = DatabaseUtils.getAppUsersFromDataBase();

        ObservableList<AppUser> userObservableList = FXCollections.observableArrayList(usersList);

        List<CommonUser> commonList = usersList.stream()
                .filter(user -> user instanceof CommonUser)
                .map(user -> (CommonUser) user)
                .collect(Collectors.toList());

        ObservableList<AppUser> UserObservableList = FXCollections.observableArrayList(usersList);


        commonUserTableView.setItems(UserObservableList);

        ObservableList<CommonUser> commonUsersList = FXCollections.observableArrayList(commonList);

        userComboBox.setItems(commonUsersList);

    }

    private void deleteUser(ActionEvent event){
        String selectedUserIdString = userComboBox.getId().toString();
        List<AppUser> usersList = DatabaseUtils.getAppUsersFromDataBase();

        Optional<AppUser> findUserById = usersList.stream()
                .filter(user -> user.getId().equals(selectedUserIdString))
                .findFirst();


        DatabaseUtils.removeUsersFromDataBase(selectedUserIdString);

    }


}
