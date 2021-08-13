package org.example.framework.Items;

// Товар
public class Item {
    private String name;
    private double price;
    private int warranty;  //Количество лет дополнительной гарантии
    private double warrantyPrice;   // Цена гарантии
    private int amount; // количество штук

    public Item(String name, double price, int warranty, double warrantyPrice) {
        this.name = name;
        this.price = price;
        this.warranty = warranty;
        this.warrantyPrice = warrantyPrice;
    }

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        this.warranty = 0;
    }

    public Item(String name, double price, int warranty, int amount) {
        this.name = name;
        this.price = price;
        this.warranty = warranty;
        this.amount = amount;
    }

    public void addItem(){++amount;}



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public int getAmount() {return amount; }

    public void setAmount(int amount){this.amount = amount; }

    public double getWarrantyPrice() {
        return warrantyPrice;
    }

    public void setWarrantyPrice(double warrantyPrice) {
        this.warrantyPrice = warrantyPrice;
    }
}
