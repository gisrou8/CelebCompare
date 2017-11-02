package com.example.jellebouwmans.celebcompare;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fonts();
    }

    private void Fonts(){
        //FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/topmodern.ttf");

        //Textfield Username
        TextView tx = (TextView)findViewById(R.id.txtPassword);
        tx.setTypeface(custom_font);

        //Textfield Password
        TextView pass = (TextView)findViewById(R.id.txtUsername);
        pass.setTypeface(custom_font);

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setTypeface(custom_font);
    }

    public void btnLoginClick(View v){
        Intent startNewActivity = new Intent(this, MainActivity.class);
        startActivity(startNewActivity);
    }
}
