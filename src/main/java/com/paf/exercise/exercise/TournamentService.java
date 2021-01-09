package com.paf.exercise.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TournamentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public TournamentService() {
//    }

    public List<Exercise> getTournaments() {
        //hämta alla Tournaments från databasen.
        List<Tournament> tournamentList;
        tournamentList = jdbcTemplate.query("select * from tournament", (resultSet, i) -> new Tournament(
                resultSet.getInt("tournament_id"),
                resultSet.getInt("reward_amount")));
        //"Serialisera" från Tournament till Exercise för att inte exponera db-pojo.
        List<Exercise> exerciseList = new ArrayList<>();
        tournamentList.forEach(tournament -> {
            Exercise e = new Exercise();
            e.tournament_id = tournament.getTournament_id();
            e.reward_amount = tournament.getReward_amount();
            exerciseList.add(e);
        });
        return exerciseList;
    }

    public void addTournament(Map<String, String> allParams) {
        String reward_amount = allParams.get("reward_amount");
        Random random = new Random();
        jdbcTemplate.execute("insert into tournament(tournament_id, reward_amount) " +
                "values(" + random.nextInt() + ", "  + reward_amount +  ")");
    }

    public List<Exercise> getPlayersInTournament(Map<String, String> allParams) {
        String tournament_id = allParams.get("tournament_id");
        //hämta alla Players från databasen.
        List<Player> playerList;
        playerList = jdbcTemplate.query("select * from player where fk_id=" + tournament_id, (resultSet, i) -> new Player(
                resultSet.getInt("player_id"),
                resultSet.getString("player_name"),
                resultSet.getInt("fk_id")));
        //"Serialisera" från Tournament till Exercise för att inte exponera db-pojo.
        List<Exercise> exerciseList = new ArrayList<>();
        playerList.forEach(player -> {
            Exercise e = new Exercise();
            e.tournament_id = player.getFk_id();
            e.player_id = player.getPlayer_id();
            e.player_name = player.getPlayer_name();
            exerciseList.add(e);
        });
        return exerciseList;
    }

    public void updateTournament(Map<String, String> allParams) {
        String reward_amount = allParams.get("reward_amount");
        String tournament_id = allParams.get("tournament_id");
        jdbcTemplate.execute("update tournament set reward_amount=" + reward_amount +
                " where tournament_id=" + tournament_id);
    }

    public void removeTournament(Map<String, String> allParams) {
        String tournament_id = allParams.get("tournament_id");
        jdbcTemplate.execute("delete from tournament where tournament_id=" + tournament_id);
    }

    public void addPlayerIntoTournament(Map<String, String> allParams) {
        Random random = new Random();
        String p_name = allParams.get("player_name");
        String tournament_id = allParams.get("tournament_id");

        jdbcTemplate.execute("insert into player(player_id, player_name, fk_id) " +
                "values(" + random.nextInt() + ", '" + p_name + "', " + tournament_id +  ")");
    }

    public void removePlayerFromTournament(Map<String, String> allParams) {
        String tournament_id = allParams.get("tournament_id");
        String player_id = allParams.get("player_id");
        jdbcTemplate.execute("delete from player where fk_id=" + tournament_id
                + " and player_id=" + player_id);
    }


    // Objekt att exponera mot frontend.
    static class Exercise {
        public int tournament_id;
        public int reward_amount;
        public int player_id;
        public String player_name;
    }
}
