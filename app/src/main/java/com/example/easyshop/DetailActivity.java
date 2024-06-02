package com.example.easyshop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize views
        ImageView imageViewProduct = findViewById(R.id.imageViewProduct);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewRating = findViewById(R.id.textViewRating);
        TextView textViewCategory = findViewById(R.id.textViewCategory);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        TextView textViewStok = findViewById(R.id.textViewStock);

        // Get product data from intent
        String productImage = getIntent().getStringExtra("product_image");
        String productTitle = getIntent().getStringExtra("product_brand");
        double productRating = getIntent().getDoubleExtra("product_rating", 0);
        String productCategory = getIntent().getStringExtra("product_category");

        double productPrice = getIntent().getDoubleExtra("product_price", 0);

        // Set data to views
        Glide.with(this)
                .load(productImage)
                .into(imageViewProduct);
        textViewTitle.setText(productTitle);
        textViewRating.setText(String.valueOf(productRating));
        textViewCategory.setText("Category: " + productCategory);
        textViewPrice.setText("Price: $" + productPrice);
    }
}