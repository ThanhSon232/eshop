package com.example.adapter.product;

public class product {
    String id;
    String name;
    String image;
    String description;
    String price;
    Float star;
    Integer quantity;
    Boolean check;

    public product(String id, String name, String image, String description, String price, Float star, Integer quantity, Boolean check) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.star = star;
        this.quantity = quantity;
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public Float getStar() {
        return star;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getCheck() {
        return check;
    }
}
