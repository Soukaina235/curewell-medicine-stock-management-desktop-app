package com.curewell.dao.impl;

import com.curewell.Application;
import com.curewell.sql.ConnectDB;
import com.curewell.dao.EmployeeDao;
import com.curewell.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public Employee findEmployeeById(int id) {
            ConnectDB connectDB = null;
            Employee employee = null;
            String req = "SELECT * FROM employee WHERE id  = " + id;
            try {
                connectDB = new ConnectDB();
                ResultSet rs = connectDB.getStatement().executeQuery(req);
                if (rs.next()) {
                    employee = new Employee(rs.getInt("id"),
                            rs.getString(("firstname")),
                            rs.getString("lastname"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("login"),
                            rs.getString("ROLE"),
                            rs.getString("email"),
                            rs.getString("password")
                            );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        return employee;
   }

    @Override
    public Employee findEmployeeByName(String name) {
        ConnectDB connectDB = null;
        Employee employee = null;
        String req = "SELECT * FROM employee WHERE lastname  = " + name;
        try {
            connectDB = new ConnectDB();
            ResultSet rs = connectDB.getStatement().executeQuery(req);
            if (rs.next()) {
                employee = new Employee(rs.getInt("id"),
                        rs.getString(("firstname")),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("ROLE")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    @Override
    public boolean validateLogin(String login, String password) {
        ConnectDB connectDB = null;
        boolean isValid = false;
        String req = "SELECT * FROM employee WHERE login = ? AND password = ?";
        try {
            connectDB = new ConnectDB();
            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(req)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                ResultSet rs = preparedStatement.executeQuery();
                isValid = rs.next();
                Application.currentUser = new Employee(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("address"), rs.getString("phone"), rs.getString("login"), rs.getString("password"), rs.getString("role"));

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isValid;
    }
    @Override
    public String getRoleByLoginAndPassword(String login, String password) {
        ConnectDB connectDB = null;
        String role = null;
        String req = "SELECT role FROM employee WHERE login=? AND password=?";

        try {
            connectDB = new ConnectDB();
            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(req)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    role = rs.getString("ROLE");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return role;
    }

    @Override
    public Employee findEmployeeByLogin(String login) {
        ConnectDB connectDB = null;
        Employee employee = null;
        String req = "SELECT * FROM employee WHERE login = '" + login + "'";
        try {
            connectDB = new ConnectDB();
            ResultSet rs = connectDB.getStatement().executeQuery(req);
            if (rs.next()) {
                employee = new Employee(rs.getInt("id"),
                        rs.getString(("firstname")),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("ROLE")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }
}