package com.curewell;

import com.curewell.dao.impl.AdminDoaImpl;
import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.dao.impl.TransactionDaoImpl;
import com.curewell.model.Employee;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import static javafx.collections.FXCollections.observableArrayList;

public class AdminController {



    private EmployeeDaoImpl employeeDao;

    private Employee currentUser;
    @FXML
    private TextField AddressField;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField FnameField;

    @FXML
    private TextField LnameField;

    @FXML
    private TextField LoginField;

    @FXML
    private TextField PasswordField;

    @FXML
    private TextField PhoneField;

    @FXML
    private TextField RoleField;

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

    private AdminDoaImpl adminDao;

    @FXML
    private TextField ConfirmPassField;

    @FXML
    private TextField CurrentPassField;

    @FXML
    private TextField Email2Field;

    @FXML
    private TextField NewPassField;

    @FXML
    private JFXButton cancelbtn;
    @FXML
    private Label MessageLabel;

    @FXML
    private Label total,valid,cancel,normal;
    @FXML
    public void initialize() {
        adminDao = new AdminDoaImpl();
        TransactionDaoImpl transactiondao = new TransactionDaoImpl();
        int rs1 = transactiondao.getCountTotal();
        int rs2 = transactiondao.getCountValidated();
        int rs3 = transactiondao.getCountCancelled();
        int rs4 = transactiondao.getCountNormal();
        if(rs1>=0){
            total.setText(Integer.toString(rs1));
        }
        if(rs2>=0){
            valid.setText(Integer.toString(rs2));
        }
        if(rs3>=0){
            cancel.setText(Integer.toString(rs3));
        }
        if(rs4>=0){
            normal.setText(Integer.toString(rs4));
        }
    }
    public void setCurrentUser(Employee employee) {
        this.currentUser = employee;
    }
    @FXML
    public void HomeButton(ActionEvent event) {

        if (this.currentUser != null) {
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
    public void addEmployee(ActionEvent event) {
        String firstName = FnameField.getText();
        String lastName = LnameField.getText();
        String email = EmailField.getText();
        String address = AddressField.getText();
        String phone = PhoneField.getText();
        String login = LoginField.getText();
        String password = PasswordField.getText();
        String role = RoleField.getText();

        Employee newEmployee = new Employee(firstName, lastName, email, address,phone,login, password,role);

        int result = adminDao.addEmployee(newEmployee);

        if (result > 0) {
            showAlert("Employee added successfully!", Alert.AlertType.INFORMATION,(Stage) addbutton.getScene().getWindow());
        } else {
            showAlert("Error adding employee. Please try again.", Alert.AlertType.ERROR,(Stage) addbutton.getScene().getWindow());
        }
    }

    private void showAlert(String message, Alert.AlertType alertType, Stage currentStage) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Employee Management System");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("listEmployee.fxml"));
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
    @FXML
    public void StockButton(ActionEvent event) {
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
            Scene scene = new Scene(root);

            Stage stage = (Stage) stockbutton.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void Modify(ActionEvent event) {
        String currentPassword = CurrentPassField.getText();
        String newEmail = Email2Field.getText();
        String newPassword = NewPassField.getText();
        String confirmPassword = ConfirmPassField.getText();

        if (adminDao.isCurrentPasswordCorrect(currentPassword)) {
            if (newPassword.equals(confirmPassword)) {
                int result = adminDao.updateAdminCredentials(newEmail, newPassword);
                if(result >0 ){
                    MessageLabel.setText("Admin credentials updated successfully!");
                }else {
                    MessageLabel.setText("No changes were made to admin credentials.");
                }
            } else {
                MessageLabel.setText("The passwords do not match!");
            }
        } else {
            MessageLabel.setText("The current password is incorrect!");
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Email2Field.setText("");
        CurrentPassField.setText("");
        NewPassField.setText("");
        ConfirmPassField.setText("");
    }
}







//    public Label txtTotalCustomers;
//    public Label TotalOrders;
//    public Label TotalSales;
//    public Label SoldItem;
//    //public PaymentDTO paymentDTOS;
//    public AreaChart areaChart;
//
//    @FXML
//    private javafx.scene.chart.PieChart PieChart;
//
//
//    private void initInfo() throws SQLException, ClassNotFoundException {
//
//
////        int customerCount = new EmployeeDaoImpl().employeeCount();
////        txtTotalCustomers.setText(String.valueOf(customerCount));
//    }
//
//    public void loadPieChart() throws SQLException, ClassNotFoundException {
//        ObservableList<PieChart.Data> pieChartData = observableArrayList(
//                new PieChart.Data("January 13%", 13),
//                new PieChart.Data("February", 25),
//                new PieChart.Data("March", 10),
//                new PieChart.Data("April", 43),
//                new PieChart.Data("April", 25),
//                new PieChart.Data("May", 22),
//                new PieChart.Data("June", 12),
//                new PieChart.Data("July", 82),
//                new PieChart.Data("August", 22),
//                new PieChart.Data("September", 32),
//                new PieChart.Data("October", 24),
//                new PieChart.Data("November", 22),
//                new PieChart.Data("December", 22));
//        PieChart.setData(pieChartData);
//    }
//
//    public void loadValueOfMonthlySales() {
//        Map<Integer, Double> monthlySales = new TransactionDaoImpl().getMonthlySales();
//        XYChart.Series series =new XYChart.Series();
//
//        for (Map.Entry<Integer, Double> entry : monthlySales.entrySet()) {
//            series.getData().add(new XYChart.Data(String.valueOf(entry.getKey()),entry.getValue()));
//        }
//
////        series.getData().add(new XYChart.Data("1",23));
////        series.getData().add(new XYChart.Data("2",65));
////        series.getData().add(new XYChart.Data("3",68));
////        series.getData().add(new XYChart.Data("4",32));
////        series.getData().add(new XYChart.Data("5",56));
////        series.getData().add(new XYChart.Data("6",76));
////        series.getData().add(new XYChart.Data("7",44));
////        series.getData().add(new XYChart.Data("7",44));
//        areaChart.getData().add(series);
//    }
////
////    @FXML
////    public void initialize() {
////        adminDao = new AdminDoaImpl();
////        TransactionDaoImpl transactiondao = new TransactionDaoImpl();
////        int rs1 = transactiondao.getCountTotal();
////        int rs2 = transactiondao.getCountValidated();
////        int rs3 = transactiondao.getCountCancelled();
////        int rs4 = transactiondao.getCountNormal();
////        if(rs1>=0){
////            total.setText(Integer.toString(rs1));
////        }
////        if(rs2>=0){
////            valid.setText(Integer.toString(rs2));
////        }
////        if(rs3>=0){
////            cancel.setText(Integer.toString(rs3));
////        }
////        if(rs4>=0){
////            normal.setText(Integer.toString(rs4));
////        }
////
////        try {
////            initInfo();
////        } catch (SQLException throwable) {
////            throwable.printStackTrace();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
////
////        loadValueOfMonthlySales();
////
////        //        try {
//////            orders ();
//////        } catch (SQLException throwables) {
//////            throwables.printStackTrace();
//////        } catch (ClassNotFoundException e) {
//////            e.printStackTrace();
//////        }
//////        try {
//////            sales ();
//////        } catch (SQLException throwables) {
//////            throwables.printStackTrace();
//////        } catch (ClassNotFoundException e) {
//////            e.printStackTrace();
//////        }
//////        try {
//////            soldItem ();
//////        } catch (SQLException throwables) {
//////            throwables.printStackTrace();
//////        } catch (ClassNotFoundException e) {
//////            e.printStackTrace();
//////        }
//////
////        try {
////            loadPieChart();
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
////    }
////
////
////
////    // ------------------------------------------------------------------------------
//    // __________________________________ old code DON'T TOUCH ____________________________________________________
//    // ------------------------------------------------------------------------------
//
//
//
//
