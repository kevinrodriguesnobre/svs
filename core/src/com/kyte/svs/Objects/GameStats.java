package com.kyte.svs.Objects;

/**
 * Hält die aktuellen Stats in dem jeweiligen Spiel
 */
public class GameStats
{
    private double _spielzeit;
    private double _startzeit;

    private int _killCounter;

    private int _roundsShot;

    public GameStats(double startzeit)
    {
        _startzeit = startzeit;
    }

    public double getPlayTime()
    {
        return System.currentTimeMillis() - _startzeit;
    }

    public int getKillCounter() {
        return _killCounter;
    }

    public void setKillCounter(int killCounter) {
        _killCounter = killCounter;
    }

    public int getRoundsShot() {
        return _roundsShot;
    }

    public void setRoundsShot(int roundsShot) {
        _roundsShot = roundsShot;
    }
}
