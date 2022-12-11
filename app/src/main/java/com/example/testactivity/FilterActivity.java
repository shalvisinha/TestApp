package com.example.testactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterActivity extends AppCompatActivity {

    CheckBox cb;
    DbManager db;
    ArrayList<String> aNames = new ArrayList<>();
    ArrayList<CheckBox> arrayCb = new ArrayList<>();
    ArrayList<String> authFilter = new ArrayList<>();
    ArrayList<String> priceFilter = new ArrayList<>();
    HashMap<String,ArrayList<String>> AdvFilter = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.aut_cb);
        Button addFilter = findViewById(R.id.addedfil);
        //getting all author's names from database
        db = new DbManager(this);

        Cursor cs = db.getAuthorName();
        while (cs.moveToNext()){
            aNames.add(cs.getString(0));
        }

        //adding checkboxes for each author
        for(int i = 0; i < aNames.size(); i++) {
            cb = new CheckBox(getApplicationContext());
            cb.setId(i);
            cb.setText(aNames.get(i));
            arrayCb.add(cb);
            l1.addView(cb);
        }

        //adding price Filter
        RadioButton below_100 = findViewById(R.id.below100);
        RadioButton rn100_300 = findViewById(R.id.r100_300);
        RadioButton above_300 = findViewById(R.id.above_300);



        addFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<arrayCb.size();i++){
                    if(arrayCb.get(i).isChecked()){
                        authFilter.add(arrayCb.get(i).getText().toString());
                        //Toast.makeText(getApplicationContext(),arrayCb.get(i).getText().toString(),Toast.LENGTH_SHORT).show();
                    }
                }

                int minPrice=0;
                int maxPrice= Integer.MAX_VALUE;

                if(rn100_300.isChecked()){
                    minPrice=100;
                    maxPrice=300;
                }
                if(below_100.isChecked()){
                    maxPrice=100;
                }
                if(above_300.isChecked()){
                    minPrice= 300;
                }

                priceFilter.add(String.valueOf(minPrice));
                priceFilter.add(String.valueOf(maxPrice));


                //adding filters
                AdvFilter.put("author_name",authFilter);
                AdvFilter.put("price",priceFilter);

                //Toast.makeText(getApplicationContext(),AdvFilter.toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), BookList.class);
                if(authFilter.size()!=0){
                    intent.putExtra("filterMap", AdvFilter);
                }else if(minPrice!=0 || maxPrice!=Integer.MAX_VALUE){
                    intent.putExtra("filterMap", AdvFilter);
                }
                startActivity(intent);
            }
        });



    }
}