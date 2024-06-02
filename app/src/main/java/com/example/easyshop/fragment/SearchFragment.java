package com.example.easyshop.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyshop.R;
import com.example.easyshop.adapter.ProductAdapter;
import com.example.easyshop.adapter.SearchAdapter;
import com.example.easyshop.api.ApiConfig;
import com.example.easyshop.api.ApiService;
import com.example.easyshop.models.Product;
import com.example.easyshop.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchAdapter searchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_search, container, false);

        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.rv_search);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(searchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProduct(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    // Jika teks pencarian kosong, atur kembali adapter ke daftar produk awal
                    searchAdapter.setProductList(new ArrayList<>());
                } else {
                    searchProduct(newText);
                }
                return false;
            }


        });

        return view;
    }

    private void searchProduct(String query) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiConfig.getClient().create(ApiService.class);
        Call<ProductResponse> call = apiService.getProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> allProducts = response.body().getResults();
                    List<Product> filteredProducts = new ArrayList<>();

                    // Filter products based on query
                    for (Product product : allProducts) {
                        if (product.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                product.getCategory().toLowerCase().contains(query.toLowerCase())) {
                            filteredProducts.add(product);
                        }
                    }

                    // Update adapter with filtered products
                    searchAdapter.setProductList(filteredProducts);

                    // If no products found, show appropriate message
                    if (filteredProducts.isEmpty()) {
                        showErrorMessage("No products found");
                    }
                } else {
                    showErrorMessage("Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showErrorMessage("Failed to connect to server");
            }
        });
    }




    private void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
