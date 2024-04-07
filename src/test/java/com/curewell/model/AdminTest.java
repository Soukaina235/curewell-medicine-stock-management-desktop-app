package com.curewell.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class AdminTest {
    private Admin admin;
    private Employee employee1, employee2;
    List<Employee> employeeList;

    @Before
    public void before(){
        List<Company> companies = new ArrayList<Company>();
        admin = new Admin(0, "fname", "lname", "09877890789", "adresse1", "ADMIN1234", "admin","ADMIN","adminmail@curewell.com", companies);
        employee1 = new Employee(1, "fname", "lname", "061111", "adresse empl", "empl", "empl", "password", "RESP_VENTE");
        employee2 = new Employee(2, "fname", "lname", "061111", "adresse empl", "empl", "empl", "password", "RESP_ACHAT");
        employeeList = new ArrayList<Employee>();
        employeeList.add(employee1);
    }

    @Test
    public void GetTest(){
        assertTrue(admin.getId() == 0
                && admin.getAddress().equals("adresse1")
                && admin.getLastName().equals("lname")
                && admin.getFirstName().equals("fname")
                && admin.getPassword().equals("ADMIN1234")
                && admin.getLogin().equals("admin")
                && admin.getRole().equals("ADMIN")
                && admin.getEmail().equals("adminmail@curewell.com")
                && admin.getPhone().equals("09877890789") );
    }
    @Test
    public void SetTest(){
        admin.setAddress("test");
        admin.setEmail("test");
        admin.setFirstName("test");
        admin.setLastName("test");
        admin.setLogin("test");
        admin.setPassword("test");
        admin.setPhone("test");
        admin.setRole("test");
        assertTrue(admin.getAddress().equals("test")
                && admin.getLastName().equals("test")
                && admin.getFirstName().equals("test")
                && admin.getPassword().equals("test")
                && admin.getLogin().equals("test")
                && admin.getRole().equals("test")
                && admin.getEmail().equals("test")
                && admin.getPhone().equals("test") );
    }
    @Test
    public void AddEmployeeTest(){
        admin.addEmployee(employeeList, employee2);
        assertTrue(employeeList.stream().anyMatch(c -> c.getId() == 2));
    }
    @Test
    public void DeleteEmployeeTest(){
        admin.deleteEmployee(employeeList, employee1);
        assertFalse(employeeList.stream().anyMatch(c -> c.getId() == 1));
    }

}