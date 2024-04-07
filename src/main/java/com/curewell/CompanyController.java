package com.curewell;

import com.curewell.dao.impl.CompanyDaoImpl;
import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.model.Company;
import com.curewell.model.StatusCompany;
import com.curewell.model.Employee;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class CompanyController implements Initializable {
    @FXML
    private TextField StatusField;

    @FXML
    private JFXButton addbutton;

    @FXML
    private TextField addressField;

    @FXML
    private JFXButton companybutton;

    @FXML
    private JFXButton employeebutton;

    @FXML
    private TextField gmailField;

    @FXML
    private JFXButton homebutton;

    @FXML
    private JFXButton medecinebutton;

    @FXML
    private TextField nameField;

    @FXML
    private JFXButton orderbutton;

    @FXML
    private TextField phoneField;

    @FXML
    private JFXButton settingbutton;

    @FXML
    private JFXButton signoutbutton;

    @FXML
    private JFXButton stockbutton;
    private CompanyDaoImpl companyDao;
    private Company selectedCompany;
    private EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    @FXML
    private VBox pnitems = null;
    @FXML
    private Button displayAddNewCompanyButton;
    @FXML
    private TextField IdField, NameField, EmailField, PhoneField, AddressField;
    @FXML
    private Button modifyButton;
    private Employee currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.companyDao = new CompanyDaoImpl();
        if (pnitems != null) {
            Node[] nodes = new Node[10];
            for (int i = 0; i < nodes.length; i++) {
                try {
                    nodes[i] = FXMLLoader.load(getClass().getResource("companyItem.fxml"));
                    pnitems.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @FXML
    public void HomeButton(ActionEvent event) {
        String login = currentUser.getLogin();
        String password = currentUser.getPassword();
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
    public void StockButton(ActionEvent event) {
        String login = currentUser.getLogin();
        String password = currentUser.getPassword();
        String ROLE = employeeDao.getRoleByLoginAndPassword(login, password);

        String fxmlPath = null;
        switch (ROLE) {
            case "ADMIN":
                fxmlPath = "listMedicine.fxml";
                break;
            case "RESP_VENTE":
                fxmlPath = "listMedicine.fxml";
                break;
            case "RESP_ACHAT":
                fxmlPath = "listMedicineAchat.fxml";
                break;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) stockbutton.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void OrderButton(ActionEvent event) {
        String login = currentUser.getLogin();
        String password = currentUser.getPassword();
        String ROLE = employeeDao.getRoleByLoginAndPassword(login, password);

        String fxmlPath = null;
        switch (ROLE) {
            case "ADMIN":
                fxmlPath = "listTransaction.fxml";
                break;
            case "RESP_VENTE":
                fxmlPath = "listTransactionDemande.fxml";
                break;
            case "RESP_ACHAT":
                fxmlPath = "listTransactionAchat.fxml";
                break;
        }

        System.out.println(fxmlPath);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) orderbutton.getScene().getWindow();

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


    @FXML
    public void CompanyButton(ActionEvent event) {
        String login = currentUser.getLogin();
        String password = currentUser.getPassword();
        String ROLE = employeeDao.getRoleByLoginAndPassword(login, password);

        String fxmlPath = null;
        switch (ROLE) {
            case "ADMIN":
                fxmlPath = "listCompaniesAdmin.fxml";
                break;
            case "RESP_VENTE":
                fxmlPath = "listCompanies.fxml";
                break;
            case "RESP_ACHAT":
                fxmlPath = "listCompanies.fxml";
                break;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) companybutton.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addNewCompanyButtonOnAction(ActionEvent event) {
        String companyName = nameField.getText();
        String companyMail = gmailField.getText();
        String companyPhone = phoneField.getText();
        String companyAddress = addressField.getText();
        String companyStatus = StatusField.getText();

        try {
            StatusCompany status = StatusCompany.valueOfIgnoreCase(companyStatus.toUpperCase());
            Company company = new Company(companyName, companyMail, companyPhone, companyAddress, status);

            int result = companyDao.add(company);
            if (result > 0) {
                showAlert("Company added successfully!", Alert.AlertType.INFORMATION, addbutton.getScene().getWindow());
            } else {
                showAlert("Error adding company. Please try again.", Alert.AlertType.ERROR, addbutton.getScene().getWindow());
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            showAlert("Invalid company status. Please enter a valid status.", Alert.AlertType.ERROR, ((Node) event.getSource()).getScene().getWindow());
        }
    }


    public void initializeData(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
        NameField.setText(selectedCompany.getName());
        EmailField.setText(selectedCompany.getGmail());
        PhoneField.setText(selectedCompany.getPhone());
        AddressField.setText(selectedCompany.getAddress());
        StatusField.setText(selectedCompany.getStatus().toString());

    }
    public void ModifyCompany(ActionEvent actionEvent) {
        try {
            selectedCompany.setName(NameField.getText());
            selectedCompany.setGmail(EmailField.getText());
            selectedCompany.setAddress(AddressField.getText());
            selectedCompany.setPhone(PhoneField.getText());

            if(StatusField.getText().equals("VIP")){
                selectedCompany.setStatus(StatusCompany.VIP);
            } else if (StatusField.getText().equals("Normal")) {
                selectedCompany.setStatus(StatusCompany.Normal);
            }else showAlert("Company status must be Normal or VIP. Please try again.", Alert.AlertType.ERROR, modifyButton.getScene().getWindow());

            int result = companyDao.updateCompany(selectedCompany.getId(),selectedCompany);

            if (result > 0) {
                showAlert("Company modified successfully!", Alert.AlertType.INFORMATION, modifyButton.getScene().getWindow());
            } else {
                showAlert("Error modifying company. Please try again.", Alert.AlertType.ERROR, modifyButton.getScene().getWindow());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message, Alert.AlertType alertType, Window owner) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Company Management System");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("listCompanies.fxml"));
                Parent loginParent = loader.load();
                Scene loginScene = new Scene(loginParent);


                Stage currentStage = (Stage) owner;
                currentStage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
