package com.cabral.lucas.nacaotrezeana.model;

/**
 * Created by Lucas on 12/01/2017.
 */

public class Game {
    private String home, visit, time, date, local;

    public Game(String home, String visit, String time, String date, String local){
        this.home = home;
        this.visit = visit;
        this.time = time;
        this.date = date;
        this.local = local;
    }

    public String getHome() {
        return home;
    }

    public String getVisit() {
        return visit;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getLocal() {
        return local;
    }
}