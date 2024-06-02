package com.example.easyshop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyshop.CartHelper;
import com.example.easyshop.DetailActivity;
import com.example.easyshop.R;
import com.example.easyshop.models.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Product> cartProductList;
    private OnCartItemDeleteListener onDeleteListener;
    private OnCartUpdatedListener onCartUpdatedListener;

    public CartAdapter(Context context, List<Product> cartProductList, OnCartUpdatedListener onCartUpdatedListener) {
        this.context = context;
        this.cartProductList = cartProductList;
        this.onCartUpdatedListener = onCartUpdatedListener;
    }

    public void setProductList(List<Product> productList) {
        this.cartProductList = productList;
        notifyDataSetChanged();
    }

    public void setOnCartItemDeleteListener(OnCartItemDeleteListener listener) {
        this.onDeleteListener = listener;
    }

    public interface OnCartItemDeleteListener {
        void onDelete(int position);
    }

    public interface OnCartUpdatedListener {
        void onCartUpdated();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartProductList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewProduct, imageViewDelete;
        TextView textViewTitle, textViewPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);

            itemView.setOnClickListener(this);
            imageViewDelete.setOnClickListener(this);
        }

        public void bind(Product product) {
            textViewTitle.setText(product.getTitle());
            textViewPrice.setText(String.valueOf(product.getPrice()));
            Glide.with(itemView.getContext()).load(product.getThumbnail()).into(imageViewProduct);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (v.getId() == R.id.imageViewDelete) {
                if (onDeleteListener != null) {
                    onDeleteListener.onDelete(position);
                }
                cartProductList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartProductList.size());
                if (onCartUpdatedListener != null) {
                    onCartUpdatedListener.onCartUpdated();
                }
                // Perbarui keranjang belanja yang disimpan
                CartHelper.saveCart(itemView.getContext(), cartProductList);
            } else {
                Context context = itemView.getContext();
                Product product = cartProductList.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("product_image", product.getThumbnail());
                intent.putExtra("product_brand", product.getTitle());
                intent.putExtra("product_rating", product.getRating());
                intent.putExtra("product_category", product.getCategory());
                intent.putExtra("product_price", product.getPrice());
                context.startActivity(intent);
            }
        }
    }
}
