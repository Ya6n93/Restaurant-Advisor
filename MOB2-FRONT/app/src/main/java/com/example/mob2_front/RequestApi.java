package com.example.mob2_front;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Driver;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RequestApi {

    @POST("register")
    Call<JsonObject> register(@Body Users users);

    @POST("login")
    Call<JsonObject> login(@Body Connexion connexion);

    @POST("create_restaurant")
    Call<JsonObject> addrestaurant(@Body Restaurant restaurant, @Header("authorization") String header);

    @POST("create_menu/{id}")
    Call<JsonObject> addmenu(@Body Menu menu, @Path("id") String restaurant_id, @Header("authorization") String header);

    @GET("show_restaurant")
    Call<JsonArray> showrestaurant();

    @GET("show_menu/{id}")
    Call<JsonArray> showmenu(@Path("id") String restaurant_id);

    @PUT("update_restaurant/{id}")
    Call<JsonObject> updaterestaurant(@Body Restaurant restaurant, @Header("authorization") String header,  @Path("id") String restaurant_id);

    @PUT("update_menu/{id}")
    Call<JsonObject> updatemenu(@Body Menu menu, @Header("authorization") String header, @Path("id") String restaurant_id);

    @GET("logout")
    Call<JsonArray> logout(@Header("authorization") String header);

    @DELETE("delete_restaurant/{id}")
    Call<JsonArray> deleterestaurant(@Header("authorization") String header, @Path("id") String restaurant_id);

    @DELETE("delete_menu/{id}")
    Call<JsonArray> deletemenu(@Header("authorization") String header, @Path("id") String restaurant_id);
}
