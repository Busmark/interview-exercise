package com.paf.exercise.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@RestController
public class ExerciseApplication {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@GetMapping(value = "/exercise", params = {"operation=addTournament"})
	public void addTournament(@RequestParam Map<String,String> allParams) {
		String reward_amount = allParams.get("reward_amount");
		Random random = new Random();

		jdbcTemplate.execute("insert into tournament(tournament_id, reward_amount) " +
				"values(" + random.nextInt() + ", "  + reward_amount +  ")");
	}

	@GetMapping(value = "/exercise", params = {"operation=getTournaments"})
	public List<Exercise> getTournaments(@RequestParam Map<String,String> allParams) {
		//hämta alla Tournaments från databasen.
		List<Tournament> tournamentList;
		tournamentList = jdbcTemplate.query("select * from tournament", new RowMapper<Tournament>() {
			@Override
			public Tournament mapRow(ResultSet resultSet, int i) throws SQLException {
				return new Tournament(
						resultSet.getInt("tournament_id"),
						resultSet.getInt("reward_amount"));
			}});

		//"Serialisera" från Tournament till Exercise för att inte exponera db-pojo.
		final List<Exercise> exerciseList = new ArrayList<>();
		tournamentList.forEach(tournament -> {
			Exercise e = new Exercise();
			e.tournament_id = tournament.getTournament_id();
			e.reward_amount = tournament.getReward_amount();
			exerciseList.add(e);
			});
		return exerciseList;
	}

	@GetMapping(value = "/exercise", params = {"operation=getPlayersInTournament"})
	public List<Exercise> getPlayersInTournament(@RequestParam Map<String,String> allParams) {
		String tournament_id = allParams.get("tournament_id");

		//hämta alla Players från databasen.
		List<Player> playerList;
		playerList = jdbcTemplate.query("select * from player where fk_id=" + tournament_id, new RowMapper<Player>() {
			@Override
			public Player mapRow(ResultSet resultSet, int i) throws SQLException {
				return new Player(
						resultSet.getInt("player_id"),
						resultSet.getString("player_name"),
						resultSet.getInt("fk_id"));
			}});

		//"Serialisera" från Tournament till Exercise för att inte exponera db-pojo.
		final List<Exercise> exerciseList = new ArrayList<>();
		playerList.forEach(player -> {
			Exercise e = new Exercise();
			e.tournament_id = player.getFk_id();
			e.player_id = player.getPlayer_id();
			e.player_name = player.getPlayer_name();
			exerciseList.add(e);
		});
		return exerciseList;
	}


	@GetMapping(value = "/exercise", params = {"operation=updateTournament"})
	public void updateTournament(@RequestParam Map<String,String> allParams) {
		String reward_amount = allParams.get("reward_amount");
		String tournament_id = allParams.get("tournament_id");

		jdbcTemplate.execute("update tournament set reward_amount=" + reward_amount +
				" where tournament_id=" + tournament_id);
	}

	@GetMapping(value = "/exercise", params = {"operation=removeTournament"})
	public void removeTournament(@RequestParam Map<String,String> allParams) {
		String tournament_id = allParams.get("tournament_id");
		jdbcTemplate.execute("delete from tournament where tournament_id=" + tournament_id);
	}

	@GetMapping(value = "/exercise", params = {"operation=addPlayerIntoTournament"})
	public void addPlayerIntoTournament(@RequestParam Map<String,String> allParams) {
		Random random = new Random();
		String p_name = allParams.get("player_name");
		String tournament_id = allParams.get("tournament_id");

		jdbcTemplate.execute("insert into player(player_id, player_name, fk_id) " +
				"values(" + random.nextInt() + ", '" + p_name + "', " + tournament_id +  ")");
	}

	@GetMapping(value = "/exercise", params = {"operation=removePlayerFromTournament"})
	public void removePlayerFromTournament(@RequestParam Map<String,String> allParams) {
		String tournament_id = allParams.get("tournament_id");
		String player_id = allParams.get("player_id");

		jdbcTemplate.execute("delete from player where fk_id=" + tournament_id
				+ " and player_id=" + player_id);
	}

	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}

	// Objekt att exponera mot frontend.
	private class Exercise {
		public int tournament_id;
		public int reward_amount;
		public int player_id;
		public String player_name;
	}
}
