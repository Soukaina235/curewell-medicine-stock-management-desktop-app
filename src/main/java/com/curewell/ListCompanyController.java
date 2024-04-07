package com.curewell;

import com.curewell.dao.impl.AdminDoaImpl;
import com.curewell.dao.impl.CompanyDaoImpl;
import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.model.Company;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class ListCompanyController {
    private AdminDoaImpl adminDao;
    private EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    private CompanyDaoImpl companyDao;
//
//    private Employee currentUser;
    @FXML
    private TableView<Company> companyTable;
    @FXML
    private TableColumn<Company, String> nameColumn, gmailColumn, phoneColumn, addressColumn, statusColumn;
    @FXML
    private JFXButton buttontable, addbutton, companybutton, employeebutton, homebutton, orderbutton,
            settingbutton, signoutbutton, stockbutton;

    @FXML
    public void initialize() {
        companyDao = new CompanyDaoImpl();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gmailColumn.setCellValueFactory(new PropertyValueFactory<>("gmail"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadCompanyData();
    }
//    public void setCurrentUser(Employee employee) {
//        this.currentUser = employee;
//    }

    private void loadCompanyData() {
        List<Company> companyList = companyDao.selectAll();
        companyTable.getItems().clear();
        companyTable.getItems().addAll(companyList);
    }
    @FXML
    public void CompanyButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listCompanies.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) companybutton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void ButtonTable(ActionEvent event) {
        String login = Application.currentUser.getLogin();
        String password = Application.currentUser.getPassword();
        String ROLE = employeeDao.getRoleByLoginAndPassword(login, password);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addCompany.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttontable.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Home and Signout
    @FXML
    public void HomeButton(ActionEvent event) {
        String login = Application.currentUser.getLogin();
        String password = Application.currentUser.getPassword();
        String ROLE = employeeDao.getRoleByLoginAndPassword(login, password);

        String fxmlPath = null;
        switch (ROLE) {
            case "ADMIN":
                fxmlPath = "admin.fxml";
                break;
            case "RESP_VENTE":
                fxmlPath = "Resp_vente.fxml";
                break;
            case "RESP_ACHAT":
                fxmlPath = "Resp_achat.fxml";
                break;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) homebutton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void signout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to Logout!");
        alert.setContentText("Are You Sure?");

        if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent loginParent = loader.load();
                Scene loginScene = new Scene(loginParent);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                currentStage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
