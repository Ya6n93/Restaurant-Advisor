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

public class DetailRestaurant extends AppCompatActivity {

    private String id_connect;
    private TextView detail_id;
    private TextView detail_name;
    private TextView detail_description;
    private TextView detail_localisation;
    private TextView detail_phone;
    private TextView detail_website;
    private TextView detail_planning;

    Button detail_update;
    Button delete;
    Button detail_menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailrestaurant);

        SharedPreferences ok = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String ok2 = ok.getString("User_id", "0");

        this.id_connect = getIntent().getStringExtra(("user_id").replace("\"", ""));

        Log.d("ok", "" + this.id_connect);
        Log.d("ok", " shared" + ok2);

        detail_id = findViewById(R.id.detail_id);
        detail_name = findViewById(R.id.detail_menu_name);
        detail_description = findViewById(R.id.detail_menu_description);
        detail_localisation = findViewById(R.id.detail_localisation);
        detail_phone = findViewById(R.id.detail_menu_price);
        detail_website = findViewById(R.id.detail_website);
        detail_planning = findViewById(R.id.detail_planning);
        Button detail_update = findViewById(R.id.detail_menu_update);
        Button delete = findViewById(R.id.detail_delete);
        Button detail_menu = findViewById(R.id.detail_menu);


        detail_id.setText(getIntent().getStringExtra("id"));
        detail_name.setText(getIntent().getStringExtra("name"));
        detail_description.setText(getIntent().getStringExtra("description"));
        detail_localisation.setText(getIntent().getStringExtra("localisation"));
        detail_phone.setText(getIntent().getStringExtra("phone"));
        detail_website.setText(getIntent().getStringExtra("website"));
        detail_planning.setText(getIntent().getStringExtra("planning"));

        detail_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmenu();
            }
        });

        if (!this.id_connect.equals(ok2)) {
            Log.d("true", "CA MARCHEEEEEEEEEEEEEEEEE");
            detail_update.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        } else {

            detail_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update_restaurant();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_restaurant();
                }
            });
        }
    }
    public void update_restaurant(){
        Intent i = new Intent(this, activity_update_restaurant.class);
        i.putExtra("id", getIntent().getStringExtra("id"));
        i.putExtra("restaurant_id", getIntent().getStringExtra("restaurant_id"));
        i.putExtra("user_id", getIntent().getStringExtra("user_id"));
        i.putExtra("name", getIntent().getStringExtra("name"));
        i.putExtra("description", getIntent().getStringExtra("description"));
        i.putExtra("localisation", getIntent().getStringExtra("localisation"));
        i.putExtra("phone", getIntent().getStringExtra("phone"));
        i.putExtra("website", getIntent().getStringExtra("website"));
        i.putExtra("planning", getIntent().getStringExtra("planning"));
        startActivity(i);
    }

    public void delete_restaurant(){

        SharedPreferences editor = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String token = editor.getString("access_token", "");
        token = token.replace("\"", "");
        this.deleted(token, getIntent().getStringExtra("id"));

    }

    public void deleted(String token, String restaurant_id){
        Call<JsonArray> call = RetrofitClass.getRequestApi().deleterestaurant(token, restaurant_id);
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
    private void showrestaurant(){

        Intent i = new Intent(this, activity_show_restaurant.class);
        startActivity(i);

    }

    private void showmenu(){

        Intent i = new Intent(this, activity_show_menu.class);
        i.putExtra("id", getIntent().getStringExtra("id"));
        i.putExtra("user_id", this.id_connect);
        startActivity(i);

    }
}