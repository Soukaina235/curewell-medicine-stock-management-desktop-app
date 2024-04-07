package com.curewell;

import com.curewell.dao.impl.CompanyDaoImpl;
import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.dao.impl.MedicineDaoImpl;
import com.curewell.dao.impl.TransactionDaoImpl;
import com.curewell.model.Medicine;
import com.curewell.model.Transaction;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class TransactionController {
    @FXML
    private ComboBox<String> companyField;

    @FXML
    private JFXButton homebutton;
    @FXML
    private JFXButton companybutton;
    @FXML
    private TextField totalField;
    @FXML
    private TextField discountField;

    @FXML
    private Button addButton;

    private Transaction selectedTransaction;

    @FXML
    private Label CompanyField;

    @FXML
    private Label CreationField;

    @FXML
    private Label DiscountField;

    @FXML
    private Label EmployeeField;
    @FXML
    private Label DateLabel;


    @FXML
    private Label IDField;


    @FXML
    private Label StatusField;

    @FXML
    private Label TotalField;

    @FXML
    private Label TypeField;

    @FXML
    private Label dateField;


    // add medicine
    @FXML
    private ComboBox<String> medicineField;
    @FXML
    private TextField quantityField;
    @FXML
    private Button addMedicine;

    @FXML
    private TableView<Map.Entry<Medicine, Integer>> medicineTable;

    @FXML
    private TableColumn<Map.Entry<Medicine, Integer>, String> medicineNameColumn;

    @FXML
    private TableColumn<Map.Entry<Medicine, Integer>, Integer> medicineQuantityColumn;
    private static Transaction transaction;
    @FXML
    private JFXButton employeebutton;

    @FXML
    private JFXButton orderbutton;

    @FXML
    private JFXButton settingbutton;

    @FXML
    private JFXButton signoutbutton;

    @FXML
    private JFXButton stockbutton;

    private EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    private final TransactionDaoImpl transactionDao = new TransactionDaoImpl();

    @FXML
    public void initialize() {
        if (companyField != null) companyField.setItems(FXCollections.observableList(new CompanyDaoImpl().selectAllNames()));

        if(medicineField != null)  medicineField.setItems(FXCollections.observableList(new MedicineDaoImpl().selectAllNames()));

        if (medicineTable != null) {
            if (transaction.getMedicineQuantityMap() != null) medicineTable.getItems().addAll(transaction.getMedicineQuantityMap().entrySet());
            if (medicineNameColumn != null) {
                medicineNameColumn.setCellValueFactory(null);
                medicineNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getName()));
            }
            if (medicineQuantityColumn != null) {
                medicineQuantityColumn.setCellValueFactory(null);
                medicineQuantityColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue()).asObject());
            }
        }

    }
    public void addTransaction(ActionEvent event) {
        String company = companyField.getValue();
        String discount = discountField.getText();

        System.out.println(company);
        System.out.println(new CompanyDaoImpl().findCompanyByName(company));

        transaction = new Transaction(Double.parseDouble(discount), new Date(), Application.currentUser, new CompanyDaoImpl().findCompanyByName(company));

        showAlertAddingTransaction("Now choose the medicines!", Alert.AlertType.INFORMATION,(Stage) addButton.getScene().getWindow());
    }

    private void showAlertAddingTransaction(String message, Alert.AlertType alertType, Stage currentStage) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Transaction Management System");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addMedicinesInTransaction.fxml"));
                Parent loginParent = loader.load();
                Scene loginScene = new Scene(loginParent);

                currentStage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMedicine() {
        String login = Application.currentUser.getLogin();
        String pwd =  Application.currentUser.getPassword();
        String role = new EmployeeDaoImpl().getRoleByLoginAndPassword(login, pwd);

        if (Integer.parseInt(quantityField.getText()) > new MedicineDaoImpl().findMedicineByName(medicineField.getValue()).getQuantity() && Objects.equals(role, "RESP_VENTE")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaction Management System");
            alert.setHeaderText(null);
            alert.setContentText("Not enough quantity in the storage!");
            alert.showAndWait();
        } else {
            transaction.getMedicineQuantityMap().put(new MedicineDaoImpl().findMedicineByName(medicineField.getValue()), Integer.parseInt(quantityField.getText()));
        }
        System.out.println(transaction);
        System.out.println(medicineTable);
        System.out.println(medicineNameColumn);
        System.out.println(medicineQuantityColumn);

        refreshUI();
    }

    private void refreshUI() {
        medicineTable.getItems().clear();
        medicineTable.getItems().addAll(transaction.getMedicineQuantityMap().entrySet());
    }
    public void confirmTransaction() {
        transaction.setTotal();
        int result = new TransactionDaoImpl().save(transaction);

        if (result > 0) {
            showAlertConfirmTransaction("Transaction added successfully!", Alert.AlertType.INFORMATION,(Stage) addButton.getScene().getWindow());
        } else {
            showAlertConfirmTransaction("Error adding transaction. Please try again.", Alert.AlertType.ERROR,(Stage) addButton.getScene().getWindow());
        }
    }

    public void showAlertConfirmTransaction(String message, Alert.AlertType alertType, Stage currentStage) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Transaction Management System");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                String login = Application.currentUser.getLogin();
                String pwd = Application.currentUser.getPassword();
                String role = new EmployeeDaoImpl().getRoleByLoginAndPassword(login, pwd);
                FXMLLoader loader = null;
                if (Objects.equals(role, "RESP_ACHAT")){
                    loader = new FXMLLoader(getClass().getResource("listTransactionAchat.fxml"));
                 }else{
                    loader = new FXMLLoader(getClass().getResource("listTransactionDemande.fxml"));
                 }
                Parent loginParent = loader.load();
                Scene loginScene = new Scene(loginParent);

                currentStage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
    public void CompanyButton(ActionEvent event) {
        String login = Application.currentUser.getLogin();
        String password = Application.currentUser.getPassword();
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
    @FXML
    public void StockButton(ActionEvent event) {
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
            Scene scene = new Scene(root);

            Stage stage = (Stage) stockbutton.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void initializeData(Transaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String creationDate = dateFormat.format(selectedTransaction.getDateCreation());

        Date validationDate = selectedTransaction.getDateValidation();
        Date cancelDate = selectedTransaction.getDateCancellation();

        IDField.setText(Integer.toString(selectedTransaction.getId()));
        CompanyField.setText(selectedTransaction.getCompany().getName());
        EmployeeField.setText(selectedTransaction.getEmployee().getLastName());
        TotalField.setText(Double.toString(selectedTransaction.getTotalString()));
        DiscountField.setText(Double.toString(selectedTransaction.getDiscount()));
        CreationField.setText(creationDate);
        TypeField.setText(selectedTransaction.getTypeString());
        StatusField.setText(selectedTransaction.getStatusString().toString());

        if (validationDate != null) {
            String formattedValidationDate = dateFormat.format(validationDate);
            dateField.setText(formattedValidationDate);
            DateLabel.setText("Validation Date:");

        } else if (cancelDate != null) {
            String formattedCancelDate = dateFormat.format(cancelDate);
            dateField.setText(formattedCancelDate);
            DateLabel.setText("Cancel Date:");
        } else {
            dateField.setText("N/A");
            DateLabel.setText("Date:");
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
    public void OrderButton(ActionEvent event) {
        String login = Application.currentUser.getLogin();
        String password = Application.currentUser.getPassword();
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
}
