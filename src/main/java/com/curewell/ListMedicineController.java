package com.curewell;

import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.dao.impl.MedicineDaoImpl;
import com.curewell.model.Medicine;
import com.curewell.model.Employee;
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

public class ListMedicineController {

    private EmployeeDaoImpl employeeDao;

    private Employee currentUser;
    private String currentRole;
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
    private JFXButton settingbutton;

    @FXML
    private JFXButton signoutbutton;

    @FXML
    private JFXButton stockbutton;

    @FXML
    private TableView<Medicine> medicineTable;

    @FXML
    private TableColumn<Medicine, String> nameColumn;

    @FXML
    private TableColumn<Medicine, String> idColumn;

    @FXML
    private TableColumn<Medicine, String> descColumn;
    @FXML
    private TableColumn<Medicine, String> PriceColumn;
    @FXML
    private TableColumn<Medicine, String> QuantityColumn;
    @FXML
    private TableColumn<Medicine, String> actionsColumn;

    public void setCurrentUser(String currentRole) {
        this.currentRole = currentRole;
    }
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        loadMedicineData();
    }

    private void loadMedicineData() {
        MedicineDaoImpl medicinedao = new MedicineDaoImpl();
        List<Medicine> medicinelist = medicinedao.selectAll();

        medicineTable.getItems().clear();

        medicineTable.getItems().addAll(medicinelist);
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
    public void ButtonTable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addMedicine.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) buttontable.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}