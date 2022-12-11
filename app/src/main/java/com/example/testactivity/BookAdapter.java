package com.example.testactivity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context myContext;
    private int myResource;
    public BookAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Book> objects) {
        super(context, resource, objects);
        this.myContext=context;
        this.myResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(myContext);

        convertView = layoutInflater.inflate(myResource,parent,false);

        ImageView bookIconX = convertView.findViewById(R.id.bookIcon_i);
        TextView bookNameX = convertView.findViewById(R.id.book_i);
        TextView authorNameX = convertView.findViewById(R.id.auth_i);
        TextView priceX = convertView.findViewById(R.id.price_i);

        bookIconX.setImageBitmap(BitmapFactory.decodeByteArray(getItem(position).getBookIm(),0,getItem(position).getBookIm().length));
        bookNameX.setText(getItem(position).getBookName());
        authorNameX.setText(getItem(position).getAuthorName());
        String stringdouble= Double.toString(getItem(position).getPrice());
        priceX.setText(stringdouble);

        return convertView;

    }
}
