package com.curewell.dao;

import com.curewell.model.Employee;

public interface EmployeeDao {
    Employee findEmployeeById(int id);

    Employee findEmployeeByName(String name);

    boolean validateLogin(String username, String password);

    String getRoleByLoginAndPassword(String login, String password);
    public Employee findEmployeeByLogin(String login);
}
