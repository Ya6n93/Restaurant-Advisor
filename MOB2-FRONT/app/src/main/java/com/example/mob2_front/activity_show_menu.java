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

public class activity_show_menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String id_connect;
    private MyListViewAdapter2 myListViewAdapter2;
    private ListView listView2;
    private List<ListViewMenu> ListViewMenu;
    private RetrofitClass Retro;
    Button add_menu;
    Button return_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        SharedPreferences ok = getSharedPreferences("store_token", Context.MODE_PRIVATE);
        String ok2 = ok.getString("User_id", "0");

        this.id_connect = getIntent().getStringExtra(("user_id").replace("\"", ""));

        ListViewMenu = new ArrayList<>();
        myListViewAdapter2 = new MyListViewAdapter2(getApplicationContext(), ListViewMenu);
        listView2 = (ListView) findViewById(R.id.listView2);
        listView2.setAdapter(myListViewAdapter2);
        ListMenuAPI();

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(activity_show_menu.this, DetailMenu.class);
                i.putExtra("id", ListViewMenu.get(position).getId());
                i.putExtra("user_id", getIntent().getStringExtra("user_id"));
                i.putExtra("restaurant_id", getIntent().getStringExtra("restaurant_id"));
                i.putExtra("name", ListViewMenu.get(position).getName());
                i.putExtra("description", ListViewMenu.get(position).getDescription());
                i.putExtra("price", ListViewMenu.get(position).getPrice());
                startActivity(i);
            }
        });
        add_menu = (Button) findViewById(R.id.add_menu);
        return_ = (Button) findViewById(R.id.return_);

        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showrestaurant();
            }
        });

        if (!this.id_connect.equals(ok2)) {
            Log.d("true", "CA MARCHEEEEEEEEEEEEEEEEE");
            add_menu.setVisibility(View.GONE);
        } else {

        add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_add();
            }
        });
        }

        NavigationView mNavigationView = findViewById(R.id.navigation);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }

    private void go_to_add() {
        Intent i = new Intent(this, activity_add_menu.class);
        startActivity(i);
    }

    private void showrestaurant(){

        Intent i = new Intent(this, activity_show_restaurant.class);
        startActivity(i);

    }

    public void ListMenuAPI() {

        String restaurantid = getIntent().getStringExtra("id");
        Log.e("valeur", "" + restaurantid);

        Retro.getRequestApi().showmenu(restaurantid).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {

                    JsonArray jarray = response.body();

                    Log.d("hihihi", "SIZE = " + jarray.size());
                    Log.d("hihihi", "contenu " + jarray);

                    Gson gson = new Gson();

                    for (int count = 0; count < jarray.size(); ++count) {

                        JsonObject obj = jarray.get(count).getAsJsonObject();
                        ListViewMenu listViewMenu = gson.fromJson(obj, ListViewMenu.class);
                        Log.d("hihihi", "" + listViewMenu.toString());
                        ListViewMenu.add(listViewMenu);
                    }
                    myListViewAdapter2.notifyDataSetChanged();

                } else {
                    Log.d("hihihi",  "" + response.errorBody());
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
}
