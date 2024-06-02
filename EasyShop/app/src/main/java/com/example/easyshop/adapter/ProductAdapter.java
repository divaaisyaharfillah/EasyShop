package com.example.easyshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyshop.CartHelper;
import com.example.easyshop.DetailActivity;
import com.example.easyshop.R;
import com.example.easyshop.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private List<Product> filteredList;
    private OnProductClickListener onProductClickListener;

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener onProductClickListener) {
        this.context = context;
        this.productList = productList;
        this.filteredList = new ArrayList<>(productList);
        this.onProductClickListener = onProductClickListener;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        this.filteredList = new ArrayList<>(productList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view, onProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = filteredList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewProduct;
        TextView textViewTitle;
        ImageView iconCart;

        OnProductClickListener onProductClickListener;
        Product product;

        public ProductViewHolder(@NonNull View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            iconCart = itemView.findViewById(R.id.iconCart);
            this.onProductClickListener = onProductClickListener;
            itemView.setOnClickListener(this);

            iconCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartHelper.addToCart(itemView.getContext(), product);
                    Toast.makeText(itemView.getContext(), product.getTitle() + " added to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(Product product) {
            this.product = product;
            textViewTitle.setText(product.getTitle());
            Glide.with(itemView.getContext())
                    .load(product.getThumbnail())
                    .into(imageViewProduct);
        }

        @Override
        public void onClick(View view) {
            onProductClickListener.onProductClick(product);
        }
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(productList);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Product product : productList) {
                if (product.getTitle().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }
}
