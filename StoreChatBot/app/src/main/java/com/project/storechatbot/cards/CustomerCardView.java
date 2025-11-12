package com.project.storechatbot.cards;

import android.content.Context;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.project.storechatbot.R;

public class CustomerCardView extends CardView {
    private TextView tvCustomerId, tvName, tvGender;

    public CustomerCardView(Context context) {
        super(context);
        inflate(context, R.layout.card_customer, this);
        tvCustomerId = findViewById(R.id.customer_id);
        tvName = findViewById(R.id.customer_name);
        tvGender = findViewById(R.id.customer_gender);
    }

    public void setData(long customerId, String name, String gender) {
        tvCustomerId.setText("Customer ID: " + customerId);
        tvName.setText("Name: " + name);
        tvGender.setText("Gender: " + gender);
    }
}
