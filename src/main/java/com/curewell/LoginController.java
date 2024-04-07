package com.curewell;

import com.curewell.dao.impl.EmployeeDaoImpl;
import com.curewell.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;

public class LoginController  {
    private EmployeeDaoImpl employeeDao;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;

    @FXML
    private TextField UsernameTextField;

    @FXML
    private Label loginMessageLabel;


    public LoginController() {
        this.employeeDao = new EmployeeDaoImpl(); // Initialize the employeeDao here
    }

    public void validateLogin() {
        String login = UsernameTextField.getText();
        String password = enterPasswordField.getText();

        if (employeeDao.validateLogin(login, password)) {

            String ROLE = employeeDao.getRoleByLoginAndPassword(login, password);
            Employee currentUser = employeeDao.findEmployeeByLogin(login);

            loginLoadMain(ROLE,currentUser);
        } else {
            loginMessageLabel.setText("Invalid Login. Please try again!");
        }

        System.out.println(Application.currentUser);
    }

    public void loginLoadMain(String role, Employee currentUser) {
        try {
            FXMLLoader loader = new FXMLLoader();
            if ("ADMIN".equals(role)) {
                loader.setLocation(getClass().getResource("admin.fxml"));
            } else if ("RESP_VENTE".equals(role)) {
                loader.setLocation(getClass().getResource("Resp_vente.fxml"));
            }else{
                loader.setLocation(getClass().getResource("Resp_achat.fxml"));
            }
            Parent root = loader.load();
//            Object controller =loader.getController();
//            if (controller instanceof AdminController) {
//                ((AdminController) controller).setCurrentUser(currentUser);
//            }else if(controller instanceof DashboardController){
//                ((DashboardController) controller).setCurrentUser(currentUser);
//            }else{
//                ((ListCompanyController) controller).setCurrentUser(currentUser);
//            }


            Scene scene = new Scene(root);

            // Assuming you have a reference to the primaryStage
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoginButtonOnAction(ActionEvent event) throws SQLException{
        if(UsernameTextField.getText().isBlank()==false && enterPasswordField.getText().isBlank() == false){
            validateLogin();
        }else{
            loginMessageLabel.setText("Please enter username and password!");
        }
    }

    public void CancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
