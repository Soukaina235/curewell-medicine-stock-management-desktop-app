package com.curewell.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class RespVenteTest {

    @Test
    public void testRespVenteGettersAndConstructor1() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "comp1", "comp1@gmail.com", "0609080706", "adr1"));
        companies.add(new Company(2, "comp2", "comp2@gmail.com", "06123456", "adr2"));
        RespAchat respAchat = new RespAchat(1, "emp1", "EMP1", "0506040809", "adr1", "emp1", "RESP_VENTE", "pwd1", "emp1@gmail.com", companies);
        assertEquals(1, respAchat.getId());
        assertEquals("emp1", respAchat.getFirstName());
        assertEquals("EMP1", respAchat.getLastName());
        assertEquals("0506040809", respAchat.getPhone());
        assertEquals("adr1", respAchat.getAddress());
        assertEquals("emp1", respAchat.getLogin());
        assertEquals("RESP_VENTE", respAchat.getRole());
        assertEquals("pwd1", respAchat.getPassword());
        assertEquals("emp1@gmail.com", respAchat.getEmail());
        assertEquals(new Company(1, "comp1","comp1@gmail.com", "0609080706", "adr1"), respAchat.getCompanies().get(0));
        assertTrue(Objects.equals(new Company(2, "comp2","comp2@gmail.com", "06123456", "adr2"), respAchat.getCompanies().get(1)));
    }

    @Test
    public void testRespVenteGettersAndConstructor2() {
        RespAchat respAchat = new RespAchat(1, "emp1", "EMP1", "0506040809", "adr1", "emp1", "RESP_VENTE", "pwd1", "emp1@gmail.com");
        assertEquals(1, respAchat.getId());
        assertEquals("emp1", respAchat.getFirstName());
        assertEquals("EMP1", respAchat.getLastName());
        assertEquals("0506040809", respAchat.getPhone());
        assertEquals("adr1", respAchat.getAddress());
        assertEquals("emp1", respAchat.getLogin());
        assertEquals("RESP_VENTE", respAchat.getRole());
        assertEquals("pwd1", respAchat.getPassword());
        assertEquals("emp1@gmail.com", respAchat.getEmail());
    }

}