package com.curewell.model;

public class Medicine {
    private final int id;
    private String name;
    private String description;
    private Category category;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private double price;
    private int quantity;

    public Medicine(int id, String name, String description, Category category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description=description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
    public Medicine(String name, String description, Category category, double price, int quantity) {
        this.id = 0;
        this.name = name;
        this.description=description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean canValidate(int quantityToRemove) {
        if (this.getQuantity() >= quantityToRemove) {
            return true;
        }
        else
            return false;
    }

    public void decreaseQuantity(int quantityToRemove) {
        int newQuantity = getQuantity() - quantityToRemove;
        this.setQuantity(newQuantity);
    }

    public void increaseQuantity(int quantityToAdd) {
        int newQuantity = getQuantity() + quantityToAdd;
        this.setQuantity(newQuantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Medicine medicine = (Medicine) obj;
        return id == medicine.id;
    }
}
