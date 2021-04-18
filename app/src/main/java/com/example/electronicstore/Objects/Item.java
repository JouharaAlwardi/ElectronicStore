package com.example.electronicstore.Objects;

public class Item {

    private String category,image,manufacturer, price,stock, title;

    public Item() {
    }

    public Item(String category, String image, String manufacturer, String price, String stock, String title) {
        this.category = category;
        this.image = image;
        this.manufacturer = manufacturer;
        this.price = price;
        this.stock = stock;
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getPrice() {
        return price;
    }

    public String getStock() {
        return stock;
    }

    public String getTitle() {
        return title;
    }
}
