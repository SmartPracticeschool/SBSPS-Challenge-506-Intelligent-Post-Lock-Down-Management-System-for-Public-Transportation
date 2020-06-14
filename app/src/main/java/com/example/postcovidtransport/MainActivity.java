package com.example.postcovidtransport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button mLoginBtn, mSignupBtn;
    ImageView logoimage;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title= findViewById(R.id.title);
        mLoginBtn = findViewById(R.id.login);
        mSignupBtn = findViewById(R.id.signup);
        logoimage = findViewById(R.id.logoimg);

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("A","sign up is caleed");
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(),Signup.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}