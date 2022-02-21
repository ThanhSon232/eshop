package com.example.adapter.category;

import com.example.adapter.product.product;

import java.util.List;

public class category {
    List<product> productList;
    String name;

    public category(List<product> productList, String name) {
        this.productList = productList;
        this.name = name;
    }

    public List<product> getProductList() {
        return productList;
    }

    public void setProductList(List<product> productList) {
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
