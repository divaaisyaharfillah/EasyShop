package com.example.easyshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyshop.DetailActivity;
import com.example.easyshop.R;
import com.example.easyshop.models.Product;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ProductViewHolder> {


    private Context context;
    private List<Product> productList;

    public SearchAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewProduct;
        TextView textViewTitle, textViewCategory;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);


            itemView.setOnClickListener(this);
        }

        public void bind(Product product) {
            textViewTitle.setText(product.getTitle());
            textViewCategory.setText(product.getCategory());
            Glide.with(itemView.getContext())
                    .load(product.getThumbnail())
                    .into(imageViewProduct);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Product product = productList.get(position);
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
