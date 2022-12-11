package com.example.testactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    DbManager odb;
    ArrayList<Book> OrderDb = new ArrayList<>();
    ListView orderList;
    OrderAdapter bookOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderList = (ListView)findViewById(R.id.order_list);
        TextView noOrder = (TextView) findViewById(R.id.no_or);
        odb= new DbManager(this);
        //sample orders
        //odb.insertOrder("o101","The Alchemist",2);
        Cursor data = odb.getOrderData();
        if(data.getCount()==0){
            noOrder.setVisibility(View.VISIBLE);
        }else{
            while (data.moveToNext()) {
                OrderDb.add(new Book(data.getBlob(0), data.getString(1), data.getString(2), data.getInt(3), data.getInt(4)));
            }

            bookOrderAdapter = new OrderAdapter(this, R.layout.order_layout,OrderDb);
            orderList.setAdapter(bookOrderAdapter);
        }

    }
}