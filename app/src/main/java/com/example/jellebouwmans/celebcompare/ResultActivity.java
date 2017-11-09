package com.example.jellebouwmans.celebcompare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

<<<<<<< HEAD
import java.io.File;
=======
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import KairosApi.Kairos;
>>>>>>> 2361bb54464d78d8e8367bf7e7a674de5b8089c8

public class ResultActivity extends AppCompatActivity {

    public TextView txtCeleb;
    public TextView txtPercent;
    public ImageView imgCeleb;
    public ImageView imgYou;
    private Bitmap imageBitmap;

    private String celebNaam = "";
    private int celebPercent = 0;

    Map<String, String> celebPhotos = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtCeleb = (TextView) findViewById(R.id.txtCeleb);
        imgCeleb = (ImageView) findViewById(R.id.imgCeleb);
        imgYou = (ImageView) findViewById(R.id.imgYou);
        txtPercent = (TextView) findViewById(R.id.txtPercent);

        String filepath = getIntent().getStringExtra("image");
        File file = new File(filepath);
        imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        celebNaam = getIntent().getStringExtra("naam");
        celebPercent = getIntent().getIntExtra("percent", 0);

        imgYou.setImageBitmap(imageBitmap);

        Fonts();

        fillCelebList();
        try {
            fillViews();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillCelebList(){
        celebPhotos.put("Erica Terpstra", "https://i.imgur.com/JeArc2h.jpg");
        celebPhotos.put("Tatjana Šimić", "https://i.imgur.com/EsKCk6y.jpg");
        celebPhotos.put("Chantal Janzen", "https://i.imgur.com/MKBmngA.jpg");
        celebPhotos.put("Michelle Obama", "https://i.imgur.com/uWqkm8c.png");
        celebPhotos.put("Kim Kardashian", "https://i.imgur.com/4wOUtFl.jpg");
        celebPhotos.put("Snooki", "https://i.imgur.com/32HIhBC.png");
        celebPhotos.put("Angelina Jolie", "https://i.imgur.com/kyemvIC.png");
        celebPhotos.put("Boef", "https://i.imgur.com/HO1vDCa.jpg");
        celebPhotos.put("Barack Obama", "https://i.imgur.com/F0WKnlX.png");
        celebPhotos.put("George Clooney", "https://i.imgur.com/shKIBht.png");
        celebPhotos.put("Frans Bauer", "https://i.imgur.com/CsSAnwo.jpg");
        celebPhotos.put("Gerard Joling", "https://i.imgur.com/jjGbTQp.png");
        celebPhotos.put("Geert Wilders", "https://i.imgur.com/blY39TO.png");
        celebPhotos.put("Brad Pitt", "https://i.imgur.com/Q2XgtTZ.png");
        celebPhotos.put("Johny Depp", "https://i.imgur.com/wxBiSKH.png");
    }

    private void Fonts(){
        //FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/topmodern.ttf");

        //Textfield Username
        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setTypeface(custom_font);

        txtCeleb.setTypeface(custom_font);

        Button btnNewPhoto = (Button)findViewById(R.id.btnNewPhoto);
        btnNewPhoto.setTypeface(custom_font);

        txtPercent.setTypeface(custom_font);
    }

    public void btnNewPhotoClick(View v){
        Intent startNewActivity = new Intent(this, MainActivity.class);
        startActivity(startNewActivity);
    }

    public void fillViews() throws IOException {
        //URL to Bitmap
        URL url = new URL(celebPhotos.get(celebNaam));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();

        Bitmap imageCeleb = BitmapFactory.decodeStream(input);

        txtCeleb.setText(celebNaam);
        txtPercent.setText("Confidence: " + celebPercent * 100 + "%");
        if (imageCeleb != null) imgCeleb.setImageBitmap(imageCeleb);
    }
}