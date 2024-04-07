package com.curewell.dao.impl;

import com.curewell.Application;
import com.curewell.model.Category;
import com.curewell.model.Transaction;
import com.curewell.dao.MedicineDao;
import com.curewell.model.Medicine;
import com.curewell.sql.ConnectDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MedicineDaoImpl implements MedicineDao {

    public void updateStockMedicines(Transaction transaction) {

        for (Map.Entry<Medicine, Integer> entry : transaction.getMedicineQuantityMap().entrySet()) {
            updateStockMedicine(entry.getKey(), entry.getValue());
        }
    }

    public int updateStockMedicine(Medicine medicine, int quantity) {
        ConnectDB connectDB = null;
        int result = 0;

        int updatedQuantity;

//        if ( Application.currentUser.getRole()  == "RESP_ACHAT") {
//            updatedQuantity = medicine.getQuantity() + quantity;
//
//        } else if(Application.currentUser.getRole() == "RESP_VENTE"){
//            updatedQuantity = medicine.getQuantity() - quantity;
//        } else {
//            updatedQuantity = medicine.getQuantity();
//        }

        String login = Application.currentUser.getLogin();
        String pwd =  Application.currentUser.getPassword();

        System.out.println(login);
        System.out.println(pwd);

        String role = new EmployeeDaoImpl().getRoleByLoginAndPassword(login, pwd);

        System.out.println(role);

        if (Objects.equals(role, "RESP_ACHAT")) {
            updatedQuantity = medicine.getQuantity() + quantity;
        } else if(Objects.equals(role, "RESP_VENTE")){
            updatedQuantity = medicine.getQuantity() - quantity;
        } else {
            updatedQuantity = medicine.getQuantity();
        }

        String req = "UPDATE medicine " +
                "SET quantity='" + updatedQuantity + "' " +
                "WHERE id='" + medicine.getId() + "'";

        try {
            connectDB = new ConnectDB();
            result = connectDB.getStatement().executeUpdate(req);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public Medicine findMedicineById(int id) {
        ConnectDB connectDB = null;
        Medicine medicine = null;
        String req = "SELECT * FROM medicine WHERE id  = " + id;
        try {
            connectDB = new ConnectDB();
            ResultSet rs = connectDB.getStatement().executeQuery(req);
            if (rs.next()) {
                medicine = new Medicine(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        Category.valueOf(rs.getString("category")),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return medicine;
    }

    @Override
    public Medicine findMedicineByName(String name) {
        ConnectDB connectDB = null;
        Medicine medicine = null;
        String req = "SELECT * FROM medicine WHERE name='" + name + "'";
        try {
            connectDB = new ConnectDB();
            ResultSet rs = connectDB.getStatement().executeQuery(req);
            if (rs.next()) {
                medicine = new Medicine(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        Category.valueOf(rs.getString("category")),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return medicine;
    }

    public List<Medicine> selectAll() {
        ConnectDB connectDB = null;
        List<Medicine> medicines = new ArrayList<>();
        String req = "SELECT * FROM medicine";
        try {
            connectDB = new ConnectDB();
            ResultSet rs = connectDB.getStatement().executeQuery(req);
            while(rs.next()) {
                medicines.add(new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        Category.valueOf(rs.getString("category")),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return medicines;
    }

    @Override
    public List<String> selectAllNames() {
        return selectAll().stream().map((m) -> m.getName()).collect(Collectors.toList());
    }
    @Override
    public int addMedicine(Medicine medicine) {
        ConnectDB connectDB = null;
        int result = 0;

        String sql = "INSERT INTO medicine (name,description,category,price,Quantity) " +
                "VALUES (?, ?, ?,?, ?)";

        try {
            connectDB = new ConnectDB();

            try (PreparedStatement preparedStatement = connectDB.conn.prepareStatement(sql)) {
                preparedStatement.setString(1, medicine.getName());
                preparedStatement.setString(2, medicine.getDescription());
                preparedStatement.setString(3, medicine.getCategory().name());
                preparedStatement.setDouble(4, medicine.getPrice());
                preparedStatement.setInt(5, medicine.getQuantity());
                result = preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
    public int updateMedicine(int idMed, Medicine med){
        ConnectDB connectDB = null;
        String req = "UPDATE medicine SET name=?,description=?,category=?, price=?,quantity=? where id=?";
        int result =0;

        try {
            connectDB = new ConnectDB();
            PreparedStatement preparedStatement = connectDB.conn.prepareStatement(req);
            preparedStatement.setString(1, med.getName());
            preparedStatement.setString(2, med.getDescription());
            preparedStatement.setString(3, med.getCategory().name());
            preparedStatement.setDouble(4, med.getPrice());
            preparedStatement.setInt(5, med.getQuantity());
            preparedStatement.setInt(6, idMed);

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
    public int deleteMedicine(int MedId){
        ConnectDB connectDB = null;
        String req = "DELETE FROM medicine where id=?";
        int result =0;
        try {
            connectDB = new ConnectDB();
            PreparedStatement preparedStatement = connectDB.conn.prepareStatement(req);
            preparedStatement.setInt(1, MedId);
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
}