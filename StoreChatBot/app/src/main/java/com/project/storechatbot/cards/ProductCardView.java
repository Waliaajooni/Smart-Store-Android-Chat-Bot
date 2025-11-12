package com.project.storechatbot.cards;

import android.content.Context;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.project.storechatbot.R;

public class ProductCardView extends CardView {
    private TextView name, desc, price, dept, brand, stock;

    public ProductCardView(Context context) {
        super(context);
        inflate(context, R.layout.card_product, this);
        name = findViewById(R.id.product_name);
//        desc = findViewById(R.id.product_);
        price = findViewById(R.id.product_price);
        dept = findViewById(R.id.product_department);
//        brand = findViewById(R.id.product_brand);
        stock = findViewById(R.id.product_stock);
    }

    public void setData(String nameText, String descText, double priceVal,
                        String deptText, String brandText, long stockQty) {
        name.setText(nameText);
//        desc.setText(descText);
        price.setText("₹ " + priceVal);
        dept.setText("Department: " + deptText);
//        brand.setText("Brand: " + brandText);
        stock.setText("Stock: " + stockQty);
    }
}
