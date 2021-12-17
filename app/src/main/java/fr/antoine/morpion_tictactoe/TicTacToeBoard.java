package fr.antoine.morpion_tictactoe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;

    private final int winningLineColor;
    private boolean winningLine = false;
    private SoundPlayer sound;

    private Dialog dialog;
    private Dialog dialogStat;
    private Button statButton;

    private final Paint paint = new Paint();
    private final GameLogic game;
    private int cellSize = getWidth()/3;


    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();
        dialog = new Dialog(context);
        dialogStat = new Dialog(context);
        sound = new SoundPlayer(context);
        statButton = findViewById(R.id.statButton);



        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard, 0, 0);
        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);


        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension/3;


        setMeasuredDimension(dimension, dimension);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
            sound.playWinningsound();
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){

        //X et Y vont prendre la position ou l'utilisateur clique
        float x = event.getX();
        float y = event.getY();

         int action = event.getAction();

         if (action == MotionEvent.ACTION_DOWN){
             int row = (int) Math.ceil(y/cellSize);
             int col = (int) Math.ceil(x/cellSize);

             if(!winningLine) {

                 //mise a jour des tour entre les joueurs
                 if (game.updateGameBoard(row, col)) {
                     invalidate();

                     try {
                         if(game.winnerCheck()){
                             winningLine = true;
                             invalidate();
                         }

                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }

                     if (game.getPlayer() % 2 == 0) {
                         game.setPlayer(game.getPlayer() - 1);
                     } else {
                         game.setPlayer(game.getPlayer() + 1);
                     }
                 }
             }

             invalidate();
             return true;
         }

         return false;
    }

    //Fonction qui permet de dessinner le plateau de jeu
    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for(int c=1;c<3;c++){
            canvas.drawLine(cellSize*c,0,cellSize*c, canvas.getWidth(), paint);
        }

        for(int r=1;r<3;r++){
            canvas.drawLine(0,cellSize*r, canvas.getWidth(),cellSize*r, paint);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawMarkers(Canvas canvas){
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
               if(game.getGameBoard()[r][c] != 0){
                    if(game.getGameBoard()[r][c] != 1){
                        drawX(canvas,r,c);
                        sound.playSnare();
                    }
                    else{
                       drawO(canvas,r,c);
                        sound.playSnare();
                    }
               }

            }
        }
    }

    //Fonction qui permet de dessiner une croix
    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(XColor);

        canvas.drawLine((float) ((col+1)*cellSize - cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) (col*cellSize + cellSize*0.2),
                        (float)((row+1)*cellSize - cellSize*0.2),
                        paint);

        canvas.drawLine((float) (col*cellSize + cellSize*0.2),
                (float)  (row*cellSize + cellSize*0.2),
                (float) ((col+1)*cellSize - cellSize*0.2),
                (float) ((row+1)*cellSize - cellSize*0.2),
                paint);
    }

    private void drawVerticalLine(Canvas canvas,int row,int col){
        canvas.drawLine(col*cellSize + cellSize/2, row,
                col*cellSize + cellSize/2, cellSize*3,
                paint);
    }

    private void drawDiagnolalLinepos(Canvas canvas){
        canvas.drawLine(0,cellSize*3,
                cellSize*3,0,
                paint);
    }

    private void drawDiagnolalLineNeg(Canvas canvas){
        canvas.drawLine(0,0,
                cellSize*3,cellSize*3,
                paint);
    }

    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        int line = game.getWinType()[0];

        switch(game.getWinType()[2]){
            case 1 :
                drawHorizontalLine(canvas,row,col);
                break;
            case 2 :
                drawVerticalLine(canvas,row,col);
                break;
            case 3 :
                drawDiagnolalLineNeg(canvas);
                break;
            case 4 :
                drawDiagnolalLinepos(canvas);
                break;

        }
    }

    //Fonction qui permet de dessiner un rond
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(OColor);

        canvas.drawOval((float) (col*cellSize + cellSize*0.2),
                (float) (row*cellSize + cellSize*0.2),
                (float) ((col*cellSize + cellSize) - cellSize*0.2),
                (float) (row*cellSize + cellSize - cellSize*0.2),
                         paint);

    }

    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }

    private void drawHorizontalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col,row*cellSize + cellSize/2,
                cellSize*3,row*cellSize + cellSize/2,
                paint);
    }

    public void setUpGame(Button playAgainButton, Button home, TextView player, String[] names, TextView playerOneScore, TextView playerTwoScore, Dialog dialog, ImageView imageViewClose, Button buttonClose, Button statButton, Dialog dialogStat){
        game.setPlayAgainButton(playAgainButton);
        game.setHomeButton(home);
        game.setPlayerTurn(player);
        game.setPlayerName(names);
        game.setPlayerOneScore(playerOneScore);
        game.setPlayerTwoScore(playerTwoScore);
        game.setDialog(dialog);
        game.setButtonClose(buttonClose);
        game.setImageViewClose(imageViewClose);
        game.setStatButton(statButton);
        game.setDialogStat(dialogStat);


    }

}
