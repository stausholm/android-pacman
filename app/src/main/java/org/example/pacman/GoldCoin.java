package org.example.pacman;



public class GoldCoin {
    private int coinx, coiny;
    private boolean taken = false;

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public int getCoinx() {
        return coinx;
    }

    public int getCoiny() {
        return coiny;
    }


    public GoldCoin(int coinx, int coiny) {
        this.coinx = coinx;
        this.coiny = coiny;
    }
}
