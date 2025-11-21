package com.project.storechatbot.cards;

import android.content.Context;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.project.storechatbot.R;
public class SaleCardView extends CardView {
    private TextView tvSaleId, tvSaleDate, tvTotalAmount, tvCustomerId;

    public SaleCardView(Context context) {
        super(context);
        inflate(context, R.layout.card_sale, this);

        tvSaleId = findViewById(R.id.sale_id);
        tvSaleDate = findViewById(R.id.sale_date);
        tvTotalAmount = findViewById(R.id.sale_total);
        tvCustomerId = findViewById(R.id.sale_customer_id);
    }

    public void setData(long saleId, String saleDate, double totalAmount, Long customerId) {
        tvSaleId.setText("Sale ID: " + saleId);
        tvSaleDate.setText("Date: " + saleDate);
        tvTotalAmount.setText("₹ " + totalAmount);
        tvCustomerId.setText("Customer ID : " + customerId);
    }
}
