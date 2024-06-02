package com.example.easyshop;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easyshop.adapter.CartAdapter;
import com.example.easyshop.models.Product;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private List<Product> cartProductList;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_cart);

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi data keranjang belanja
        cartProductList = new ArrayList<>();

        // Inisialisasi adapter dan set ke RecyclerView
        cartAdapter = new CartAdapter(this, cartProductList, new CartAdapter.OnCartUpdatedListener() {
            @Override
            public void onCartUpdated() {
                // Callback jika ada pembaruan pada keranjang belanja
                // Di sini Anda dapat memperbarui tampilan total harga atau melakukan tindakan lainnya
            }
        });
        recyclerView.setAdapter(cartAdapter);
    }
}
