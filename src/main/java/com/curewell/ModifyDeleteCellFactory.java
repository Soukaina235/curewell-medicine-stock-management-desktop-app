package com.curewell;

import com.curewell.dao.impl.AdminDoaImpl;
import com.curewell.dao.impl.CompanyDaoImpl;
import com.curewell.model.Employee;
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

public class ModifyDeleteCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new ModifyDeleteCell<>();
    }
    private static AdminDoaImpl adminDao;
    private static CompanyDaoImpl companyDao;

    public ModifyDeleteCellFactory() {
        adminDao = new AdminDoaImpl();
    }

    public static class ModifyDeleteCell<S, T> extends TableCell<S, T> {
        private final Button modifyButton = new Button("Modify");
        private final Button deleteButton = new Button("Delete");

        public ModifyDeleteCell() {

            modifyButton.setOnAction(event -> {
                if (getTableRow() != null && getTableRow().getItem() instanceof Employee) {
                    Employee selectedEmployee = (Employee) getTableRow().getItem();
                    showModifyForm(event, selectedEmployee);
                }
            });

            deleteButton.setOnAction(event -> {
                if (getTableRow() != null && getTableRow().getItem() instanceof Employee) {
                    Employee selectedEmployee = (Employee) getTableRow().getItem();
                    showDeleteConfirmation(selectedEmployee);
                }
            });
        }

        private void showModifyForm(ActionEvent event, Employee selectedEmployee) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyEmployee.fxml"));
                Parent modifyForm = loader.load();

                Scene modifyScene = new Scene(modifyForm);

                ModifyDeleteController modifyController = loader.getController();

                modifyController.initializeData(selectedEmployee);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                currentStage.setScene(modifyScene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void showDeleteConfirmation(Employee selectedEmployee) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Employee");
            alert.setHeaderText("You're about to delete the employee!");
            alert.setContentText("Are you sure you want to delete this employee?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                int employeeId = selectedEmployee.getId();
                int deleteResult = adminDao.deleteEmployee(employeeId);

                if (deleteResult > 0) {
                    showAlert("Employee deleted successfully!", Alert.AlertType.INFORMATION, deleteButton.getScene().getWindow());
                } else {
                    showAlert("Error deleting employee. Please try again.", Alert.AlertType.ERROR, deleteButton.getScene().getWindow());
                }
            }
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new TableCellPane(modifyButton, deleteButton));
            }
        }

        private void showAlert(String message, Alert.AlertType alertType, Window window) {
            Alert alert = new Alert(alertType);
            alert.setTitle("Employee Management System");
            alert.setHeaderText(null);
            alert.setContentText(message);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("listEmployee.fxml"));
                    Parent loginParent = loader.load();
                    Scene loginScene = new Scene(loginParent, 1200, 661);

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
