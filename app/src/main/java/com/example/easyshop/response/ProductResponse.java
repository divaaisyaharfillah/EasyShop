package com.example.easyshop.response;

import com.example.easyshop.models.Product;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductResponse {
    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getResults() {
        return products;
    }


}

