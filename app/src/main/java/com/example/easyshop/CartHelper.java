package com.example.easyshop;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.easyshop.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartHelper {

    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_KEY = "cart_key";

    public static List<Product> getCart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        String cartJson = sharedPreferences.getString(CART_KEY, null);
        if (cartJson != null) {
            Type type = new TypeToken<List<Product>>() {}.getType();
            return new Gson().fromJson(cartJson, type);
        } else {
            return new ArrayList<>();
        }
    }

    public static void addToCart(Context context, Product product) {
        List<Product> cart = getCart(context);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        // Tambahkan produk baru di bagian atas daftar
        cart.add(0, product);
        saveCart(context, cart);
    }

    public static void saveCart(Context context, List<Product> cartProductList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String cartJson = new Gson().toJson(cartProductList);
        editor.putString(CART_KEY, cartJson);
        editor.apply();
    }

    public static double getTotalPrice(Context context) {
        List<Product> cart = getCart(context);
        double total = 0;
        if (cart != null) {
            for (Product product : cart) {
                total += product.getPrice();
            }
        }
        return total;
    }
}
