package com.project.storechatbot.service;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatApiService {
    @GET("api/v1/chat")
    Call<JsonObject> getQueryResponse(@Query("query") String query);


    @GET("api/v1/chat1")
    Call<JsonObject> getQueryResponse1(@Query("query") String query);
}
