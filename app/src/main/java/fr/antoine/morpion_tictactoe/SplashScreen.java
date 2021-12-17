package fr.antoine.morpion_tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    protected void onCreate(Bundle AppCompatActivity) {
        super.onCreate(AppCompatActivity);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);


    }
}
