package com.curewell.model;

import java.util.List;

public class RespVente extends Employee{

    protected List<Transaction> transactions;

    public RespVente(int id, String firstName, String lastName, String phone, String address,String login,String role, String password, String email, List<Company> companies, List<Transaction> transactions) {
        super(id, firstName, lastName, phone, address,login,role, password, email, companies);
        this.transactions = transactions;
    }

    public RespVente(int id, String firstName, String lastName, String phone, String address,String login,String role, String password, String email) {
        super(id, firstName, lastName, email, address,phone, login, password, role);
    }
}