package fr.antoine.morpion_tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //Attributs
    private Button playerActivityButton;
    private Button rulesButton;
    private Button aboutButton;
    private Button quitButton;
    private ImageView soundImage;
    private ImageView muteSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerActivityButton = (Button) findViewById(R.id.playButton);
        rulesButton = (Button) findViewById(R.id.rulesButton);
        aboutButton = (Button) findViewById(R.id.aboutButton);
        quitButton = (Button) findViewById(R.id.quitButton);
        soundImage = (ImageView) findViewById(R.id.soundImage);
        muteSound = (ImageView) findViewById(R.id.muteImage);

        Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
        startService(intent);


        playerActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayerActivity();
            }
        });

        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRuleActivity();
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutActivity();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitActivity();
            }
        });

        soundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundImage.setVisibility(View.GONE);
                stopService(intent);
                muteSound.setVisibility(View.VISIBLE);
            }
        });


        muteSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muteSound.setVisibility(View.GONE);
                startService(intent);
                soundImage.setVisibility(View.VISIBLE);
            }
        });
    }

    public void openPlayerActivity(){
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    public void openRuleActivity(){
        Intent intent = new Intent(this, Rule.class);
        startActivity(intent);
    }

    public void openAboutActivity(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void quitActivity(){
        moveTaskToBack(false);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }


}