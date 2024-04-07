package com.curewell.dao;

import com.curewell.model.Employee;
import java.util.List;

public interface AdminDao {

     int addEmployee(Employee employee) ;
    List<Employee> getAllEmployees();
    int deleteEmployee(int employeeId);
    int modifyEmployee(Employee employee);
    int updateAdminCredentials(String newEmail, String newPassword);
}
