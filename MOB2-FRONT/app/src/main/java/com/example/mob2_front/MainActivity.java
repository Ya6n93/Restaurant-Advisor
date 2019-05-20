package com.example.mob2_front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    Button button;
    Button button2;
    Button button3;
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitClass retrofit = new RetrofitClass();

        SharedPreferences.Editor editor = getSharedPreferences("store_token", MODE_PRIVATE).edit();
        editor.putString("User_id", "" + "0" );

        editor.apply();

        username = (EditText) findViewById(R.id.update_name);
        password = (EditText) findViewById(R.id.update_description);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.update1);
        button3 = (Button) findViewById(R.id.button3);
        button5 = (Button) findViewById(R.id.button5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showrestaurant();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_add_restaurant();
            }
        });
    }

    private void showrestaurant(){
        Intent i = new Intent(this, activity_show_restaurant.class);
        startActivity(i);
    }

    private void go_to_add_restaurant(){
        Intent a = new Intent(this, activity_show_menu.class);
        startActivity(a);
    }

    private void startRegisterActivity() {
        Intent i = new Intent(this, activity_register.class);
        startActivity(i);
    }

    public void login(){
        String Susername = username.getText().toString();
        String Spassword = password.getText().toString();


        Connexion connexion = new Connexion(Susername, Spassword);
        this.connect(connexion);
    }

    public void connect(Connexion connexion){
        Call<JsonObject> call = RetrofitClass.getRequestApi().login(connexion);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    String TOKEN = ""+response.body().get("Token");
                    String id = "" + response.body().get("User_id");
                    SharedPreferences.Editor editor = getSharedPreferences("store_token", MODE_PRIVATE).edit();
                    editor.putString("access_token", "Bearer " + TOKEN );
                    editor.putString("User_id", "" + id );

                    editor.apply();
                    Log.d("login", "response: " + response.body());
                    showrestaurant();
                }
                else {
                    Log.e("login", "error: " + response.errorBody().source());
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
}
