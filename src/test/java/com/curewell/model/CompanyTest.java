package com.curewell.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class CompanyTest {

    @Test
    public void testCompanyGettersAndConstructor1() {
        Company company = new Company(1, "company1", "company1@gmail.com", "1234567890", "adr");
        assertEquals(1, company.getId());
        assertEquals("company1", company.getName());
        assertEquals("company1@gmail.com", company.getGmail());
        assertEquals("1234567890", company.getPhone());
        assertEquals("adr", company.getAddress());
        assertEquals(StatusCompany.Normal, company.getStatus());
    }

    @Test
    public void testCompanyGettersAndConstructor2() {
        Company company = new Company("company1", "company1@gmail.com", "1234567890", "adr");
        assertEquals(100, company.getId());
        assertEquals("company1", company.getName());
        assertEquals("company1@gmail.com", company.getGmail());
        assertEquals("1234567890", company.getPhone());
        assertEquals("adr", company.getAddress());
        assertEquals(StatusCompany.Normal, company.getStatus());
    }

    @Test
    public void testCompanyGettersAndConstructor3() {
        Company company = new Company(1, "company1", "company1@gmail.com", "1234567890", "adr", StatusCompany.VIP);
        assertEquals(1, company.getId());
        assertEquals("company1", company.getName());
        assertEquals("company1@gmail.com", company.getGmail());
        assertEquals("1234567890", company.getPhone());
        assertEquals("adr", company.getAddress());
        assertEquals(StatusCompany.VIP, company.getStatus());
    }

    @Test
    public void testCompanyGettersAndConstructor4() {
        Company company = new Company("company1", "company1@gmail.com", "1234567890", "adr", StatusCompany.VIP);
        assertEquals(0, company.getId());
        assertEquals("company1", company.getName());
        assertEquals("company1@gmail.com", company.getGmail());
        assertEquals("1234567890", company.getPhone());
        assertEquals("adr", company.getAddress());
        assertEquals(StatusCompany.VIP, company.getStatus());
    }

    @Test
    public void testCompanySetters() {
        Company company = new Company(2, "company", "company@gmail.com", "0506070809", "adr");
        company.setName("companyNew");
        company.setGmail("company@gmail.com");
        company.setPhone("0506070809");
        company.setAddress("adr");
        company.setStatus(StatusCompany.Normal);

        assertEquals("companyNew", company.getName());
        assertEquals("company@gmail.com", company.getGmail());
        assertEquals("0506070809", company.getPhone());
        assertEquals("adr", company.getAddress());
        assertEquals(StatusCompany.Normal, company.getStatus());
    }

    @Test
    public void testCompanyEquality() {
        Company company1 = new Company(3, "company1", "company1@gmail.com", "0505050505", "adr1");
        Company company2 = new Company(3, "company2", "company2@gmail.com", "0506060606", "adr2");
        Company company3 = new Company(4, "company1", "company1@gmail.com", "0505050505", "adr1");

        assertEquals(company1, company2);
        assertNotEquals(company1, company3);
    }

    @Test
    public void testTooString() {
        Company company1 = new Company(3, "company1", "company1@gmail.com", "0505050505", "adr1");
        String toString = "Company{id=3, name='company1', gmail='company1@gmail.com', Phone='0505050505', address='adr1', status=Normal}";
        assertEquals(toString, company1.toString());
    }
}
