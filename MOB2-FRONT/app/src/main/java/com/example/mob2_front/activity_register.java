package com.example.mob2_front;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_register extends AppCompatActivity {

    private EditText firstname;
    private EditText lastname;
    private EditText old;
    private EditText phone;
    private EditText email;
    private EditText username;
    private EditText password;


    Button button4;
    Button button3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button4 = (Button) findViewById(R.id.button4);
        button3 = (Button) findViewById(R.id.button3);

        firstname = (EditText) findViewById(R.id.update_name);
        lastname = (EditText) findViewById(R.id.update_description);
        old = (EditText) findViewById(R.id.update_price);
        phone = (EditText) findViewById(R.id.editText8);
        email = (EditText) findViewById(R.id.update_website);
        username = (EditText) findViewById(R.id.update_planning);
        password = (EditText) findViewById(R.id.editText6);


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }
    public void register(){

        String Sfirstname = firstname.getText().toString();
        String Slastname = lastname.getText().toString();
        String Sold = old.getText().toString();
        String Sphone = phone.getText().toString();
        String Semail = email.getText().toString();
        String Susername = username.getText().toString();
        String Spassword = password.getText().toString();

        Users users = new Users(Sfirstname, Slastname, Sold, Sphone, Semail, Susername, Spassword);
        Log.d("register", users.getEmail() + " " + users.getFirstname()+ " " + users.getLastname()+ " " + users.getOld()+ " " + users.getPassword()+ " " + users.getPhone() + " " + users.getUsername());
        this.create_user(users);

    }

    public void create_user(Users users){
        Call<JsonObject> call = RetrofitClass.getRequestApi().register(users);
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

    private void startRegisterActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
