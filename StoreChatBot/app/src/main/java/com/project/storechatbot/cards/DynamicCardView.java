package com.project.storechatbot.cards;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.project.storechatbot.R;

public class DynamicCardView extends CardView {

    private LinearLayout container;

    public DynamicCardView(Context context) {
        super(context);
        inflate(context, R.layout.dynamic_full_card, this);
        container = findViewById(R.id.cardContainer);
    }

    public void addProperty(String key, String value) {
        CardRender row = new CardRender(getContext());
        row.setData(key, value);
        container.addView(row);
    }
}
