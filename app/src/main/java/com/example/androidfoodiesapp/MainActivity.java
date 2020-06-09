package com.example.androidfoodiesapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;
    TextView txtMotto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn= (Button)findViewById(R.id.btnSignIn);
        btnSignUp= (Button)findViewById(R.id.btnSignUp);
        txtMotto = (TextView)findViewById(R.id.txtMotto);

        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/AlexBrush-Regular.ttf");
        txtMotto.setTypeface(face);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn= new Intent(MainActivity.this,SignIn.class);
                startActivity(signIn);



            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp= new Intent(MainActivity.this,SignUp.class);
                startActivity(signUp);

            }
        });
    }
}
