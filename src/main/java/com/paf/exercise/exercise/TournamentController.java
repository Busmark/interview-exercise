package com.paf.exercise.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    /**
     * Equivalent to @GetMapping("/exercise/tournaments")
     */
    @GetMapping(value = "/exercise", params = {"operation=getTournaments"})
    public List<TournamentService.Exercise> getTournaments(@RequestParam Map<String,String> allParams) {
        return tournamentService.getTournaments();
    }

    /**
     * Equivalent to @PostMapping("/exercise/tournaments")
     */
    @GetMapping(value = "/exercise", params = {"operation=addTournament"})
    public void addTournament(@RequestParam Map<String,String> allParams) {
		tournamentService.addTournament(allParams);
    }

    /**
     * Equivalent to @GetMapping("/exercise/tournaments/{tournament_id}/players")
     */
    @GetMapping(value = "/exercise", params = {"operation=getPlayersInTournament"})
    public List<TournamentService.Exercise> getPlayersInTournament(@RequestParam Map<String,String> allParams) {
        return tournamentService.getPlayersInTournament(allParams);

    }

    /**
     * Equivalent to @PutMapping("/exercise/tournaments/{tournament_id}")
     */
    @GetMapping(value = "/exercise", params = {"operation=updateTournament"})
    public void updateTournament(@RequestParam Map<String,String> allParams) {
		tournamentService.updateTournament(allParams);
    }

    /**
     * Equivalent to @DeleteMapping("/exercise/tournaments/{tournament_id}")
     */
    @GetMapping(value = "/exercise", params = {"operation=removeTournament"})
    public void removeTournament(@RequestParam Map<String,String> allParams) {
		tournamentService.removeTournament(allParams);

    }

    /**
     * Equivalent to @PostMapping("/exercise/tournaments/{tournament_id}/players")
     */
    @GetMapping(value = "/exercise", params = {"operation=addPlayerIntoTournament"})
    public void addPlayerIntoTournament(@RequestParam Map<String,String> allParams) {
		tournamentService.addPlayerIntoTournament(allParams);
    }

    /**
     * Equivalent to @DeleteMapping("/exercise/tournaments/{tournament_id}/players/{player_id}")
     */
    @GetMapping(value = "/exercise", params = {"operation=removePlayerFromTournament"})
    public void removePlayerFromTournament(@RequestParam Map<String,String> allParams) {
		tournamentService.removePlayerFromTournament(allParams);
    }
}
