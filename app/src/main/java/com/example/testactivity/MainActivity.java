package com.example.testactivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Uri photoUri;
    ImageView bookIcon;
    String imgPath="";
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DbManager(this);
        Button addBooks = (Button) findViewById(R.id.add_books);
        Button viewBooks = (Button)findViewById(R.id.view_books);
        Button order = (Button)findViewById(R.id.view_orders);

        //pop-up for adding Books
        addBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });

        //viewing Books
        viewBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(),BookList.class);
                startActivity(next);
            }
        });

        //viewing Orders
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(next);
            }
        });

    }

    //getting URI and Path of bookIcon
    final private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                    photoUri = result.getData().getData();
                    bookIcon.setImageURI(photoUri);
                    imgPath = photoUri.toString();

                }
            }
    );

    //Form for adding Books
    protected void showDialog(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog,null);
        bookIcon = mview.findViewById(R.id.book_icon);
        Button addImg = mview.findViewById(R.id.add_img);
        EditText bName = mview.findViewById(R.id.b_name);
        EditText aName = mview.findViewById(R.id.a_name);
        EditText pr = mview.findViewById(R.id.price);
        Button addBooks = mview.findViewById(R.id.add_book);
        Button cancel = mview.findViewById(R.id.cancel);
        alert.setView(mview);


        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(iGallery);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        addBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = bName.getText().toString();
                String authorName = aName.getText().toString();
                Integer price=0;
                String price_str = pr.getText().toString();
                try {
                    price = Integer.parseInt(price_str);
                }catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
                //validations
                if( !bookName.isEmpty() ){
                    if(!authorName.isEmpty()){
                        if(!imgPath.isEmpty()){
                            if(price!=0){
                                byte[] imgData = null;
                                try {
                                    ContentResolver cr = getBaseContext().getContentResolver();
                                    InputStream inputStream = cr.openInputStream(photoUri);
                                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    imgData = baos.toByteArray();

                                    boolean insertData = db.insert(imgData,bookName,authorName,price);
                                    if(insertData){
                                        alertDialog.dismiss();

                                        Toast.makeText(MainActivity.this,"Book data Saved!", Toast.LENGTH_LONG).show();
                                    }else{
                                        alertDialog.dismiss();
                                        Toast.makeText(MainActivity.this,"some error occurred!", Toast.LENGTH_LONG).show();
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Toast.makeText(MainActivity.this,"Please Enter price of the book",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"Please enter cover of the book",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"Please enter author name",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this,"Please enter book name",Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();







    }
}