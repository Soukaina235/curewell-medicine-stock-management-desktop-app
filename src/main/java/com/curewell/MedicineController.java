package com.curewell;

import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.dao.impl.MedicineDaoImpl;
import com.curewell.model.Category;
import com.curewell.model.Medicine;
import com.curewell.model.Employee;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MedicineController implements Initializable {
    private EmployeeDaoImpl employeeDao;
    private Employee currentUser;
    @FXML
    private TextField med_category;
    @FXML
    private JFXButton companybutton;
    private Medicine selectedMedicine;

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
    private TextField med_name;
    @FXML
    private TextField med_desc;
    @FXML
    private TextField med_price;
    @FXML
    private TextField med_quantity;
    @FXML
    private JFXButton addbutton;
    @FXML
    private JFXButton medicinebutton;
    private MedicineDaoImpl medicinedao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.medicinedao = new MedicineDaoImpl();
        this.employeeDao = new EmployeeDaoImpl();
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
    public void SettingButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) settingbutton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void EmployeeButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listEmployee.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) employeebutton.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void MedicineButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listMedicine.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) medicinebutton.getScene().getWindow();

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
    public void addMedicine(ActionEvent event) {
        String name = med_name.getText();
        String description = med_desc.getText();
        Double price = Double.parseDouble(med_price.getText());
        int quantite = Integer.parseInt(med_quantity.getText());
        String CategoryString = med_category.getText();
        Category selectedCategory=null;

        switch (CategoryString) {
            case "Cat1":
                selectedCategory = Category.Cat1;
                break;
            case "Cat2":
                selectedCategory = Category.Cat2;
                break;
            case "Cat3":
                selectedCategory = Category.Cat3;
                break;
        }
        
        Medicine medicine = new Medicine(name, description,selectedCategory, price, quantite);

        int result = new MedicineDaoImpl().addMedicine(medicine);

        if (result > 0) {
            showAlert("Medicine added successfully!", Alert.AlertType.INFORMATION,(Stage) addbutton.getScene().getWindow());
        } else {
            showAlert("Error adding medecine. Please try again.", Alert.AlertType.ERROR,(Stage) addbutton.getScene().getWindow());
        }
    }

    public void initializeData(Medicine selectedMedicine) {
        this.selectedMedicine = selectedMedicine;
        med_name.setText(selectedMedicine.getName());
        med_desc.setText(selectedMedicine.getDescription());
        med_category.setText(selectedMedicine.getCategory().toString());
        med_price.setText(Double.toString(selectedMedicine.getPrice()));
        med_quantity.setText(Integer.toString(selectedMedicine.getQuantity()));

    }
    public void ModifyMedicine(ActionEvent actionEvent) {
        try {
            String categoryString = med_category.getText();
            Category selectedCategory = null;

            switch (categoryString) {
                case "Cat1":
                    selectedCategory = Category.Cat1;
                    break;
                case "Cat2":
                    selectedCategory = Category.Cat2;
                    break;
                case "Cat3":
                    selectedCategory = Category.Cat3;
                    break;
            }

            selectedMedicine.setName(med_name.getText());
            selectedMedicine.setDescription(med_desc.getText());
            selectedMedicine.setCategory(selectedCategory);
            Double price = Double.parseDouble(med_price.getText());
            selectedMedicine.setPrice(price);
            int quantity = Integer.parseInt(med_quantity.getText());
            selectedMedicine.setQuantity(quantity);

            int result = medicinedao.updateMedicine(selectedMedicine.getId(),selectedMedicine);

            if (result > 0) {
                showAlert("Medicine modified successfully!", Alert.AlertType.INFORMATION, (Stage)medicinebutton.getScene().getWindow());
            } else {
                showAlert("Error modifying company. Please try again.", Alert.AlertType.ERROR,(Stage) medicinebutton.getScene().getWindow());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message, Alert.AlertType alertType, Stage currentStage) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Medicine Management System");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("listMedicineAchat.fxml"));
                Parent loginParent = loader.load();
                Scene loginScene = new Scene(loginParent);


                currentStage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}