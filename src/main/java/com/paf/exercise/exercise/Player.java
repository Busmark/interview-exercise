package com.paf.exercise.exercise;

public class Player {

    private int player_id;
    private String player_name;
    private int fk_id;

    public Player(int player_id, String player_name, int fk_id) {
        this.player_id = player_id;
        this.player_name = player_name;
        this.fk_id = fk_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getFk_id() {
        return fk_id;
    }

    public void setFk_id(int fk_id) {
        this.fk_id = fk_id;
    }
}
