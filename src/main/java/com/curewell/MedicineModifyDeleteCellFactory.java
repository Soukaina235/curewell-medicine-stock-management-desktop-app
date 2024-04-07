package com.curewell;

import com.curewell.dao.impl.MedicineDaoImpl;
import com.curewell.model.Medicine;
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

public class MedicineModifyDeleteCellFactory implements Callback<TableColumn<Medicine, String>, TableCell<Medicine, String>> {

    private final MedicineDaoImpl medicineDao;
    @Override
    public TableCell<Medicine, String> call(TableColumn<Medicine, String> param) {
        return new MedicineModifyDeleteCell();
    }

    public MedicineModifyDeleteCellFactory() {
        this.medicineDao = new MedicineDaoImpl();
    }

    private class MedicineModifyDeleteCell extends TableCell<Medicine, String> {
        private final Button modifyButton = new Button("Modify");
        private final Button deleteButton = new Button("Delete");

        public MedicineModifyDeleteCell() {
            modifyButton.setOnAction(this::handleModifyButtonClick);
            deleteButton.setOnAction(this::handleDeleteButtonClick);
        }

        private void handleModifyButtonClick(ActionEvent event) {
            Medicine selectedMedicine = getSelectedMedicine();
            showModifyForm(event, selectedMedicine);
        }

        private void handleDeleteButtonClick(ActionEvent event) {
            Medicine selectedMedicine = getSelectedMedicine();
            showDeleteConfirmation(selectedMedicine);
        }

        private Medicine getSelectedMedicine() {
            return (Medicine) getTableRow().getItem();
        }

        private void showModifyForm(ActionEvent event, Medicine selectedMedicine) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("modifymedicine.fxml"));
                Parent modifyForm = loader.load();
                Scene modifyScene = new Scene(modifyForm);
                MedicineController medicineController = loader.getController();
                medicineController.initializeData(selectedMedicine);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(modifyScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void showDeleteConfirmation(Medicine selectedMedicine) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Medicine");
            alert.setHeaderText("You're about to delete the Medicine!");
            alert.setContentText("Are you sure you want to delete this medicine?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                int medicineId = selectedMedicine.getId();
                int deleteResult = medicineDao.deleteMedicine(medicineId);

                if (deleteResult > 0) {
                    showAlert("Medicine deleted successfully!", Alert.AlertType.INFORMATION, deleteButton.getScene().getWindow());
                } else {
                    showAlert("Error deleting Medicine. Please try again.", Alert.AlertType.ERROR, deleteButton.getScene().getWindow());
                }
            }
        }

        private void showAlert(String message, Alert.AlertType alertType, Window window) {
            Alert alert = new Alert(alertType);
            alert.setTitle("Medicine Management System");
            alert.setHeaderText(null);
            alert.setContentText(message);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("listMedicine.fxml"));
                    Parent loginParent = loader.load();
                    Scene loginScene = new Scene(loginParent);

                    Stage currentStage = (Stage) window;
                    currentStage.setScene(loginScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new TableCellPane(modifyButton, deleteButton));
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
