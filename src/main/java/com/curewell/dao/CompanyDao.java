package com.curewell.dao;

import com.curewell.model.Company;

import java.util.List;

public interface CompanyDao {
    Company findCompanyById(int id);

    Company findCompanyByName(String name);

    List<Company> selectAll();
    int add(Company company);
    //update function
    int updateCompany(int idComp,Company comp);
    int deleteCompany(int compId);

    List<String> selectAllNames();
}
