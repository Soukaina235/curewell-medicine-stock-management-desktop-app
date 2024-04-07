package com.curewell.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Transaction {
    protected int id;
    protected double total;
    protected double discount;
    protected final Date dateCreation;

    protected StatusTransaction status;
    protected final Employee employee;
    protected final Company company;
    protected Date dateValidation;
    protected Date dateCancellation;
    protected Map<Medicine, Integer> medicineQuantityMap;
    protected TypeTransaction type;

    public Transaction(int id, double discount, Date dateCreation, Employee employee, Company company, Map<Medicine, Integer> medicineQuantityMap) {
        this.id = id;
        this.discount = discount;
        this.dateCreation = dateCreation;
        this.status = StatusTransaction.Normal;
        this.employee = employee;
        this.company = company;
        this.medicineQuantityMap = medicineQuantityMap;

        double total = 0;
        for (Map.Entry<Medicine, Integer> me : this.medicineQuantityMap.entrySet()) {
            total +=  me.getKey().getPrice() * me.getValue();

        }

        this.discount = discount;
        this.total = total - total * (discount / 100);

        this.dateValidation = null;
        this.dateCancellation =null;

        if (employee.getClass() == RespAchat.class) { type = TypeTransaction.Achat; }
        else if (employee.getClass() == RespVente.class) { type = TypeTransaction.Demande; }
    }

    public Transaction(int id, double total, double discount, Date dateCreation, StatusTransaction status, Employee employee, Company company, Date dateValidation, Date dateCancellation, TypeTransaction type, Map<Medicine, Integer> medicineQuantityMap) {
        this.id = id;
        this.total = total;
        this.discount = discount;
        this.dateCreation = dateCreation;
        this.status = status;
        this.employee = employee;
        this.company = company;
        this.dateValidation = dateValidation;
        this.dateCancellation = dateCancellation;
        this.type = type;
        this.medicineQuantityMap = medicineQuantityMap;
    }

    //
    public Transaction(int id, double total, double discount, Date dateCreation, StatusTransaction status, Employee employee, Company company, Date dateValidation, Date dateCancellation, TypeTransaction type) {
        this.id = id;
        this.total = total;
        this.discount = discount;
        this.dateCreation = dateCreation;
        this.status = status;
        this.employee = employee;
        this.company = company;
        this.dateValidation = dateValidation;
        this.dateCancellation = dateCancellation;
        this.type = type;
    }

    public Transaction(int id, double total, double discount, Date dateCreation, StatusTransaction status, Employee employee, Company company, Date dateValidation, Date dateCancellation) {
        this.id = id;
        this.total = total;
        this.discount = discount;
        this.dateCreation = dateCreation;
        this.status = status;
        this.employee = employee;
        this.company = company;
        this.dateValidation = dateValidation;
        this.dateCancellation = dateCancellation;
        this.medicineQuantityMap = new HashMap<>();

        if (employee.getClass() == RespVente.class) type = TypeTransaction.Demande;
        if (employee.getClass() == RespAchat.class) type = TypeTransaction.Achat;
    }

    //
    public Transaction( double total, double discount, Date dateCreation, StatusTransaction status, Employee employee, Company company, Date dateValidation, Date dateCancellation) {
        this.id = 0;
        this.total = total;
        this.discount = discount;
        this.dateCreation = dateCreation;
        this.status = status;
        this.employee = employee;
        this.company = company;
        this.dateValidation = dateValidation;
        this.dateCancellation = dateCancellation;
        this.medicineQuantityMap = new HashMap<>();

        if (employee.getClass() == RespVente.class) type = TypeTransaction.Demande;
        if (employee.getClass() == RespAchat.class) type = TypeTransaction.Achat;
    }

    public Transaction(int id, double discount, Date dateCreation, Employee employee, Company company) {
        this.id = id;
        this.discount = discount;
        this.dateCreation = dateCreation;
        this.employee = employee;
        this.company = company;
        this.dateValidation = null;
        this.dateCancellation = null;
        this.medicineQuantityMap = new HashMap<>();

//        if (employee.getClass() == RespVente.class) type = TypeTransaction.Demande;
//        if (employee.getClass() == RespAchat.class) type = TypeTransaction.Achat;
    }

    public Transaction(double discount, Date dateCreation, Employee employee, Company company) {
        this.discount = discount;
        this.dateCreation = dateCreation;
        this.employee = employee;
        this.company = company;
        this.status = StatusTransaction.Normal;
        this.dateValidation = null;
        this.dateCancellation = null;
        this.medicineQuantityMap = new HashMap<>();

        if (employee.getClass() == RespVente.class) type = TypeTransaction.Demande;
        if (employee.getClass() == RespAchat.class) type = TypeTransaction.Achat;
    }



    public int getId() {
        return id;
    }
    public double getTotal() {
        return total;
    }

    public double getTotalString() {
        return total;
    }
    public double getDiscount() {
        return discount;
    }
    public Date getDateCreation() {
        return dateCreation;
    }
    public StatusTransaction getStatus() {
        return status;
    }

    public StatusTransaction getStatusString() {
        return status;
    }
    public Employee getEmployee() {
        return employee;
    }
    public Company getCompany() {
        return company;
    }
    public Date getDateValidation() {
        return dateValidation;
    }
    public Date getDateCancellation() {
        return dateCancellation;
    }

    public TypeTransaction getType() {
        return type;
    }

    public Map<Medicine, Integer> getMedicineQuantityMap() {

        return medicineQuantityMap;
    }

    public void setTotal() {
        this.total = 0;
        for (Map.Entry<Medicine, Integer> me : this.medicineQuantityMap.entrySet()) {
            total +=  me.getKey().getPrice() * me.getValue();

        }
        total = total - (discount / 100) * total;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public void setStatus(StatusTransaction status) {
        this.status = status;
    }
    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }
    public void setDateCancellation(Date dateCancellation) {
        this.dateCancellation = dateCancellation;
    }
    public void setMedicineQuantityMap(Map<Medicine, Integer> medicineQuantityMap) {
        this.medicineQuantityMap = medicineQuantityMap;
    }
    public void setType() {
        if (employee.getClass() == RespVente.class) type = TypeTransaction.Demande;
        else type = TypeTransaction.Achat;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String msg =  "Transaction{" +
                "id=" + id +
                ", total=" + total +
                ", discount=" + discount +
                ", dateCreation=" + dateCreation +
                ", status=" + status +
                ", employee=" + employee +
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
        return msg + stringBuilder + "\n\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction transaction = (Transaction) obj;
        return id == transaction.id;
    }

    public String getEmployeeFullName() {
        return employee.getFirstName() + " " + employee.getLastName();
    }

    public String getCompanyName() {
        return company.getName();
    }
    public String getTypeString() {
        return type.toString();
    }

    public void setId(int id) {
        this.id = id;
    }
}
