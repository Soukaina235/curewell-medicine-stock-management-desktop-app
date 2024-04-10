package com.curewell;


import com.curewell.dao.TransactionDao;
import com.curewell.dao.impl.TransactionDaoImpl;
import com.curewell.model.Employee;
import com.curewell.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.XYChart;
import java.util.*;
import java.util.stream.Collectors;


public class AnalysisController {

    @FXML
    TableView<Employee> employeeTableView;

    @FXML
    LineChart<Integer, Double> profitLineChart;

    @FXML
    private Label companyProfitLabel;

    private TransactionDao transactionDao;

    @FXML
    public void initialize() {
        // Load data for top 3 employees
        List<Employee> topEmployees = getTopNEmployeesWithHighestTotalTransactions(3);
        topEmployees.stream().filter(Objects::nonNull)  // Filter out null elements
                .forEach(n->n.setTotalTransactions(n.getalltransactions2()));
        employeeTableView.getItems().addAll(topEmployees);
        // Set up the employee table columns
        TableColumn<Employee, String> employeeNameColumn = new TableColumn<>("lastName");
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Employee, Integer> totalTransactionsColumn = new TableColumn<>("totalTransactions");
        totalTransactionsColumn.setCellValueFactory(new PropertyValueFactory<>("totalTransactions"));

        employeeTableView.getColumns().addAll(employeeNameColumn, totalTransactionsColumn);

        // Load data for company profit in the latest 10 transactions*
        List<Transaction> transactions = new TransactionDaoImpl().findAll();
        List<Transaction> demandeTransactions = transactions.stream()
                .filter(transaction -> transaction.getType().toString().equals("Demande"))
                .collect(Collectors.toList());

        // Calculate the total profit from 'demande' transactions
        double companyProfit = demandeTransactions.stream()
                .mapToDouble(Transaction::getTotal)
                .sum();

        companyProfitLabel.setText("$" + String.format("%.2f", companyProfit));

        // Load data for the line chart
        XYChart.Series<Integer, Double> profitSeries = new XYChart.Series<>();
        for (int i = 0; i < demandeTransactions.size(); i++) {
            profitSeries.getData().add(new XYChart.Data<>(i + 1, demandeTransactions.get(i).getTotal()));
        }
        profitLineChart.getData().add(profitSeries);
        displayTopNEmployeesWithHighestTotalTransactions(5);

    }

    @FXML
    BarChart<String, Number> barChart;

    public void displayTopNEmployeesWithHighestTotalTransactions(int n) {
        // Clear the previous data
        barChart.getData().clear();

        // Set the title for the bar chart
        barChart.setTitle("Top " + n + " Employees with Highest Total Transactions");

        // Create a list to store the top n employees
        List<Employee> topNEmployees = getTopNEmployeesWithHighestTotalTransactions(n);

        // Create a series to store the data
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Loop through the top n employees and add their data to the series
        for (Employee employee : topNEmployees) {
            double totalTransactions = employee.getalltransactions2();
            series.getData().add(new XYChart.Data<>(employee.getLastName(), totalTransactions));
        }

        // Add the series to the bar chart
        barChart.getData().add(series);
    }

    public List<Employee> getTopNEmployeesWithHighestTotalTransactions(int n) {
        List<Transaction> allTransactions = new TransactionDaoImpl().findAll();

        // Map to store the total transactions for each employee
        Map<Employee, Double> employeeTotalTransactions = new HashMap<>();

        for (Transaction transaction : allTransactions) {
            Employee employee = transaction.getEmployee();

            // Update the total transactions for the employee
            employeeTotalTransactions.put(employee, employeeTotalTransactions.getOrDefault(employee, 0.0) + transaction.getTotal());
        }

        // Sort the employees based on their total transactions in descending order
        List<Employee> sortedEmployees = employeeTotalTransactions.entrySet().stream()
                .sorted(Map.Entry.<Employee, Double>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return sortedEmployees;
    }
}
