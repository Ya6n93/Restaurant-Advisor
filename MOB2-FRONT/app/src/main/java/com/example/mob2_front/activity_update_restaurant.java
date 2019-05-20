package com.example.mob2_front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class activity_update_restaurant extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button update1;
    Button return_;

    private EditText name;
    private EditText description;
    private EditText phone;
    private EditText localisation;
    private EditText website;
    private EditText planning;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_restaurant);

        update1 = (Button) findViewById(R.id.update1);
        return_ = (Button) findViewById(R.id.return_);

        name = (EditText) findViewById(R.id.update_name);
        description = (EditText) findViewById(R.id.update_description);
        phone = (EditText) findViewById(R.id.update_price);
        localisation = (EditText) findViewById(R.id.update_localisation);
        website = (EditText) findViewById(R.id.update_website);
        planning = (EditText) findViewById(R.id.update_planning);

        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updaterestaurant();
            }
        });
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showrestaurant();
            }
        });
        NavigationView mNavigationView = findViewById(R.id.navigation);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
        name.setText(getIntent().getStringExtra("name"));
        description.setText(getIntent().getStringExtra("description"));
        phone.setText(getIntent().getStringExtra("phone"));
        localisation.setText(getIntent().getStringExtra("localisation"));
        website.setText(getIntent().getStringExtra("website"));
        planning.setText(getIntent().getStringExtra("planning"));
    }

    private void showrestaurant() {
        Intent i = new Intent(this, activity_show_restaurant.class);
        startActivity(i);
    }

    public void updaterestaurant() {
        String Sname = name.getText().toString();
        String Sdescription = description.getText().toString();
        String Sphone = phone.getText().toString();
        String Slocalisation = localisation.getText().toString();
        String Swebsite = website.getText().toString();
        String Splanning = planning.getText().toString();

        Restaurant restaurant = new Restaurant(Sname, Sdescription, Sphone, Slocalisation, Swebsite, Splanning);
        Log.d("update_restaurant", restaurant.getName() + " " + restaurant.getDescription() + " " + restaurant.getPhone() + " " + restaurant.getWebsite() + " " + restaurant.getLocalisation() + " " + restaurant.getPlanning());

        SharedPreferences editor = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String token = editor.getString("access_token", "");
        token = token.replace("\"", "");
        this.update_restaurant(restaurant, token, getIntent().getStringExtra("id"));
    }

    public void update_restaurant(Restaurant restaurant, String token, String restaurant_id) {
        Call<JsonObject> call = RetrofitClass.getRequestApi().updaterestaurant(restaurant, token, restaurant_id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Log.e("update_restaurant", "response: " + response.body());
                    showrestaurant();
                } else {
                    Log.e("update_restaurant", "error: " + response.errorBody().source());
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
