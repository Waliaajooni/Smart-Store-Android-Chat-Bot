package com.project.storechatbot;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.project.storechatbot.cards.CustomerCardView;
import com.project.storechatbot.cards.ProductCardView;
import com.project.storechatbot.cards.SaleCardView;
import com.project.storechatbot.client.RetrofitClient;
import com.project.storechatbot.service.ChatApiService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText queryInput;
    private LinearLayout chatContainer;
    private ChatApiService apiService;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatContainer = findViewById(R.id.chatContainer);
        queryInput = findViewById(R.id.queryInput);
        ImageButton sendButton = findViewById(R.id.sendButton);
        scrollView = findViewById(R.id.scrollView);

        apiService = RetrofitClient.getClient().create(ChatApiService.class);

        sendButton.setOnClickListener(v -> {
            String query = queryInput.getText().toString().trim();
            if (!query.isEmpty()) {
                chatContainer.removeAllViews();
                callChatApi(query);
                queryInput.setText("");
            }
        });

//        setContentView(R.layout.activity_main);

        apiService = RetrofitClient.getClient().create(ChatApiService.class);

        // Example: user typed "show all products"
//        callChatApi("show all products");
    }

    private void callChatApi(String query) {
        apiService.getQueryResponse(query).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject result = response.body();
                    parseDynamicResponse(result);
                } else {
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parseDynamicResponse(JsonObject response) {
        for (String key : response.keySet()) {
            JsonArray dataArray = response.getAsJsonArray(key);
            switch (key.toLowerCase()) {
                case "product":
                    renderProductCards(dataArray);
                    break;
                case "sale":
                    renderSaleCards(dataArray);
                    break;
                case "customer":
                    renderCustomerCards(dataArray);
                    break;
                default:
                    renderTextMessage("Unknown type: " + key);
            }
        }

        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    private void renderTextMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void renderProductCards(JsonArray products) {
        for (JsonElement item : products) {
            JsonObject obj = item.getAsJsonObject();
            String name = obj.has("name") ? obj.get("name").getAsString() : "";
            String desc = obj.has("productDesc") ? obj.get("productDesc").getAsString() : "";
            double price = obj.has("price") ? obj.get("price").getAsDouble() : 0.0;
            String dept = obj.has("department") ? obj.get("department").getAsString() : "";
            String brand = obj.has("brand") ? obj.get("brand").getAsString() : "";
            long stock = obj.has("stockQuantity") ? obj.get("stockQuantity").getAsLong() : 0;

            ProductCardView card = new ProductCardView(this);
            card.setData(name, desc, price, dept, brand, stock);
            chatContainer.addView(card);
        }
    }

    private void renderSaleCards(JsonArray sales) {
        for (JsonElement item : sales) {
            JsonObject obj = item.getAsJsonObject();
            Long saleId = obj.has("saleId") ? obj.get("saleId").getAsLong() : 0;
            String saleDate = obj.has("saleDate") ? obj.get("saleDate").getAsString() : "";
            Double totalAmount = obj.has("totalAmount") ? obj.get("totalAmount").getAsDouble() : 0.0;
            JsonObject customerJson = obj.has("customer") ? obj.get("customer").getAsJsonObject() : null;
            Long customerId = customerJson.has("customerId") ? customerJson.get("customerId").getAsLong() : 0;

            SaleCardView card = new SaleCardView(this);
            card.setData(saleId, saleDate, totalAmount, customerId);
            chatContainer.addView(card);
        }
    }

    private void renderCustomerCards(JsonArray customers) {
        for (JsonElement item : customers) {
            JsonObject obj = item.getAsJsonObject();
            long customerId = obj.has("customerId") ? obj.get("customerId").getAsLong() : 0;
            String name = obj.has("customerName") ? obj.get("customerName").getAsString() : "";
            String gender = obj.has("gender") ? obj.get("gender").getAsString() : "";
            JsonArray salesForCustomer = obj.has("sales") ? obj.get("sales").getAsJsonArray() : null;
            double totalAmount = 0.0d;

            if (salesForCustomer != null) {
                for (JsonElement saleElement : salesForCustomer) {
                    JsonObject saleObj = saleElement.getAsJsonObject();

                    // Extract individual sale fields
                    long saleId = saleObj.has("saleId") ? saleObj.get("saleId").getAsLong() : 0;
                    String saleDate = saleObj.has("saleDate") ? saleObj.get("saleDate").getAsString() : "";
                    totalAmount += saleObj.has("totalAmount") ? saleObj.get("totalAmount").getAsDouble() : 0.0d;
                }
            }

            CustomerCardView card = new CustomerCardView(this);
            card.setData(customerId, name, gender, totalAmount);
            chatContainer.addView(card);
        }
    }

}
