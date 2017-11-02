package com.example.jellebouwmans.celebcompare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    public TextView txtCeleb;
    public ImageView imgCeleb;
    public ImageView imgYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtCeleb = (TextView) findViewById(R.id.txtCeleb);
        imgCeleb = (ImageView) findViewById(R.id.imgCeleb);
        imgYou = (ImageView) findViewById(R.id.imgYou);
    }



}
