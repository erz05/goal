package com.goal.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goal.R;
import com.goal.listeners.GameListener;
import com.goal.listeners.MapListener;
import com.goal.listeners.MenuListener;
import com.goal.listeners.OnPauseListener;
import com.goal.views.BackgroundView;
import com.goal.views.Game;
import com.goal.views.Map;
import com.goal.views.Menu;
import com.goal.views.Pause;

public class MainActivity extends Activity implements GameListener, MenuListener, MapListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, OnPauseListener {

    private static final String PREFS_NAME = "MyPrefsFile";
    private int level;
    private int score = 0;
    private Game game;
    private Menu menu;
    private Map map;
    //private BackgroundView background;
    private Pause pauseView;
    private boolean paused = true;

    private GestureDetector detector;

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //background = new BackgroundView(this);
        game = new Game(this);
        game.setListener(this);
        pauseView = new Pause(this);
        pauseView.setListener(this);

        FrameLayout menuLayout = (FrameLayout) findViewById(R.id.menuLayout);
        menu = new Menu(this);
        menu.setListener(this);
        menuLayout.addView(menu);

        detector = new GestureDetector(this, this);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    }

    @Override
    public void onStop(){
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.commit();
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        game = null;
        menu = null;
        map = null;
        //background = null;
        pauseView = null;
    }

    @Override
    public void onBackPressed(){
        if(paused){
            super.onBackPressed();
        }else {
            game.pause();
            RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
            contentLayout.addView(pauseView);
            paused = true;
        }
        //todo pause/are you sure you want to quit
    }

    @Override
    public void onPlay() {
        map = new Map(this);
        map.setListener(this);
        FrameLayout menuLayout = (FrameLayout) findViewById(R.id.menuLayout);
        menuLayout.removeView(menu);
        menuLayout.addView(map);
        menu = null;
        System.gc();
    }

    @Override
    public void onLevelSelected() {
        if(game != null){
            FrameLayout gameLayout = (FrameLayout) findViewById(R.id.gameLayout);
            gameLayout.addView(game);
            //gameLayout.addView(background);
            game.start();
            paused = false;

            ImageView playLevel = (ImageView) findViewById(R.id.playLevel);
            ImageView replayLevel = (ImageView) findViewById(R.id.replayLevel);

            playLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    game.shootBall();
                }
            });

            replayLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    game.resetLevel();
                }
            });

            FrameLayout menuLayout = (FrameLayout) findViewById(R.id.menuLayout);
            menuLayout.removeView(map);
            map = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        detector.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        game.createPlayer((int)e.getX(), (int)e.getY());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onPause(boolean quit) {
        if(quit){
            paused = true;
            onBackPressed();
        }else {
            paused = false;
            RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_frame);
            contentLayout.removeView(pauseView);
            game.resume();
        }
    }

    @Override
    public void setScore(final int score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                score(score);
            }
        });
    }

    public void score(int score){
        TextView scoreText = (TextView) findViewById(R.id.score);
        this.score += score;
        scoreText.setText("Score: "+this.score);
    }
}