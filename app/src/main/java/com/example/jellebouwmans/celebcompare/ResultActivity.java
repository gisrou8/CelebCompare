package com.example.jellebouwmans.celebcompare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    public TextView txtCeleb;
    public ImageView imgCeleb;
    public ImageView imgYou;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtCeleb = (TextView) findViewById(R.id.txtCeleb);
        imgCeleb = (ImageView) findViewById(R.id.imgCeleb);
        imgYou = (ImageView) findViewById(R.id.imgYou);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        imageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        imgYou.setImageBitmap(imageBitmap);
    }

    private void Fonts(){
        //FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/topmodern.ttf");

        //Textfield Username
        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setTypeface(custom_font);

        //Textfield Password
        TextView txtCeleb = (TextView)findViewById(R.id.txtCeleb);
        txtCeleb.setTypeface(custom_font);

        Button btnNewPhoto = (Button)findViewById(R.id.btnNewPhoto);
        btnNewPhoto.setTypeface(custom_font);
    }
}
