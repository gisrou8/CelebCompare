package com.example.jellebouwmans.celebcompare;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fonts();
    }

    private void Fonts(){
        //FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/topmodern.ttf");
    }
}