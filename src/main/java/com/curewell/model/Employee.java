package com.curewell.model;

import com.curewell.dao.impl.TransactionDaoImpl;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Objects;

public class Employee {
    protected final int id;
    protected String firstName;
    protected String lastName;
    protected String phone;
    protected String address;
    protected String role;
    protected String login;
    protected String password;
    protected String email;
    protected List<Company> companies;
    protected double totalTransactions;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Employee(int id, String firstName, String lastName, String phone, String address, String login, String role, String password, String email, List<Company> companies) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.email = email;
        this.login = login;
        this.role = role;
        this.companies = companies;
    }

    public Employee(int id, String firstName, String lastName,String email, String address, String phone,String login, String password,String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Employee(String firstName, String lastName,String email, String address,String phone, String login, String password,String role) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTotalTransactions(Double t) {this.totalTransactions=t;}

    public double getalltransactions2() {
        List<Transaction> transactions = new TransactionDaoImpl().findAll();
        List<Transaction> a = transactions.stream()
                .filter(transaction -> transaction.getType().toString().equals("Demande"))
                .toList().stream().filter(n -> n.getEmployee().lastName.equals(this.lastName)).toList();
        return a.stream().mapToDouble(Transaction::getTotal)
                .sum();

    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", companies=" + companies +
                '}';
    }

    public static void addCompany(@org.jetbrains.annotations.NotNull List<Company> companies, Company company) {
        companies.add(company);
    }

    public static void deleteCompany(@NotNull List<Company> companies, Company company) {
        companies.removeIf(comp -> Objects.equals(comp, company));
    }
}