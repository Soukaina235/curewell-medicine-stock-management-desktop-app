package com.curewell.model;


public class Company {
    private final int id;
    private String name;
    private String gmail;
    private String phone;
    private String address;
    private StatusCompany status;

    public Company(int id,String name,String gmail, String phone, String address) {
        super();
        this.id = id;
        this.name = name;
        this.gmail=gmail;
        this.phone = phone;
        this.address = address;
        this.status = StatusCompany.Normal;
    }

    public Company(String name,String gmail, String phone, String address) {
        super();
        this.id = 100;
        this.name = name;
        this.gmail=gmail;
        this.phone = phone;
        this.address = address;
        this.status = StatusCompany.Normal;
    }

    public Company(int id,String name,String gmail, String phone, String address, StatusCompany status) {
        super();
        this.id = id;
        this.name = name;
        this.gmail=gmail;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public Company(String name,String gmail, String phone, String address, StatusCompany status) {
        super();
        this.id = 0;
        this.name = name;
        this.gmail=gmail;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public StatusCompany getStatus() {
        return status;
    }

    // public void setId(int id){this.id=id};

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(StatusCompany status) {
        this.status = status;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gmail='" + gmail + '\'' +
                ", Phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Company company = (Company) obj;
        return company.id == id;
    }

}
