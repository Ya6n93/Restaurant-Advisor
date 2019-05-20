package com.example.mob2_front;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListViewAdapter2 extends BaseAdapter {
    private Context context;
    private List<ListViewMenu> listViewMenu;

    public MyListViewAdapter2(Context context, List listViewMenu){
        this.context = context;
        this.listViewMenu = listViewMenu;
    }

    @Override
    public int getCount() {
        return listViewMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_row, null);
        }
        ListViewMenu listViewMenus = listViewMenu.get(position);
        System.out.println("----------" + listViewMenus.getName());

        TextView textViewMenuName = (TextView) convertView.findViewById(R.id.detail_menu_name);
        textViewMenuName.setText(listViewMenus.getName());




        return convertView;
    }
}
