package com.example.mob2_front;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_add_restaurant extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button button;
    Button button2;

    private EditText name;
    private EditText description;
    private EditText phone;
    private EditText localisation;
    private EditText website;
    private EditText planning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.update1);

        name = (EditText) findViewById(R.id.update_name);
        description = (EditText) findViewById(R.id.update_description);
        phone = (EditText) findViewById(R.id.update_price);
        localisation = (EditText) findViewById(R.id.update_localisation);
        website = (EditText) findViewById(R.id.update_website);
        planning = (EditText) findViewById(R.id.update_planning);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showrestaurant();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addrestaurant();
            }
        });

        NavigationView mNavigationView = findViewById(R.id.navigation);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }

    public void addrestaurant(){
        String Sname = name.getText().toString();
        String Sdescription = description.getText().toString();
        String Sphone = phone.getText().toString();
        String Slocalisation = localisation.getText().toString();
        String Swebsite = website.getText().toString();
        String Splanning = planning.getText().toString();

        Restaurant restaurant = new Restaurant(Sname, Sdescription, Sphone, Slocalisation, Swebsite, Splanning);
        Log.d("add_restaurant", restaurant.getName() + " " + restaurant.getDescription()+ " " + restaurant.getPhone()+ " " + restaurant.getWebsite() + " " + restaurant.getLocalisation() + " " + restaurant.getPlanning());

        SharedPreferences editor = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String token = editor.getString("access_token", "" );
        token = token.replace("\"","");
        this.create_restaurant(restaurant, token);
    }

    private void showrestaurant(){
        Intent i = new Intent(this, activity_show_restaurant.class);
        startActivity(i);
    }

    public void create_restaurant(Restaurant restaurant, String token){
        Call<JsonObject> call = RetrofitClass.getRequestApi().addrestaurant(restaurant, token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Log.d("add_restaurant", "response: " + response.body());
                    showrestaurant();
                }
                else {
                    Log.e("add_restaurant", "error: " + response.errorBody().source());
                    Context context = getApplicationContext();
                    CharSequence text = "Try again !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
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