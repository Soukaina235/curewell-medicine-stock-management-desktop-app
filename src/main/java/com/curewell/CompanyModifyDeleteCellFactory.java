package com.curewell;

import com.curewell.dao.impl.CompanyDaoImpl;
import com.curewell.model.Company;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import java.io.IOException;
import java.util.Optional;

public class CompanyModifyDeleteCellFactory implements Callback<TableColumn<Company, Void>, TableCell<Company, Void>> {

    private static CompanyDaoImpl companyDao;

    public CompanyModifyDeleteCellFactory() {
        companyDao = new CompanyDaoImpl();
    }

    @Override
    public TableCell<Company, Void> call(TableColumn<Company, Void> param) {
        return new CompanyModifyDeleteCell();
    }


    public class CompanyModifyDeleteCell extends TableCell<Company, Void> {
        private final Button modifyButton = new Button("Modify");
        private final Button deleteButton = new Button("Delete");

        public CompanyModifyDeleteCell() {
            modifyButton.setOnAction(event -> {
                if (getTableRow() != null && getTableRow().getItem() instanceof Company) {
                    Company selectedCompany = (Company) getTableRow().getItem();
                    showModifyForm(event, selectedCompany);
                }
            });

            deleteButton.setOnAction(event -> {
                if (getTableRow() != null && getTableRow().getItem() instanceof Company) {
                    Company selectedCompany = (Company) getTableRow().getItem();
                    showDeleteConfirmation(selectedCompany);
                }
            });
        }

        private void showModifyForm(ActionEvent event, Company selectedCompany) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyCompany.fxml"));
                Parent modifyForm = loader.load();
                Scene modifyScene = new Scene(modifyForm);
                CompanyController companyController = loader.getController();
                companyController.initializeData(selectedCompany);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(modifyScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void showDeleteConfirmation(Company selectedCompany) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Company");
            alert.setHeaderText("You're about to delete the company!");
            alert.setContentText("Are you sure you want to delete this company?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                int companyId = selectedCompany.getId();
                int deleteResult = companyDao.deleteCompany(companyId);

                if (deleteResult > 0) {
                    showAlert("Company deleted successfully!", Alert.AlertType.INFORMATION, deleteButton.getScene().getWindow());
                } else {
                    showAlert("Error deleting company. Please try again.", Alert.AlertType.ERROR, deleteButton.getScene().getWindow());
                }
            }
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new TableCellPane(modifyButton, deleteButton));
            }
        }

        private void showAlert(String message, Alert.AlertType alertType, Window window) {
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

                    Stage currentStage = (Stage) window;
                    currentStage.setScene(loginScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private static class TableCellPane extends javafx.scene.layout.HBox {
            TableCellPane(Button modifyButton, Button deleteButton) {
                setSpacing(5);
                getChildren().addAll(modifyButton, deleteButton);
            }
        }
    }
}
