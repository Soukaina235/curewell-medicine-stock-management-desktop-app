package com.curewell.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void testEmployeeGettersAndConstructor1() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "comp1", "comp1@gmail.com", "0609080706", "adr1"));
        companies.add(new Company(2, "comp2", "comp2@gmail.com", "06123456", "adr2"));
        Employee employee = new Employee(1, "emp1", "EMP1", "0506040809", "adr1", "emp1", "RESP_VENTE", "pwd1", "emp1@gmail.com", companies);
        assertEquals(1, employee.getId());
        assertEquals("emp1", employee.getFirstName());
        assertEquals("EMP1", employee.getLastName());
        assertEquals("0506040809", employee.getPhone());
        assertEquals("adr1", employee.getAddress());
        assertEquals("emp1", employee.getLogin());
        assertEquals("RESP_VENTE", employee.getRole());
        assertEquals("pwd1", employee.getPassword());
        assertEquals("emp1@gmail.com", employee.getEmail());
        assertEquals(new Company(1, "comp1","comp1@gmail.com", "0609080706", "adr1"), employee.getCompanies().get(0));
        assertTrue(Objects.equals(new Company(2, "comp2","comp2@gmail.com", "06123456", "adr2"), employee.getCompanies().get(1)));
    }

    @Test
    public void testEmployeeGettersAndConstructor2() {
        Employee employee = new Employee(1, "emp1", "EMP1", "emp1@gmail.com", "adr1", "0506040809", "emp1", "pwd1", "RESP_VENTE");
        assertEquals(1, employee.getId());
        assertEquals("emp1", employee.getFirstName());
        assertEquals("EMP1", employee.getLastName());
        assertEquals("0506040809", employee.getPhone());
        assertEquals("adr1", employee.getAddress());
        assertEquals("emp1", employee.getLogin());
        assertEquals("RESP_VENTE", employee.getRole());
        assertEquals("pwd1", employee.getPassword());
        assertEquals("emp1@gmail.com", employee.getEmail());
    }

    @Test
    public void testEmployeeGettersAndConstructor3() {
        Employee employee = new Employee("emp1", "EMP1", "emp1@gmail.com", "adr1", "0506040809", "emp1", "pwd1", "RESP_VENTE");
        assertEquals(0, employee.getId());
        assertEquals("emp1", employee.getFirstName());
        assertEquals("EMP1", employee.getLastName());
        assertEquals("0506040809", employee.getPhone());
        assertEquals("adr1", employee.getAddress());
        assertEquals("emp1", employee.getLogin());
        assertEquals("RESP_VENTE", employee.getRole());
        assertEquals("pwd1", employee.getPassword());
        assertEquals("emp1@gmail.com", employee.getEmail());
    }

    @Test
    public void testSetters() {
        Employee employee = new Employee("emp1", "EMP1", "emp1@gmail.com", "adr1", "0506040809", "emp1", "pwd1", "RESP_VENTE");

        employee.setFirstName("emp1New");
        assertEquals("emp1New", employee.getFirstName());

        employee.setLastName("EMP1New");
        assertEquals("EMP1New", employee.getLastName());

        employee.setAddress("adr123 New");
        assertEquals("adr123 New", employee.getAddress());

        employee.setPhone("0601020304");
        assertEquals("0601020304", employee.getPhone());

        employee.setLogin("login1 New");
        assertEquals("login1 New", employee.getLogin());

        employee.setPassword("passwd1 New");
        assertEquals("passwd1 New", employee.getPassword());

        employee.setEmail("employee1NEW@gmail.com");
        assertEquals("employee1NEW@gmail.com", employee.getEmail());

        employee.setRole("ADMIN");
        assertEquals("ADMIN", employee.getRole());
    }

    @Test
    public void testToString() {
        Employee employee = new Employee(1, "emp1", "EMP1", "emp1@gmail.com", "adr1", "0506040809", "emp1", "pwd1", "RESP_VENTE");
        String toString = "Employee{id=1, firstName='emp1', lastName='EMP1', phone='0506040809', address='adr1', password='pwd1', email='emp1@gmail.com', companies=null}";
        assertEquals(toString, employee.toString());
    }
}