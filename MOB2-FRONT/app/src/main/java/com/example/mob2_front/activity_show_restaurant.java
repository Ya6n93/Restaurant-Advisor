package com.example.mob2_front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_show_restaurant extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String id_connect;
    private MyListViewAdapter myListViewAdapter;
    private ListView listView;
    private List<ListViewResto> ListViewRestos;
    private RetrofitClass Retro;
    Button add;
    Button logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurant);

        SharedPreferences ok = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String ok2 = ok.getString("User_id", null);

        this.id_connect = getIntent().getStringExtra(("user_id").replace("\"", ""));

        ListViewRestos = new ArrayList<>();
        myListViewAdapter = new MyListViewAdapter(getApplicationContext(), ListViewRestos);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myListViewAdapter);
        ListRestoAPI();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(activity_show_restaurant.this, DetailRestaurant.class);
                Log.d("haha", "" + ListViewRestos.get(position).getId());
                i.putExtra("id", ListViewRestos.get(position).getId());
                i.putExtra("user_id", ListViewRestos.get(position).getUser_id());
                i.putExtra("name", ListViewRestos.get(position).getName());
                i.putExtra("description", ListViewRestos.get(position).getDescription());
                i.putExtra("localisation", ListViewRestos.get(position).getLocalisation());
                i.putExtra("phone", ListViewRestos.get(position).getPhone());
                i.putExtra("website", ListViewRestos.get(position).getWebsite());
                i.putExtra("planning", ListViewRestos.get(position).getPlanning());
                startActivity(i);
            }
        });
        add = (Button) findViewById(R.id.add);
        logout = (Button) findViewById(R.id.logout);


        NavigationView mNavigationView = findViewById(R.id.navigation);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        if (ok2.equals("0")) {
            add.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
        } else {

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    go_to_add();
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disconnect();
                }
            });
        }
    }

    private void go_to_add() {
        Intent i = new Intent(this, activity_add_restaurant.class);
        startActivity(i);
    }

    public void ListRestoAPI() {

        Retro.getRequestApi().showrestaurant().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {

                    JsonArray jarray1 = response.body();
                    JsonArray jarray = (JsonArray) jarray1.get(0);

                    Log.d("retau", "SIZE = " + jarray.size());
                    Log.d("retau", "contenu " + jarray);

                    Gson gson = new Gson();

                    for (int count = 0; count < jarray.size(); ++count) {

                        JsonObject obj = jarray.get(count).getAsJsonObject();
                        ListViewResto listViewResto = gson.fromJson(obj, ListViewResto.class);
                        Log.d("retau", "" + listViewResto.toString());
                        ListViewRestos.add(listViewResto);
                    }
                    myListViewAdapter.notifyDataSetChanged();
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("restaurant", "fail: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public void disconnect(){
        SharedPreferences editor = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String token = editor.getString("access_token", "" );
        token = token.replace("\"","");
        this.Logout(token);
    }

    public void Logout(String token){
        Call<JsonArray> call = RetrofitClass.getRequestApi().logout(token);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {
                    Log.d("logout", "response: " + response.body());
                    Intent i = new Intent(activity_show_restaurant.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Log.e("logout", "error: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("logout", "fail: " + t.getMessage());
            }
        });
    }
}
