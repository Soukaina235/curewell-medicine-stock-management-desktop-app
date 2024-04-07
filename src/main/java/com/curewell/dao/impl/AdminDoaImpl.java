package com.curewell.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.curewell.dao.AdminDao;
import com.curewell.sql.ConnectDB;
import com.curewell.model.Employee;

public class AdminDoaImpl implements AdminDao {
    @Override
    public int addEmployee(Employee employee) {
        ConnectDB connectDB = null;
        int result = 0;

        String sql = "INSERT INTO employee (firstname, lastname, email, address, phone, login, password, ROLE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connectDB = new ConnectDB();

            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(sql)) {
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setString(3, employee.getEmail());
                preparedStatement.setString(4, employee.getAddress());
                preparedStatement.setString(5, employee.getPhone());
                preparedStatement.setString(6, employee.getLogin());
                preparedStatement.setString(7, employee.getPassword());
                preparedStatement.setString(8, employee.getRole());

                result = preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connectDB != null) {
                try {
                    connectDB.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public List<Employee> getAllEmployees() {
        ConnectDB connectDB = null;
        List<Employee> employeeList = new ArrayList<>();

        String sql = "SELECT id,firstname, lastname, email, address, phone, login, password, role FROM employee";

        try {
            connectDB = new ConnectDB();

            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String firstName = resultSet.getString("firstname");
                        String lastName = resultSet.getString("lastname");
                        String email = resultSet.getString("email");
                        String address = resultSet.getString("address");
                        String phone = resultSet.getString("phone");
                        String login = resultSet.getString("login");
                        String password = resultSet.getString("password");
                        String role = resultSet.getString("ROLE");

                        Employee employee = new Employee(id,firstName, lastName, email, address, phone, login,password, role);
                        employeeList.add(employee);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connectDB != null) {
                try {
                    connectDB.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return employeeList;
    }

    @Override
    public int modifyEmployee(Employee employee) {
        ConnectDB connectDB = null;
        int result = 0;

        String sql = "UPDATE employee SET firstname=?, lastname=?, email=?, address=?, phone=?, login=?, password=?, ROLE=? WHERE id=?";

        try {
            connectDB = new ConnectDB();

            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(sql)) {
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setString(3, employee.getEmail());
                preparedStatement.setString(4, employee.getAddress());
                preparedStatement.setString(5, employee.getPhone());
                preparedStatement.setString(6, employee.getLogin());
                preparedStatement.setString(7, employee.getPassword());
                preparedStatement.setString(8, employee.getRole());
                preparedStatement.setInt(9, employee.getId()); // assuming 'id' is the primary key

                result = preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connectDB != null) {
                try {
                    connectDB.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public int deleteEmployee(int employeeId) {
        ConnectDB connectDB = null;
        int result = 0;

        String sql = "DELETE FROM employee WHERE id=?";

        try {
            connectDB = new ConnectDB();

            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);

                result = preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connectDB != null) {
                try {
                    connectDB.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    @Override
    public int updateAdminCredentials(String newEmail, String newPassword) {
        int result = 0;

        String sql = "UPDATE employee SET email = ?, password = ? WHERE firstname='admin'";

        try{
            ConnectDB connectDB = new ConnectDB();
            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(sql)) {

                preparedStatement.setString(1, newEmail);
                preparedStatement.setString(2, newPassword);

                result = preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
        }
            return result;
    }

    public boolean isCurrentPasswordCorrect(String currentPassword) {
        String storedPassword = "";

        try {
            ConnectDB connectDB = new ConnectDB();
            String sql = "SELECT password FROM employee WHERE firstname = 'admin' ";

            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    storedPassword = resultSet.getString("password");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return currentPassword.equals(storedPassword);
    }
}