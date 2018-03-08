package org.example.pacman;


public class Enemy {
    private int enemyX, enemyY;
    private int speed = 1;
    private int maxSpeed = 3;

    public void increaseSpeed(int newSpeed) {
        if (this.speed <= maxSpeed) {
            this.speed = newSpeed;
        }
    }

    public int getEnemyX() {
        return enemyX;
    }

    public int getEnemyY() {
        return enemyY;
    }


    public void goToPacMan(int pacX, int pacY) {
        if (this.enemyX > pacX) {
            this.enemyX-= speed;
        }
        if (this.enemyX < pacX) {
            this.enemyX+= speed;
        }
        if (this.enemyY > pacY) {
            this.enemyY-= speed;
        }
        if (this.enemyY < pacY) {
            this.enemyY+= speed;
        }
    }

    public Enemy(int enemyX, int enemyY) {
        this.enemyX = enemyX;
        this.enemyY = enemyY;
    }
}
