package com.example.easyshop.api;

import com.example.easyshop.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("products")
    Call<ProductResponse> getProducts();

    @GET("search")
    Call<ProductResponse> searchProducts(@Query("query") String query);






}


