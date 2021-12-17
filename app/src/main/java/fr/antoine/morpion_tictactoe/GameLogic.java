package fr.antoine.morpion_tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameLogic {

    private int[][] gameBoard;
    private int player = 1;
    private int[] winType = {-1, -1, -1};                         //Premier élement --> rangée, deuxieme élement --> colonnes, troisieme element --> line
    private Button playAgainButton;
    private Button homeButton;
    private TextView playerTurn;
    private String[] playerName = {"Player 1", "Player 2"};
    private TextView playerOneScore;
    private Dialog dialogStat;
    private TextView player1;
    private TextView timeGame;
    private TextView player2;
    private TextView playerTwoScore;
    private TextView playerTwoWinRate;
    private TextView playerOneWinRate;
    private Button statButton;
    private int nbTie;
    private TextView playerOneWin;
    private TextView playerTwoWin;
    private int scorePlayerOne;
    private TextView nbGames;
    private TextView tieGame;
    private int scorePlayerTwo;
    private Dialog dialog;
    private ImageView imageViewClose;
    private Button buttonClose;
    private TextView playerT;
    private Handler handler = new Handler();
    private Runnable runnable;


    public GameLogic() {
        long startTime = System.currentTimeMillis();


        gameBoard = new int[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }

        runnable = () -> {
            handler.postDelayed(runnable, 1000);
            long endTime = System.currentTimeMillis();
            long secT = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
            long sec = secT % 60;
            long min = (secT - sec) / 60;
            timeGame.setText(String.valueOf(min + "min" + sec + "sec"));
        };

    }

    public boolean updateGameBoard(int row, int col) {
        if (gameBoard[row - 1][col - 1] == 0) {
            gameBoard[row - 1][col - 1] = player;

            if (player == 1) {
                playerTurn.setText(("Au tour de : " + playerName[1]));
            } else {
                playerTurn.setText(("Au tour de : " + playerName[0]));
            }
            return true;
        } else {
            return false;
        }

    }

    public boolean winnerCheck() throws InterruptedException {

        boolean isWinner = false;
        int t1 = 0, t2 = 0;


        //Vérifie si les cases ont la même valeur horizontalement
        for (int r = 0; r < 3; r++) {
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0) {
                winType = new int[]{r, 0, 1};
                isWinner = true;

                if (playerName[player - 1] == playerName[0]) {
                    scorePlayerOne++;
                    playerOneScore.setText(String.valueOf(scorePlayerOne));

                } else {
                    scorePlayerTwo++;
                    playerTwoScore.setText(String.valueOf(scorePlayerTwo));
                }


            }
        }

        //Vérifie si les cases ont la même valeur verticalement
        for (int c = 0; c < 3; c++) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[2][c] == gameBoard[0][c] && gameBoard[0][c] != 0) {
                isWinner = true;
                winType = new int[]{0, c, 2};
                if (playerName[player - 1] == playerName[0]) {
                    scorePlayerOne++;
                    playerOneScore.setText(String.valueOf(scorePlayerOne));
                } else {
                    scorePlayerTwo++;
                    playerTwoScore.setText(String.valueOf(scorePlayerTwo));
                }

            }
        }

        //Vérifie si les cases ont la même valeur en diagonale (en partant en haut a gauche)
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            isWinner = true;
            winType = new int[]{0, 2, 3};
            if (playerName[player - 1] == playerName[0]) {
                scorePlayerOne++;
                playerOneScore.setText(String.valueOf(scorePlayerOne));
            } else {
                scorePlayerTwo++;
                playerTwoScore.setText(String.valueOf(scorePlayerTwo));
            }

        }

        //Vérifie si les cases ont la même valeur en diagonale (en partant en haut a droite)
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0) {
            isWinner = true;
            winType = new int[]{2, 2, 4};
            if (playerName[player - 1] == playerName[0]) {
                scorePlayerOne++;
                playerOneScore.setText(String.valueOf(scorePlayerOne));
            } else {
                scorePlayerTwo++;
                playerTwoScore.setText(String.valueOf(scorePlayerTwo));
            }

        }

        int boardFilled = 0;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] != 0) {
                    boardFilled += 1;
                }
            }
        }


        if (isWinner) {


            playAgainButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            statButton.setVisibility(View.VISIBLE);
            playerTurn.setText((playerName[player - 1] + " à gagné !"));

            dialog.setContentView(R.layout.win_layout_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            playerT = dialog.findViewById(R.id.pTurn);
            imageViewClose = dialog.findViewById(R.id.imageViewClose);
            buttonClose = dialog.findViewById(R.id.buttonClose);
            playerT.setText((playerName[player - 1] + " à gagné !"));

            dialog.show();

            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            imageViewClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            showDialogStat();

            return true;


        } else if (boardFilled == 9) {


            nbTie++; //Variables qui récupère le nombre de partie nulle
            playAgainButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            statButton.setVisibility(View.VISIBLE);
            playerTurn.setText("Il y a égalité");


            dialog.setContentView(R.layout.tie_layout_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            imageViewClose = dialog.findViewById(R.id.imageViewClose);
            buttonClose = dialog.findViewById(R.id.buttonClose);
            dialog.show();

            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            imageViewClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            showDialogStat();

            return false;

        } else {
            return false;
        }


    }

    public void resetGame() {

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }

        player = 1;
        playAgainButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        statButton.setVisibility(View.GONE);
        winType = new int[]{-1, -1, -1};
        playerTurn.setText(("Au tour de : " + playerName[0]));


    }

    public void showDialogStat() {

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogStat.setContentView(R.layout.stat_layout_dialog);
                dialogStat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                imageViewClose = dialogStat.findViewById(R.id.imageViewClose);
                buttonClose = dialogStat.findViewById(R.id.buttonClose);
                player1 = dialogStat.findViewById(R.id.player1);
                player2 = dialogStat.findViewById(R.id.player2);
                nbGames = dialogStat.findViewById(R.id.nbGames);
                tieGame = dialogStat.findViewById(R.id.tieGame);
                playerOneWin = dialogStat.findViewById(R.id.playerOneWin);
                playerOneWinRate = dialogStat.findViewById(R.id.playerOneWinRate);
                playerTwoWin = dialogStat.findViewById(R.id.playerTwoWin);
                playerTwoWinRate = dialogStat.findViewById(R.id.playerTwoWinRate);
                timeGame = dialogStat.findViewById(R.id.timeGame);

                int numGames = getScorePlayerOne() + getScorePlayerTwo();
                nbGames.setText(String.valueOf(numGames));
                tieGame.setText(String.valueOf(nbTie));
                player1.setText(String.valueOf(playerName[0]));
                player2.setText(String.valueOf(playerName[1]));
                playerOneWin.setText(String.valueOf(getScorePlayerOne()));
                float WinRate1 = (float) getScorePlayerOne() / numGames * 100;
                DecimalFormat numberFormat = new DecimalFormat("#0");
                playerOneWinRate.setText(String.valueOf(numberFormat.format(WinRate1)) + "%");

                playerTwoWin.setText(String.valueOf(getScorePlayerTwo()));

                float WinRate2 = (float) getScorePlayerTwo() / numGames * 100;
                playerTwoWinRate.setText(String.valueOf(numberFormat.format(WinRate2)) + "%");

                handler.postDelayed(runnable, 1000);


                dialogStat.show();

                buttonClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogStat.dismiss();
                        handler.removeCallbacks(runnable);
                    }
                });

                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogStat.dismiss();
                        handler.removeCallbacks(runnable);
                    }
                });
            }
        });

    }


    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setPlayAgainButton(Button playAgainButton) { this.playAgainButton = playAgainButton; }

    public void setHomeButton(Button homeButton) {
        this.homeButton = homeButton;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerName(String[] playerName) {
        this.playerName = playerName;
    }

    public int[] getWinType() {
        return winType;
    }

    public void setPlayerOneScore(TextView playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public void setPlayerTwoScore(TextView playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public int getScorePlayerOne() {
        return scorePlayerOne;
    }

    public int getScorePlayerTwo() {
        return scorePlayerTwo;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void setImageViewClose(ImageView imageViewClose) { this.imageViewClose = imageViewClose; }

    public void setButtonClose(Button buttonClose) {
        this.buttonClose = buttonClose;
    }

    public void setStatButton(Button statButton) {
        this.statButton = statButton;
    }

    public void setDialogStat(Dialog dialogStat) { this.dialogStat = dialogStat; }

}
