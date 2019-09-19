package gr.manolis.stelios.footie.api.controllers;


import gr.manolis.stelios.footie.api.RestResponse;
import gr.manolis.stelios.footie.api.mappers.PlayerMapper;
import gr.manolis.stelios.footie.api.services.ViewsService;
import gr.manolis.stelios.footie.core.peristence.dtos.Player;
import gr.manolis.stelios.footie.core.peristence.dtos.Team;
import gr.manolis.stelios.footie.core.peristence.dtos.games.Result;
import gr.manolis.stelios.footie.core.services.PlayerService;
import gr.manolis.stelios.footie.core.services.ServiceUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/rest/players")
public class RestPlayerController {

    final static Logger logger = Logger.getLogger(RestPlayerController.class);

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerMapper playerMapper;

    @GetMapping("/")
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();

        return players;
    }

    @ResponseBody
    @PostMapping("/")
    public RestResponse addPlayer(@RequestParam(name = "player_name", required = false) String playerName,
                                  @RequestParam(name = "team_id", required = false) int teamId ) {

        Team team = serviceUtils.loadTeam(teamId);
        Player player = new Player(playerName, team);
        playerService.addPlayer(player);

        return new RestResponse(RestResponse.SUCCESS, "player added ");
    }

}
