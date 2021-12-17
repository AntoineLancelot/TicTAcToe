package fr.antoine.morpion_tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private TextView playerOne;
    private TextView playerTwo;
    private TextView playerTurn;
    private Button homeButton;
    private Button playAgainButton;
    private Dialog dialogStat;
    private TicTacToeBoard ticTacToeBoard;
    private TextView playerOneScore;
    private TextView playerTwoScore;
    private Dialog dialog;
    private Button statButton;
    private ImageView imageViewClose;
    private Button buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        dialog = new Dialog(this);
        dialogStat = new Dialog(this);
        GameLogic game = new GameLogic();

        playerOne = (TextView) findViewById(R.id.playerOne);
        playerTwo = (TextView) findViewById(R.id.playerTwo);
        playerTurn = (TextView) findViewById(R.id.playerTurn);
        homeButton = (Button) findViewById(R.id.homeButton);
        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);
        playAgainButton = findViewById(R.id.playAgainButton);
        statButton = findViewById(R.id.statButton);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);

        //Les bouttons "rejouer" et "acceuil" sont cachés au lancement de l'activité
        playAgainButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        statButton.setVisibility(View.GONE);

        //On récupère le nom des joueurs saisi et on remplace le TextView par le nom des joueurs
        String [] playerNames = getIntent().getStringArrayExtra("PLAYER_NAME");
        playerOne.setText(playerNames[0]);  //playerNames[0] -> joueur 1
        playerTwo.setText(playerNames[1]);  //playerNames[1] -> joueur 2

        if(playerNames != null){
            playerTurn.setText(("Au tour de : " + playerNames[0]));
        }


        ticTacToeBoard.setUpGame(playAgainButton, homeButton, playerTurn, playerNames, playerOneScore, playerTwoScore, dialog,imageViewClose,buttonClose, statButton,dialogStat);


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticTacToeBoard.resetGame();
                ticTacToeBoard.invalidate();
            }
        });


    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}