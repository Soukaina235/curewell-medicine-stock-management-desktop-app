package com.curewell;

import com.curewell.dao.impl.TransactionDaoImpl;
import com.curewell.model.Transaction;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ListTransactionController {
    private TransactionDaoImpl transactionDao;

    @FXML
    private JFXButton buttontable;
    @FXML
    private JFXButton addbutton;

    @FXML
    private JFXButton companybutton;

    @FXML
    private JFXButton employeebutton;

    @FXML
    private JFXButton homebutton;

    @FXML
    private JFXButton orderbutton;
    @FXML
    private Button validateButton;

    @FXML
    private JFXButton settingbutton;

    @FXML
    private JFXButton signoutbutton;

    @FXML
    private JFXButton stockbutton;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, String> idColumn;
    @FXML
    private TableColumn<Transaction, String> companyNameColumn;
    @FXML
    private TableColumn<Transaction, String> employeeNameColumn;
    @FXML
    private TableColumn<Transaction, String> creationDateColumn;
    @FXML
    private TableColumn<Transaction, String> totalColumn;
    @FXML
    private TableColumn<Transaction, String> statusColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;

    @FXML
    private JFXButton addNewTransactionButton;


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeFullName"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalString"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statusString"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeString"));
        loadTransactionData();
    }
    private void loadTransactionData() {
        List<Transaction> transactionList = new TransactionDaoImpl().findAll();

        transactionTable.getItems().clear();

        transactionTable.getItems().addAll(transactionList);
    }

    public void addNewTransactionAction () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addTransaction.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) addNewTransactionButton.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleValidation(Transaction transaction) {
        int typeTransactionId = transaction.getId();
        int result = transactionDao.validerTypeTransaction(typeTransactionId);

        if (result > 0) {
            showAlert("Transaction validated successfully!", Alert.AlertType.INFORMATION, validateButton.getScene().getWindow());
        } else {
            showAlert("Error validating transaction. Please try again.", Alert.AlertType.ERROR, validateButton.getScene().getWindow());
        }
    }


    private void showAlert(String message, Alert.AlertType alertType, Window owner) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Transaction Management System");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("listTransaction.fxml"));
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