package com.example.easyshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyshop.R;
import com.example.easyshop.adapter.CartAdapter;
import com.example.easyshop.CartHelper;
import com.example.easyshop.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.OnCartUpdatedListener {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartProductList;
    private TextView textViewTotalPrice;

    public CartFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.rv_cart);
        textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        cartProductList = CartHelper.getCart(requireContext());
        if (cartProductList == null) {
            cartProductList = new ArrayList<>();
        }
        cartAdapter = new CartAdapter(requireContext(), cartProductList, this);
        recyclerView.setAdapter(cartAdapter);

        updateTotalPrice();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cartProductList = CartHelper.getCart(requireContext());
        if (cartProductList == null) {
            cartProductList = new ArrayList<>();
        }
        cartAdapter.setProductList(cartProductList); // Update adapter's data
        updateTotalPrice();
    }

    @Override
    public void onCartUpdated() {
        updateTotalPrice();
        // Simpan perubahan keranjang belanja yang diperbarui ke preferensi bersama
        CartHelper.saveCart(requireContext(), cartProductList);
    }

    private void updateTotalPrice() {
        double totalPrice = CartHelper.getTotalPrice(requireContext());
        textViewTotalPrice.setText(String.format("Total Price: $%.2f", totalPrice));
    }
}