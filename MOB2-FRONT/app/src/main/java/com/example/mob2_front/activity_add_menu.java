package com.example.mob2_front;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_add_menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button button2;

    private EditText name;
    private EditText description;
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        button2 = (Button) findViewById(R.id.update1);

        name = (EditText) findViewById(R.id.update_name);
        description = (EditText) findViewById(R.id.update_description);
        price = (EditText) findViewById(R.id.update_price);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmenu();
            }
        });

        NavigationView mNavigationView = findViewById(R.id.navigation);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }

    public void addmenu(){
        String Sname = name.getText().toString();
        String Sdescription = description.getText().toString();
        String Sprice = price.getText().toString();

        Menu menu = new Menu(Sname, Sdescription, Sprice);
        Log.d("add menu :", menu.getName() + " " + menu.getDescription()+ " " + menu.getPrice());

        SharedPreferences editor = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String token = editor.getString("access_token", "" );
        token = token.replace("\"","");
        this.create_menu(menu, token, getIntent().getStringExtra("restaurant_id"));
    }

    public void create_menu(Menu menu, String token, String restaurant_id){
        Call<JsonObject> call = RetrofitClass.getRequestApi().addmenu(menu, token, restaurant_id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Log.d("register", "response: " + response.body());
                }
                else {
                    Log.e("register", "error: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("register", "fail: " + t.getMessage());
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}