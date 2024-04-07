package com.curewell.dao;

import com.curewell.model.Medicine;

import java.util.List;

public interface MedicineDao {
    Medicine findMedicineById(int id);
    List<Medicine> selectAll();
    int addMedicine(Medicine medicine);
    Medicine findMedicineByName(String name);
    List<String> selectAllNames();
    int deleteMedicine(int MedId);
    int updateMedicine(int idMed, Medicine med);
}
