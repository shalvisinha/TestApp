package com.example.testactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BookList extends AppCompatActivity {

    DbManager db;
    ArrayList<Book> bookDb = new ArrayList<>();
    ArrayList<Book> filteredDb = new ArrayList<>();
    ListView book_list;
    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        book_list = (ListView)findViewById(R.id.blist);
        db= new DbManager(this);
        Cursor data = db.getData();
        while (data.moveToNext()) {
            bookDb.add(new Book(data.getBlob(0), data.getString(1), data.getString(2), data.getInt(3)));
        }

        //applying filters
        ArrayList<String> aFilter = new ArrayList<>();
        ArrayList<String> pFilter = new ArrayList<>();
        if(getIntent().hasExtra("filterMap")){
            Intent intent = getIntent();
            HashMap<String, ArrayList<String>> filter = (HashMap<String, ArrayList<String>>)intent.getSerializableExtra("filterMap");
            aFilter = filter.get("author_name");
            pFilter = filter.get("price");
            //Toast.makeText(getApplicationContext(),aFilter.toString(),Toast.LENGTH_LONG).show();
            int minP = 0;
            int maxP = Integer.MAX_VALUE;
            try{
                minP=Integer.parseInt(pFilter.get(0));
                maxP= Integer.parseInt(pFilter.get(1));

            }catch (NumberFormatException ne){
                ne.printStackTrace();
            }

            for(int i=0;i<bookDb.size();i++){
                if(bookDb.get(i).getPrice()>=minP && bookDb.get(i).getPrice()<=maxP){
                    if(aFilter.size()!=0){
                        for(int j=0;j<aFilter.size();j++){
                            if(bookDb.get(i).getAuthorName().equals(aFilter.get(j))){
                                filteredDb.add(bookDb.get(i));
                            }
                        }
                    }else{
                        filteredDb.add(bookDb.get(i));
                    }
                }
            }
            bookAdapter = new BookAdapter(this, R.layout.book_layout,filteredDb);
        }else{
            bookAdapter = new BookAdapter(this, R.layout.book_layout,bookDb);
        }
        book_list.setAdapter(bookAdapter);
        Button filterButton = findViewById(R.id.filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFilter = new Intent(getApplicationContext(),FilterActivity.class);
                startActivity(addFilter);
            }
        });
    }
}