package com.example.mob2_front;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass {

    private Retrofit retrofit;
    private static RequestApi requestApi;

    public RetrofitClass() {this.configRetrofit();}

    public static RequestApi getRequestApi() {
        return requestApi;
    }

    public static void setRequestApi(RequestApi requestApi) {
        RetrofitClass.requestApi = requestApi;
    }

    private void configRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        requestApi = retrofit.create(RequestApi.class);
    }

}
