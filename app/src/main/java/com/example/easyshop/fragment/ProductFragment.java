package com.example.easyshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyshop.DetailActivity;
import com.example.easyshop.R;
import com.example.easyshop.adapter.ProductAdapter;
import com.example.easyshop.api.ApiConfig;
import com.example.easyshop.api.ApiService;
import com.example.easyshop.models.Product;
import com.example.easyshop.response.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ApiService apiService;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        apiService = ApiConfig.getClient().create(ApiService.class);
        fetchData();
        return view;
    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        Call<ProductResponse> call = apiService.getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> productList = response.body().getProducts();
                    adapter = new ProductAdapter(requireContext(), productList, ProductFragment.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra("product_image", product.getThumbnail());
        intent.putExtra("product_brand", product.getTitle());
        intent.putExtra("product_rating", product.getRating());
        intent.putExtra("product_category", product.getCategory());
        intent.putExtra("product_price", product.getPrice());
        startActivity(intent);
    }
}