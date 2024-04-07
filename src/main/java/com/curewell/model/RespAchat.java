package com.curewell.model;

import java.util.ArrayList;
import java.util.List;

public class RespAchat extends Employee{

    protected List<Transaction> transactions;

    public RespAchat(int id, String firstName, String lastName, String phone, String address,String login,String role, String password, String email, List<Company> companies) {
        super(id, firstName, lastName, phone, address,login,role, password, email, companies);
        transactions = new ArrayList<>();
    }

    public RespAchat(int id, String firstName, String lastName, String phone, String address,String login,String role, String password, String email) {
        super(id, firstName, lastName, email, address,phone, login, password, role);
    }

}
