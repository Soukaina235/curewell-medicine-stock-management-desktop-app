package com.curewell;

import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.dao.impl.TransactionDaoImpl;
import com.curewell.model.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class TransactionValidateCellFactory implements Callback<TableColumn<Transaction, Void>, TableCell<Transaction, Void>> {

    private static TransactionDaoImpl transactionDao;
    private String transactionType;

    public TransactionValidateCellFactory() {
        transactionDao = new TransactionDaoImpl();
    }

    @Override
    public TableCell<Transaction, Void> call(TableColumn<Transaction, Void> param) {
        return new TransactionValidateCell();
    }

    public class TransactionValidateCell extends TableCell<Transaction, Void> {
        private final Button validateButton = new Button("Validate");
        private final Button cancelButton = new Button("Cancel");
        private final Button detailsButton = new Button("Details");


        public TransactionValidateCell() {
            validateButton.setOnAction(event -> {
                if (getTableRow() != null && getTableRow().getItem() instanceof Transaction) {
                    Transaction transaction = (Transaction) getTableRow().getItem();
                    transactionType = transaction.getType().name();
                    handleValidation(transaction);
                }
            });

            cancelButton.setOnAction(event -> {
                if (getTableRow() != null && getTableRow().getItem() instanceof Transaction) {
                    Transaction transaction = (Transaction) getTableRow().getItem();
                    transactionType = transaction.getType().name();
                    handleCancellation(transaction);
                }
            });
            String login = Application.currentUser.getLogin();
            String pwd =  Application.currentUser.getPassword();

            String role = new EmployeeDaoImpl().getRoleByLoginAndPassword(login, pwd);

            if (Objects.equals(role, "RESP_ACHAT")) {
                cancelButton.setVisible(false);

            } else{
                cancelButton.setVisible(true);
            }

            detailsButton.setOnAction(event -> {
                if (getTableRow() != null && getTableRow().getItem() instanceof Transaction) {
                    Transaction selectedTransaction = (Transaction) getTableRow().getItem();
                    transactionType = selectedTransaction.getType().name();

                    showdetailsTransaction(event, selectedTransaction);
                }
            });

            validateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
            detailsButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        }


        private void handleValidation(Transaction transaction) {
            int typeTransactionId = transaction.getId();
            String status = new TransactionDaoImpl().getStatusTransaction(transaction);
            if(Objects.equals(status, "Normal")) {
                int result = transactionDao.validerTypeTransaction(typeTransactionId);
                if (result > 0) {
                    showAlert("Transaction validated successfully!", Alert.AlertType.INFORMATION, validateButton.getScene().getWindow());
                } else {
                    showAlert("Error validating transaction. Please try again.", Alert.AlertType.ERROR, validateButton.getScene().getWindow());
                }
            } else if (Objects.equals(status, "Canceled")){
                showAlert("Transaction already cancelled.", Alert.AlertType.WARNING, validateButton.getScene().getWindow());
            } else {
                showAlert("Transaction already validated.", Alert.AlertType.WARNING, validateButton.getScene().getWindow());
            }
        }

        private void handleCancellation(Transaction transaction) {
            int typeTransactionId = transaction.getId();
            String status = new TransactionDaoImpl().getStatusTransaction(transaction);


            if(Objects.equals(status, "Normal")) {
                int result = transactionDao.cancelTransaction(typeTransactionId);
                if (result > 0) {
                    showAlert("Transaction cancelled successfully!", Alert.AlertType.INFORMATION, cancelButton.getScene().getWindow());
                } else {
                    showAlert("Error cancelling transaction. Please try again.", Alert.AlertType.ERROR, cancelButton.getScene().getWindow());
                }
            } else if (Objects.equals(status, "Canceled")){
                showAlert("Transaction already cancelled.", Alert.AlertType.WARNING, cancelButton.getScene().getWindow());
            } else {
                showAlert("Transaction already validated.", Alert.AlertType.WARNING, cancelButton.getScene().getWindow());
            }
        }
        private void showdetailsTransaction(ActionEvent event, Transaction selectedTransaction) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("transactionDetails.fxml"));
                Parent modifyForm = loader.load();
                Scene modifyScene = new Scene(modifyForm);
                TransactionController TransactionController = loader.getController();
                TransactionController.initializeData(selectedTransaction);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(modifyScene);
            } catch (IOException e) {
                e.printStackTrace();
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
                    String fxmlPath = "listTransaction" + transactionType + ".fxml";
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                    Parent loginParent = loader.load();
                    Scene loginScene = new Scene(loginParent);

                    Stage currentStage = (Stage) owner;
                    currentStage.setScene(loginScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new HBox(5, validateButton, cancelButton,detailsButton));
            }
        }
    }
}
