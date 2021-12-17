package fr.antoine.morpion_tictactoe;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


public class SoundPlayer{


    private static SoundPool soundPool;
    private static int snareSound;
    private static int winningSound;

    public SoundPlayer(Context context){

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);

        snareSound = soundPool.load(context,R.raw.hit,1);
        winningSound = soundPool.load(context,R.raw.winning,1);

    }

    public void playSnare()
    {
        soundPool.play(snareSound,1.5f,1.5f,1,0,1.0f);
    }
    public void playWinningsound() { soundPool.play(winningSound,1.0f,1.0f,1,0,1.0f); }

}
