package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


public class Game {
    //context is a reference to the activity
    private Context context;
    private int points = 0;

    //bitmap of the pacman
    private Bitmap pacBitmap;

    //textview reference to points
    private TextView pointsView;

    private int pacx, pacy;

    //the list of goldcoins - initially empty
    private ArrayList<GoldCoin> coins = new ArrayList<>();
    //the list of enemy - initially empty
    private ArrayList<Enemy> enemies = new ArrayList<>();

    //a reference to the gameview
    private GameView gameView;
    private int h,w; //height and width of screen

    private boolean coinsInitialised = false;
    private boolean enemyInitialized = false;

    public boolean gameOver = false;
    public int levelTime = 60;
    private TextView timerView;


    public boolean running = false;
    public int direction = 0;



    public boolean getCoinsInitialised() {
        return coinsInitialised;
    }

    //check if enemy is initialized
    public boolean getEnemyInitialized() {
        return enemyInitialized;
    }


    public Game(Context context, TextView view, TextView timerView)
    {
        this.context = context;
        this.pointsView = view;
        this.timerView = timerView;
        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);

    }

    public void setGameView(GameView view)
    {
        this.gameView = view;
    }


    public void initCoins() {

        for (int i = 0; i < 10; i++) {

            Random r = new Random();

            int x = r.nextInt(w);
            int y = r.nextInt(h);

            coins.add(new GoldCoin(x,y));
        }
        coinsInitialised = true;
    }


    // Init enemies
    public void initEnemy()
    {
        for (int i = 0; i < 1; i++) {

            Random r = new Random();

            int x = r.nextInt(w);
            int y = r.nextInt(h);

            enemies.add(new Enemy(x,y));
        }
        enemyInitialized = true;
    }


    public void newGame()
    {

        pacx = 50;
        pacy = 400; //just some starting coordinates
        //reset the points
        points = 0;
        setTimerText();
        setPointsDisplay();
        gameView.invalidate(); //redraw screen
    }

    public void setSize(int h, int w)
    {
        this.h = h;
        this.w = w;
    }

    public void movePacmanRight(int pixels)
    {
        doCollisionCheck();
        //still within our boundaries?
        if (pacx+pixels+pacBitmap.getWidth()<w) {
            pacx = pacx + pixels;
            gameView.invalidate();
        }
    }


    public void movePacmanLeft(int pixels)
    {
        doCollisionCheck();
        //still within our boundaries?
        if (pacx-pixels+pacBitmap.getWidth()<w && pacx > 0) {
            pacx = pacx - pixels;
            gameView.invalidate();
        }
    }

    public void movePacmanUp(int pixels)
    {
        doCollisionCheck();
        //still within our boundaries?
        if (pacy-pixels+pacBitmap.getHeight()<h && pacy > 0) {
            pacy = pacy - pixels;
            gameView.invalidate();
        }
    }

    public void movePacmanDown(int pixels)
    {
        doCollisionCheck();
        //still within our boundaries?
        if (pacy+pixels+pacBitmap.getHeight()<h) {
            pacy = pacy + pixels;
            gameView.invalidate();
        }
    }

    // check if the pacman touches a gold coin
    public void doCollisionCheck()
    {

        int radius = 100;
        int pax = pacx + pacBitmap.getWidth() / 2;
        int pay = pacy + pacBitmap.getHeight() / 2;
        for (GoldCoin coin : getCoins()) {
            int a = Math.abs(pax - coin.getCoinx());
            int b = Math.abs(pay - coin.getCoiny());
            if(Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)) < radius) {

                gameView.invalidate();

                if(!coin.isTaken()) {
                    coin.setTaken(true);
                    points++;
                    setPointsDisplay();
                    System.out.println(points);
                    System.out.println("Hit");

                    //increase enemy speed when pacman takes points
                    for (Enemy enemy : getEnemies()) {
                        if (getPoints() == 3) {
                            enemy.increaseSpeed(2);
                        } else if (getPoints() == 6) {
                            enemy.increaseSpeed(3);
                        }
                    }
                }

            }
        }

        for(Enemy enemy : getEnemies()) {
            enemy.goToPacMan(pax, pay);
            gameView.invalidate();
            int a = Math.abs(pax - enemy.getEnemyX());
            int b = Math.abs(pay - enemy.getEnemyY());
            if (Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)) < radius) {
                running = false;
                gameOver = true;
                Toast toast = Toast.makeText(context.getApplicationContext(), "You lost", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }




    public int getPacx()
    {
        return pacx;
    }

    public int getPacy()
    {
        return pacy;
    }

    public int getPoints()
    {
        return points;
    }


    public void setPointsDisplay() {
        pointsView.setText(context.getResources().getString(R.string.points)+" "+points);
        if (points == 10) {
            Toast.makeText(context.getApplicationContext(),"You WIN!",Toast.LENGTH_SHORT).show();
            running = false;
        }
    }

    public void setTimerText() {
        timerView.setText(context.getResources().getString(R.string.levelTimer) + " " + levelTime);

        if (levelTime == 0) {
            direction = 0;
            running = false;
            gameOver = true;
            Toast.makeText(context.getApplicationContext(),"END OF GAME!",Toast.LENGTH_LONG).show();
        }
    }


    public ArrayList<GoldCoin> getCoins()
    {
        return coins;
    }

    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }

    public Bitmap getPacBitmap()
    {
        return pacBitmap;
    }


}
