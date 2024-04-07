package com.curewell.dao.impl;

import com.curewell.model.Company;
import com.curewell.model.StatusCompany;
import com.curewell.dao.CompanyDao;
import com.curewell.sql.ConnectDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyDaoImpl implements CompanyDao {
    @Override
    public Company findCompanyById(int id) {
        ConnectDB connectDB = null;
        Company company = null;
        String req = "SELECT * FROM company WHERE id  = " + id;
        try {
            connectDB = new ConnectDB();
            ResultSet rs = connectDB.getStatement().executeQuery(req);
            if (rs.next()) {
                company = new Company(rs.getInt("id"),
                        rs.getString(("name")),
                        rs.getString(("gmail")),
                        rs.getString("phone"),
                        rs.getString("address"),
                        StatusCompany.valueOf(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return company;
    }

    @Override
    public Company findCompanyByName(String name) {
        ConnectDB connectDB = null;
        Company company = null;
        String req = "SELECT * FROM company WHERE name='" + name + "'";
        try {
            connectDB = new ConnectDB();
            ResultSet rs = connectDB.getStatement().executeQuery(req);
            if (rs.next()) {
                company = new Company(rs.getInt("id"),
                        rs.getString(("name")),
                        rs.getString(("gmail")),
                        rs.getString("phone"),
                        rs.getString("address"),
                        StatusCompany.valueOf(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return company;
    }


    @Override
    public List<Company> selectAll() {
        ConnectDB connectDB = null;
        List<Company> companies = new ArrayList<>();

        String req = "SELECT * FROM company";

        try {
            connectDB = new ConnectDB();
            PreparedStatement preparedStatement = connectDB.conn.prepareStatement(req);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Company company = new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gmail"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        StatusCompany.valueOf(rs.getString("status"))
                );
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (connectDB != null) {
                try{
                    connectDB.conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return companies;
    }

    @Override
    public int add(Company company) {
        ConnectDB connectDB = null;
        String req = "INSERT INTO company (name, gmail, phone, address, status) VALUES (?, ?, ?, ?, ?)";
        int result =0;

        try {
            connectDB = new ConnectDB();
            PreparedStatement preparedStatement = connectDB.conn.prepareStatement(req);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getGmail());
            preparedStatement.setString(3, company.getPhone());
            preparedStatement.setString(4, company.getAddress());
            preparedStatement.setString(5, company.getStatus().name());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (connectDB != null) {
                try {
                    connectDB.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    public int updateCompany(int idComp,Company comp){
        ConnectDB connectDB = null;
        String req = "UPDATE company SET name=?,gmail=?,phone=?, address=?,status=? where id=?";
        int result =0;

        try {
            connectDB = new ConnectDB();
            PreparedStatement preparedStatement = connectDB.conn.prepareStatement(req);
            preparedStatement.setString(1, comp.getName());
            preparedStatement.setString(2, comp.getGmail());
            preparedStatement.setString(3, comp.getPhone());
            preparedStatement.setString(4, comp.getAddress());
            preparedStatement.setString(5, comp.getStatus().name());
            preparedStatement.setInt(6, idComp);

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (connectDB != null) {
                try {
                    connectDB.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    @Override
    public int deleteCompany(int CompId){
        ConnectDB connectDB = null;
        String req = "DELETE FROM company where id=?";
        int result =0;
        try {
            connectDB = new ConnectDB();
            PreparedStatement preparedStatement = connectDB.conn.prepareStatement(req);
            preparedStatement.setInt(1, CompId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (connectDB != null) {
                try {
                    connectDB.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public List<String> selectAllNames(){
        return selectAll().stream().map(Company::getName).collect(Collectors.toList());
    }
}

