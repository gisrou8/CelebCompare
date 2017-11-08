package com.example.jellebouwmans.celebcompare;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import KairosApi.Kairos;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Kairos kairos = new Kairos(this);
        Fonts();
    }

    private void Fonts(){
        //FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/topmodern.ttf");

        //Textfield Username
        TextView txtGender = (TextView)findViewById(R.id.txtChooseGender);
        txtGender.setTypeface(custom_font);

        //Textfield Password
        TextView txtPicture = (TextView)findViewById(R.id.txtChoosePicture);
        txtPicture.setTypeface(custom_font);
    }
}
