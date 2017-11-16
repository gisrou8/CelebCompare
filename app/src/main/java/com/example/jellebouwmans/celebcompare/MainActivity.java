package com.example.jellebouwmans.celebcompare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;

import KairosApi.Kairos;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;
    Bitmap imageBitmap;

    ImageButton imgbtnMale;
    ImageButton imgbtnFemale;
    byte[] imagebyteArray;
    Kairos kairos = new Kairos(this);
    String gender = "unkown";
    JSONArray malecelebs;
    JSONArray femalecelebs;
    String naam = "";
    Double percent = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        try {
            getFromGalleryFemale();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            getFromGalleryMale();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Fonts();

        imgbtnFemale = (ImageButton) findViewById(R.id.imgbtnFemale);
        imgbtnMale = (ImageButton) findViewById(R.id.imgbtnMale);

        imgbtnFemale.setBackgroundResource(R.drawable.genderfemale);
        imgbtnMale.setBackgroundResource(R.drawable.gendermale);
    }


    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private String encodeImage(byte[] b)
    {
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
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
        if(gender != "unkown"){
            dispatchTakePictureIntent();
        }
        else{
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Select gender first");
            dlgAlert.setTitle("Gender");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    //creates a temporary file and return the absolute file path
    public static String tempFileImage(Context context, Bitmap bitmap, String name) {

        File outputDir = context.getCacheDir();
        File imageFile = new File(outputDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(context.getClass().getSimpleName(), "Error writing file", e);
        }

        return imageFile.getAbsolutePath();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageBitmap = (Bitmap) data.getExtras().get("data");

            //Convert to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagebyteArray = stream.toByteArray();

            Intent startNewActivity = new Intent(this, ResultActivity.class);
            startNewActivity.putExtra("image",imagebyteArray);
            startNewActivity.putExtra("gender", gender);

            //startNewActivity.putExtra("naam", naam);
            //startNewActivity.putExtra("percent", percent);

            startActivity(startNewActivity);
        }
        else if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageBitmap = BitmapFactory.decodeStream(imageStream);

            //Convert to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            try {
                verify(encodeImage(byteArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            Intent startNewActivity = new Intent(this, ResultActivity.class);
            startNewActivity.putExtra("naam", naam);
            startNewActivity.putExtra("percent", percent);

            startActivity(startNewActivity);
        }
    }

    public Double[] verify(final String encodeimage) throws JSONException {
        Double[] condition = null;

        if(gender.equals("Male"))
        {
            for(int i = 0; i <= malecelebs.length(); i++)
            {
                try {
                    condition = kairos.verifyImage(malecelebs.getString(i), encodeimage, "MyGalleryMale");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Delay hier ////////////////////////////////////////////////////

                // Pak de hoogste uit de lijst
                if (condition[0] > percent){
                    percent = condition[0];
                    naam = malecelebs.getString(i);
                }
            }

        }
        return condition;
    }


    public void getFromGalleryMale() throws InterruptedException {
        kairos.getAllFromGallery();

        //Delay hier //////////////////////////////////////////
        malecelebs = kairos.getMalecelebs();
    }

    public void getFromGalleryFemale() throws InterruptedException {

        kairos.getAllFromGalleryFemale();

        //Delay hier //////////////////////////////////////////
        femalecelebs = kairos.getFemalecelebs();
    }

    public void btnMaleClick(View v){
        imgbtnMale.setBackgroundResource(R.drawable.gendermaleclick);
        imgbtnFemale.setBackgroundResource(R.drawable.genderfemale);
        gender = "Male";
    }

    public void btnFemaleClick(View v){
        imgbtnFemale.setBackgroundResource(R.drawable.genderfemaleclick);
        imgbtnMale.setBackgroundResource(R.drawable.gendermale);
        gender = "Female";
    }

    public void btnFileClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}
