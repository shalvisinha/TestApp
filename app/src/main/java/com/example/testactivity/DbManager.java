package com.example.testactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbManager extends SQLiteOpenHelper {
    public static final String dbname ="Books.db";

    public DbManager(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry = "CREATE TABLE book(book_cover blob, book_name text not null, author_name text, price integer)";
        String qry2 = "CREATE TABLE orders(order_id text,bookname text not null, quantity integer, foreign key(bookname) references book(book_name))";
        sqLiteDatabase.execSQL(qry2);
        sqLiteDatabase.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       sqLiteDatabase.execSQL("drop table if exists book");
       sqLiteDatabase.execSQL("drop table if exists orders");
       onCreate(sqLiteDatabase);
       onCreate(sqLiteDatabase);
    }

    public Boolean insert(byte[] img, String bname , String aname, int pr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("book_cover",img);
        cv.put("book_name",bname);
        cv.put("author_name",aname);
        cv.put("price",pr);

        long res = db.insert("book",null,cv);
        if(res==-1)
        {
            return false;
        }
        else
            return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from book",null);
        return data;
    }

    public  Cursor getAuthorName(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select author_name from book",null);
        return data;
    }

    public Boolean insertOrder(String oid , String bname, int quan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("order_id",oid);
        cv.put("bookname",bname);
        cv.put("quantity",quan);

        long res = db.insert("orders",null,cv);
        if(res==-1)
        {
            return false;
        }
        else
            return true;
    }

    public Cursor getOrderData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT b.book_cover, b.book_name,b.author_name, b.price, o.quantity FROM book b INNER JOIN orders o ON b.book_name= o.bookname",null);
        return data;
    }

}
