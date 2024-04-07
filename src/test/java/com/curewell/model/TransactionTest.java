package com.curewell.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class TransactionTest {
    private Transaction transaction;
    private RespAchat employee1;

    private RespVente employee2;
    private Company company;
    private Medicine medicine;
    private Map<Medicine, Integer> medicineQuantityMap, medicineQuantityMap2;
    private List<Company> companies;
    private Date date1;
    private Date date2;
    private Date date3;

    @Before
    public void before(){
        date1 = new Date(11-1-2024);
        date2 = new Date(12-1-2024);
        date3 = new Date(13-1-2024);
        medicine = new Medicine("med1", "descr 1", Category.Cat1, 100, 2);
        medicineQuantityMap = new HashMap<>();
        medicineQuantityMap.put(medicine,2);
        medicineQuantityMap2 = new HashMap<>();
        medicineQuantityMap2.put(medicine,10);
        companies = new ArrayList<Company>();
        company = new Company(1, "fournisseur", "fournisseur@gmail.com", "12345", "adresse 1 2 3");
        employee1 = new RespAchat(1, "fname", "lname", "061111", "adresse empl", "empl", "empl", "password", "RESP_VENTE");
        employee2 = new RespVente(2, "fname", "lname", "061111", "adresse empl", "empl", "empl", "password", "RESP_ACHAT");
    }

    @Test
    public void ConstructorGetTest1(){
        transaction = new Transaction(1, 10, date1, employee1, company);
        assertTrue(
                transaction.getId() == 1
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getEmployee().equals(employee1)
                        && transaction.getCompany().equals(company)
                        && transaction.getCompanyName().equals(company.getName())
                        && transaction.getEmployeeFullName().equals(employee1.getFirstName() + " " + employee1.getLastName())
        );
    }
    @Test
    public void ConstructorGetTest2(){
        transaction = new Transaction(10, date1, employee1, company);
        assertTrue(
                transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getEmployee().equals(employee1)
                        && transaction.getCompany().equals(company)
                        && transaction.getCompanyName().equals(company.getName())
                        && transaction.getEmployeeFullName().equals(employee1.getFirstName() + " " + employee1.getLastName())
        );
    }
    @Test
    public void ConstructorGetTest3(){
        transaction = new Transaction(1, 100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3, TypeTransaction.Achat);
        assertTrue(
                transaction.getId() == 1
                        && transaction.getTotal() == 100
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getDateValidation().equals(date2)
                        && transaction.getDateCancellation().equals(date3)
                        && transaction.getStatus().equals(StatusTransaction.Validated)
                        && transaction.getType().equals(TypeTransaction.Achat)
                        && transaction.getEmployee().equals(employee1)
                        && transaction.getCompany().equals(company)
                        && transaction.getCompanyName().equals(company.getName())
                        && transaction.getEmployeeFullName().equals(employee1.getFirstName() + " " + employee1.getLastName())
        );
    }
    @Test
    public void ConstructorGetTest4(){
        transaction = new Transaction(100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3);
        assertTrue(
                transaction.getTotal() == 100
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getDateValidation().equals(date2)
                        && transaction.getDateCancellation().equals(date3)
                        && transaction.getStatus().equals(StatusTransaction.Validated)
                        && transaction.getEmployee().equals(employee1)
                        && transaction.getCompany().equals(company)
                        && transaction.getCompanyName().equals(company.getName())
                        && transaction.getEmployeeFullName().equals(employee1.getFirstName() + " " + employee1.getLastName())
        );
    }
    @Test
    public void ConstructorGetTest5(){
        transaction = new Transaction(1,100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3);
        assertTrue(
                transaction.getTotal() == 100
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getDateValidation().equals(date2)
                        && transaction.getDateCancellation().equals(date3)
                        && transaction.getStatus().equals(StatusTransaction.Validated)
                        && transaction.getType().equals(TypeTransaction.Achat)
                        && transaction.getEmployee().equals(employee1)
                        && transaction.getCompany().equals(company)
                        && transaction.getCompanyName().equals(company.getName())
                        && transaction.getEmployeeFullName().equals(employee1.getFirstName() + " " + employee1.getLastName())
        );
    }
    @Test
    public void ConstructorGetTest6(){
        transaction = new Transaction(1,100, 10, date1, StatusTransaction.Validated, employee2, company, date2, date3);
        assertTrue(
                transaction.getTotal() == 100
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getDateValidation().equals(date2)
                        && transaction.getDateCancellation().equals(date3)
                        && transaction.getStatus().equals(StatusTransaction.Validated)
                        && transaction.getType().equals(TypeTransaction.Demande)
                        && transaction.getEmployee().equals(employee2)
                        && transaction.getCompany().equals(company)
                        && transaction.getCompanyName().equals(company.getName())
                        && transaction.getEmployeeFullName().equals(employee1.getFirstName() + " " + employee1.getLastName())
        );
    }
    @Test
    public void ConstructorGetTest7(){
        transaction = new Transaction(1, 100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3, TypeTransaction.Demande);
        assertTrue(
                transaction.getId() == 1
                        && transaction.getTotal() == 100
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getDateValidation().equals(date2)
                        && transaction.getDateCancellation().equals(date3)
                        && transaction.getStatus().equals(StatusTransaction.Validated)
                        && transaction.getType().equals(TypeTransaction.Demande)
                        && transaction.getEmployee().equals(employee1)
                        && transaction.getCompany().equals(company)
                        && transaction.getCompanyName().equals(company.getName())
                        && transaction.getEmployeeFullName().equals(employee1.getFirstName() + " " + employee1.getLastName())
        );
    }
    @Test
    public void ConstructorGetTest8(){
        transaction = new Transaction(1,10, date1,  employee1, company, medicineQuantityMap);
        assertTrue(
                transaction.getId() == 1
                        && transaction.getTotal() == 180
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getStatus().equals(StatusTransaction.Normal)
                        && transaction.getType().equals(TypeTransaction.Achat)
                        && transaction.getEmployee().equals(employee1)
                        && transaction.getCompany().equals(company)
                        && transaction.getMedicineQuantityMap().equals(medicineQuantityMap)
        );
    }
    @Test
    public void ConstructorGetTest9(){
        transaction = new Transaction(1, 100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3, TypeTransaction.Demande, medicineQuantityMap);
        transaction.setType();
        assertTrue(
                transaction.getId() == 1
                        && transaction.getTotal() == 100
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getDateValidation().equals(date2)
                        && transaction.getDateCancellation().equals(date3)
                        && transaction.getStatus().equals(StatusTransaction.Validated)
                        && transaction.getType().equals(TypeTransaction.Achat)
                        && transaction.getEmployee().equals(employee1)
                        && transaction.getCompany().equals(company)
        );
    }
    @Test
    public void ConstructorGetTest10(){
        transaction = new Transaction(1,10, date1,  employee2, company, medicineQuantityMap);
        transaction.setType();
        assertTrue(
                transaction.getId() == 1
                        && transaction.getTotalString() == 180
                        && transaction.getDiscount() == 10
                        && transaction.getDateCreation().equals(date1)
                        && transaction.getStatusString().equals(StatusTransaction.Normal)
                        && transaction.getTypeString().equals("Demande")
                        && transaction.getEmployee().equals(employee2)
                        && transaction.getCompany().equals(company)
                        && transaction.getMedicineQuantityMap().equals(medicineQuantityMap)
        );
    }

    @Test
    public void SetIdTest(){
        transaction = new Transaction(1, 10, date1, employee1, company);
        transaction.setId(2);
        assertTrue(transaction.getId() == 2);
    }
    @Test
    public void SetMedicineMapTest(){
        transaction = new Transaction(1, 10, date1, employee1, company, medicineQuantityMap);
        transaction.setMedicineQuantityMap(medicineQuantityMap2);
        assertTrue(transaction.getMedicineQuantityMap().equals(medicineQuantityMap2));
    }
    @Test
    public void SetDiscountTest(){
        transaction = new Transaction(1, 10, date1, employee1, company);
        transaction.setDiscount(100);
        assertTrue(transaction.getDiscount() == 100);
    }
    @Test
    public void SetTotalTest(){
        transaction = new Transaction(1, 10, date1, employee1, company, medicineQuantityMap);
        transaction.setTotal();
        assertTrue(transaction.getTotal() == 180);
    }
    @Test
    public void SetStatusTest(){
        transaction = new Transaction(1, 100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3, TypeTransaction.Demande);
        transaction.setStatus(StatusTransaction.Canceled);
        assertTrue(transaction.getStatus().equals(StatusTransaction.Canceled));
    }
    @Test
    public void SetValidationDateTest(){
        transaction = new Transaction(1, 100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3, TypeTransaction.Demande);
        transaction.setDateValidation(date1);
        assertTrue(transaction.getDateValidation().equals(date1));
    }
    @Test
    public void SetCancellationDateTest(){
        transaction = new Transaction(1, 100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3, TypeTransaction.Demande);
        transaction.setDateCancellation(date1);
        assertTrue(transaction.getDateCancellation().equals(date1));
    }


    @Test
    public void ToStringTest1(){
        transaction = new Transaction(1, 100, 10, date1, StatusTransaction.Validated, employee1, company, date2, date3, TypeTransaction.Demande, medicineQuantityMap);
        String str = transaction.toString();
        String test = "Transaction{" +
                "id=" + transaction.getId() +
                ", total=" + transaction.getTotalString() +
                ", discount=" + transaction.getDiscount() +
                ", dateCreation=" + transaction.getDateCreation() +
                ", status=" + transaction.getStatusString() +
                ", employee=" + transaction.getEmployee() +
                ", medicineMap=";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Transaction Details:\n");
        for (Map.Entry<Medicine, Integer> entry : medicineQuantityMap.entrySet()) {
            Medicine medicine = entry.getKey();
            int quantity = entry.getValue();

            stringBuilder.append("Medicine: ").append(medicine.getName())
                    .append(", Quantity: ").append(quantity)
                    .append("\n");
        }
        test = test + stringBuilder + "\n\n";
        assertTrue(str.equals(test));
    }

    @Test
    public void EqualsTest(){
        transaction = new Transaction(1, 10, date1, employee1, company);
        Transaction transaction2 = new Transaction(1, 30, date2, employee2, company);
        assertTrue(transaction.equals(transaction2));
    }
}