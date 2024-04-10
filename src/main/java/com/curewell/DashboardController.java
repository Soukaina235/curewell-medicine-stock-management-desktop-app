package com.curewell;

import com.curewell.dao.impl.AdminDoaImpl;
import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.dao.impl.TransactionDaoImpl;
import com.curewell.util.MonthRetreival;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import static javafx.collections.FXCollections.observableArrayList;

public class DashboardController {
    public Label txtTotalCustomers;
    public Label TotalOrders;
    public Label TotalSales;
    public Label SoldItem;
    //public PaymentDTO paymentDTOS;

    private void initInfo() throws SQLException, ClassNotFoundException {


//        int customerCount = new EmployeeDaoImpl().employeeCount();
//        txtTotalCustomers.setText(String.valueOf(customerCount));
    }

//    public void loadPieChart() throws SQLException, ClassNotFoundException {
//        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = observableArrayList(
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
//public void loadPieChart() throws SQLException, ClassNotFoundException {
//    TransactionDaoImpl transactionDao = new TransactionDaoImpl();
//
//    // Initialize map with all months set to zero
//    Map<String, Integer> validatedTransactionsByMonth = new HashMap<>();
//    for (Month month : Month.values()) {
//        validatedTransactionsByMonth.put(month.toString(), 0);
//    }
//
//    // Fetch data from database and update the map
//    Map<String, Integer> fetchedData = transactionDao.getValidatedTransactionsByMonth();
//
//    // Update the map with fetched data
//    for (Map.Entry<String, Integer> entry : fetchedData.entrySet()) {
//        validatedTransactionsByMonth.put(entry.getKey(), entry.getValue());
//    }
//
//    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
//
//    int totalTransactions = fetchedData.values().stream().mapToInt(Integer::intValue).sum();
//
//    // Iterate over all months
//    for (Month month : Month.values()) {
//        String monthName = month.toString();
//        int count = validatedTransactionsByMonth.get(monthName);
//
//        // Calculate percentage
//        double percentage = (count * 100.0) / totalTransactions;
//
//        // Add the data to the pie chart with the percentage and count included in the label
//        pieChartData.add(new PieChart.Data(monthName + " " + String.format("%.2f", percentage), count));
//    }
//
//    PieChart.setData(pieChartData);
//}



    //    public void loadValueOfMonthlySales() {
//        Map<Integer, Double> monthlySales = new TransactionDaoImpl().getMonthlySales();
//        XYChart.Series series =new XYChart.Series();
//
////        for (Map.Entry<Integer, Double> entry : monthlySales.entrySet()) {
////            series.getData().add(new XYChart.Data(String.valueOf(entry.getKey()),entry.getValue()));
////            //System.out.println(entry.getKey() + "   " + entry.getValue());
////        }
//
//        int currentMonth = MonthRetreival.retreiveMonth();
//
//        for (int i = currentMonth + 1; i <= 12; i++) {
//            double sales = monthlySales.containsKey(i) ? monthlySales.get(i) : 0;
//            series.getData().add(new XYChart.Data(String.valueOf(i), sales));
//        }
//        for (int i = 1; i <= currentMonth; i++) {
//            double sales = monthlySales.containsKey(i) ? monthlySales.get(i) : 0;
//            series.getData().add(new XYChart.Data(String.valueOf(i), sales));
//        }
//
//
//
////        series.getData().add(new XYChart.Data("7",23));
////        series.getData().add(new XYChart.Data("2",65));
////        series.getData().add(new XYChart.Data("1",68));
////        series.getData().add(new XYChart.Data("4",32));
////        series.getData().add(new XYChart.Data("5",56));
////        series.getData().add(new XYChart.Data("6",76));
////        series.getData().add(new XYChart.Data("8",44));
////        series.getData().add(new XYChart.Data("3",44));
//        areaChart.getData().add(series);
//    }





    // ------------------------------------------------------------------------------
    // __________________________________ old code DON'T TOUCH ____________________________________________________
    // ------------------------------------------------------------------------------






    private EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();


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
    public AreaChart areaChart;

    @FXML
    private PieChart PieChart;
    @FXML
    private BarChart barChart;


//    public void setCurrentUser(Employee employee) {
//        this.currentUser = employee;
//    }

    @FXML
    private Label total,valid,cancel,normal;

    public void loadPieChart() throws SQLException, ClassNotFoundException {
        TransactionDaoImpl transactionDao = new TransactionDaoImpl();
        Map<String, Integer> validatedTransactionsByMonth = transactionDao.getValidatedTransactionsByMonth();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Month month : Month.values()) {
            String monthName = month.toString();
            int count = validatedTransactionsByMonth.getOrDefault(monthName, 0);

            // Add the data to the pie chart with the month name and count
            pieChartData.add(new PieChart.Data(monthName + " (" + count + ")", count));
        }

        PieChart.setData(pieChartData);
    }

    public void loadBarChart() throws SQLException, ClassNotFoundException {
        TransactionDaoImpl transactionDao = new TransactionDaoImpl();

        // Fetch data from database
        Map<String, Integer> transactionsByClient = transactionDao.getTotalTransactionsByClient();

        // Sort the map in descending order and limit to top 5
        Map<String, Integer> top5Clients = transactionsByClient.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        barChart.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());

        int seriesCounter = 0;
        // Iterate over top 5 clients
        for (Map.Entry<String, Integer> entry : top5Clients.entrySet()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(seriesCounter + 1), entry.getValue());
            StackPane bar = new StackPane();
            Label label = new Label(entry.getKey());
            bar.getChildren().add(label);
            bar.getStyleClass().add("default-color" + seriesCounter); // Apply CSS class to StackPane
            data.setNode(bar);
            series.getData().add(data);

            seriesCounter++;
        }

        // Set the series name
        series.setName("Top 5 Clients");

        barChart.getData().add(series);
    }
    public void loadValueOfMonthlySales() {
        Map<Integer, Double> monthlySales = new TransactionDaoImpl().getMonthlySales();
        XYChart.Series series = new XYChart.Series();

        for (int i = 1; i <= 12; i++) {
            double sales = monthlySales.containsKey(i) ? monthlySales.get(i) : 0;
            series.getData().add(new XYChart.Data(Month.of(i).name(), sales));
        }
        series.setName("2024");
        areaChart.getData().add(series);
        CategoryAxis xAxis = (CategoryAxis) areaChart.getXAxis();
        xAxis.setGapStartAndEnd(true);
        xAxis.setStartMargin(0.5);
        xAxis.setEndMargin(0.5);
    }
    @FXML
    public void initialize() {
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

        try {
//            loadPieChart();
            loadBarChart();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        barChart.setLegendVisible(false);

        loadValueOfMonthlySales();
        areaChart.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());

        //        try {
//            orders ();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            sales ();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            soldItem ();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
////            loadPieChart();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
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

    @FXML
    public void StockButton(ActionEvent event) {
        String login = Application.currentUser.getLogin();
        String password = Application.currentUser.getPassword();
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
}