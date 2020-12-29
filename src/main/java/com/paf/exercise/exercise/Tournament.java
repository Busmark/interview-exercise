package com.paf.exercise.exercise;

public class Tournament {

    private int tournament_id;
    private int reward_amount;

    public Tournament() {
    }

    public Tournament(int tournament_id, int reward_amount) {
        this.tournament_id = tournament_id;
        this.reward_amount = reward_amount;
    }

    public int getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public int getReward_amount() {
        return reward_amount;
    }

    public void setReward_amount(int reward_amount) {
        this.reward_amount = reward_amount;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                ", tournament_id=" + tournament_id +
                ", reward_amount=" + reward_amount +
                '}';
    }
}
