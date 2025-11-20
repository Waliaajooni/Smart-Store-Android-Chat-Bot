package com.project.storechatbot.cards;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.project.storechatbot.R;

public class CardRender extends CardView {

    private TextView propertyName, propertyValue;

    public CardRender(@NonNull Context context) {
        super(context);
        inflate(context, R.layout.activity_card, this);
    }


}
