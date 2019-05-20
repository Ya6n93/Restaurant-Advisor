package com.example.mob2_front;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListViewAdapter extends BaseAdapter {
    private Context context;
    private List<ListViewResto> listViewRestos;

    public MyListViewAdapter(Context context, List listViewRestos){
        this.context = context;
        this.listViewRestos = listViewRestos;
    }

    @Override
    public int getCount() {
        return listViewRestos.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewRestos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.restau_row, null);
        }
        ListViewResto listViewResto = listViewRestos.get(position);
        System.out.println("----------" + listViewResto.getName());

        TextView textViewRestoName = (TextView) convertView.findViewById(R.id.detail_menu_name);
        textViewRestoName.setText(listViewResto.getName());




        return convertView;
    }
}
