package com.example.mob2_front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMenu extends AppCompatActivity {

    private String id_connect;
    private TextView detail_menu_name;
    private TextView detail_menu_description;
    private TextView detail_menu_price;

    Button detail_menu_update;
    Button detail_menu_delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailmenu);

        SharedPreferences ok = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String ok2 = ok.getString("User_id", "0");

        this.id_connect = getIntent().getStringExtra(("user_id").replace("\"", ""));

        Log.d("ok", "" + this.id_connect);
        Log.d("ok", " shared" + ok2);

        detail_menu_name = findViewById(R.id.detail_menu_name);
        detail_menu_description = findViewById(R.id.detail_menu_description);
        detail_menu_price = findViewById(R.id.detail_menu_price);

        Button detail_menu_update = findViewById(R.id.detail_menu_update);
        Button detail_menu_delete = findViewById(R.id.detail_menu_delete);

        detail_menu_name.setText(getIntent().getStringExtra("name"));
        detail_menu_description.setText(getIntent().getStringExtra("description"));
        detail_menu_price.setText(getIntent().getStringExtra("price"));


        Log.d("ok2", "" + ok2 + id_connect);
        if (!this.id_connect.equals(ok2)) {
            Log.d("true", "CA MARCHEEEEEEEEEEEEEEEEE");
            detail_menu_update.setVisibility(View.GONE);
            detail_menu_delete.setVisibility(View.GONE);
        } else {

            detail_menu_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update_menu();
                }
            });

            detail_menu_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_menu();
                }
            });
        }
    }

    public void update_menu() {
        Intent i = new Intent(this, activity_update_menu.class);
        i.putExtra("restaurant_id", getIntent().getStringExtra("restaurant_id"));
        i.putExtra("name", getIntent().getStringExtra("name"));
        i.putExtra("description", getIntent().getStringExtra("description"));
        i.putExtra("price", getIntent().getStringExtra("price"));
        startActivity(i);
    }

    public void delete_menu() {

        SharedPreferences editor = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String token = editor.getString("access_token", "");
        token = token.replace("\"", "");
        this.deleted(token, getIntent().getStringExtra("id"));

    }

    public void deleted(String token, String restaurant_id) {
        Call<JsonArray> call = RetrofitClass.getRequestApi().deletemenu(token, restaurant_id);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {
                    Log.e("delete_restaurant", "response: " + response.body());
                    showrestaurant();
                } else {
                    Log.e("delete_restaurant", "error: " + response.errorBody().source());
                    Context context = getApplicationContext();
                    CharSequence text = "Try again !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("register", "fail: " + t.getMessage());
            }
        });
    }

    private void showrestaurant() {

        Intent i = new Intent(this, activity_show_restaurant.class);
        startActivity(i);

    }

    private void showmenu() {

        Intent i = new Intent(this, activity_show_menu.class);
        i.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(i);

    }
}