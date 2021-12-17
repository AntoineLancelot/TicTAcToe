package fr.antoine.morpion_tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputLayout;

public class PlayerActivity extends AppCompatActivity {

    private Button playButton;
    public  EditText playerOneName;
    public  EditText playerTwoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playButton = (Button) findViewById(R.id.playButton);
        playerOneName = findViewById(R.id.playerOneName);
        playerTwoName = findViewById(R.id.playerTwoName);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
    }

    public void openGameActivity(){


            String player1Name = playerOneName.getText().toString();
            String player2Name = playerTwoName.getText().toString();
            if(player1Name.matches("") && player2Name.matches("")) {
                playerOneName.setError("Veuillez saisir le nom du joueur");
                playerTwoName.setError("Veuillez saisir le nom du joueur");
                return;
            }
            if(player2Name.matches("")){
                playerTwoName.setError("Veuillez saisir le nom du joueur");
                return;
            }
            if(player1Name.matches("")){
                playerOneName.setError("Veuillez saisir le nom du joueur");
                return;
            }


            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("PLAYER_NAME", new String[]{player1Name, player2Name});
            startActivity(intent);
    }
}