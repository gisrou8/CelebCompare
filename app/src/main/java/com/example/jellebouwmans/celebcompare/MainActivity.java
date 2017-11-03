package com.example.jellebouwmans.celebcompare;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void btnCameraClick(View v) {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageBitmap = (Bitmap) data.getExtras().get("data");

            //Convert to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent startNewActivity = new Intent(this, ResultActivity.class);
            startNewActivity.putExtra("image",byteArray);

            startActivity(startNewActivity);
        }
    }
}
