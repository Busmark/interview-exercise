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
		System.out.println("addTournament");

		String reward_amount = allParams.get("reward_amount");

		Random random = new Random();
		int id = random.nextInt();
		int tournamentId = random.nextInt();
		jdbcTemplate.execute("insert into exercise(id, tournament_id, reward_amount) " +
				"values(" + id + ", " + tournamentId + ", " + reward_amount + ")");
	}

	// [{"tournament_id":-1924526871,"reward_amount":0,"player_id":0,"player_name":null}]
	@GetMapping(value = "/exercise", params = {"operation=getTournaments"})
	public List<Exercise> getTournaments(@RequestParam Map<String,String> allParams) {
		System.out.println("getTournaments");

		List<Exercise> list = new ArrayList<>();

			list = jdbcTemplate.query("select * from exercise where player_id is null", new RowMapper<Exercise>() {
				@Override
				public Exercise mapRow(ResultSet resultSet, int i) throws SQLException {
					Exercise e = new Exercise();
					e.tournament_id = resultSet.getInt("tournament_id");
					e.reward_amount = resultSet.getInt("reward_amount");
					return e;
				}
			});

		return list;
	}

	@GetMapping(value = "/exercise", params = {"operation=getPlayersInTournament"})
	public List<Exercise> getPlayersInTournament(@RequestParam Map<String,String> allParams) {
		System.out.println("getPlayersInTournament");

		List<Exercise> list = new ArrayList<>();
		String tournament_id = allParams.get("tournament_id");

		list = jdbcTemplate.query("select * from exercise where tournament_id=" + tournament_id +
				" and player_id is not null", new RowMapper<Exercise>() {
			@Override
			public Exercise mapRow(ResultSet resultSet, int i) throws SQLException {
				Exercise e = new Exercise();
				e.tournament_id = resultSet.getInt("tournament_id");
				e.player_id = resultSet.getInt("player_id");
				e.player_name = resultSet.getString("player_name");
				return e;
			}
		});

		return list;
	}

	@GetMapping(value = "/exercise", params = {"operation=updateTournament"})
	public void updateTournament(@RequestParam Map<String,String> allParams) {
		System.out.println("updateTournament");

		String reward_amount = allParams.get("reward_amount");
		String tournament_id = allParams.get("tournament_id");
		jdbcTemplate.execute("update exercise set reward_amount=" + reward_amount +
					" where tournament_id=" + tournament_id);
	}

	@GetMapping(value = "/exercise", params = {"operation=removeTournament"})
	public void removeTournament(@RequestParam Map<String,String> allParams) {
		System.out.println("removeTournament");

		String tournament_id = allParams.get("tournament_id");
		jdbcTemplate.execute("delete from exercise where tournament_id=" + tournament_id);
	}

	@GetMapping(value = "/exercise", params = {"operation=addPlayerIntoTournament"})
	public void addPlayerIntoTournament(@RequestParam Map<String,String> allParams) {
		System.out.println("addPlayerIntoTournament");

		String tournament_id = allParams.get("tournament_id");
		String player_name = allParams.get("player_name");

		Random random = new Random();
		int id = random.nextInt();
		int playerId = random.nextInt();
		jdbcTemplate.execute("insert into exercise(id, tournament_id, player_id, player_name) " +
				"values(" + id + ", " + tournament_id + ", " + playerId + ", '" + player_name + "')");
	}

	@GetMapping(value = "/exercise", params = {"operation=removePlayerFromTournament"})
	public void removePlayerFromTournament(@RequestParam Map<String,String> allParams) {
		System.out.println("removePlayerFromTournament");

		String tournament_id = allParams.get("tournament_id");
		String player_id = allParams.get("player_id");

		jdbcTemplate.execute("delete from exercise where tournament_id=" + tournament_id
				+ " and player_id=" + player_id);
	}



	//todo Skapa en defaultmetod som tar emot "busigt" anrop. Den ska dock inte göra något. Bara se till att Spring Boot inte smäller.

	/**
	 * Default method to catch
	 * @param operation
	 * @param tournament_id
	 * @param reward_amount
	 * @param player_id
	 * @param player_name
	 * @return
	 */
	@GetMapping("/exercise")
	public List<Exercise> exercise(@RequestParam String operation,
								   @RequestParam(required = false) Integer tournament_id,
								   @RequestParam(required = false) Integer reward_amount,
								   @RequestParam(required = false) Integer player_id,
								   @RequestParam(required = false) String player_name) {
		System.out.println(operation +", "+ tournament_id +", "+ reward_amount +", "+ player_id +", "+ player_name);

		List<Exercise> list = new ArrayList<>();
		return list;
	}

	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}

	private class Exercise {
		public int tournament_id;
		public int reward_amount;
		public int player_id;
		public String player_name;
	}
}
