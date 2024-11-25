package vk.koris.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vk.koris.demo.domain.Player;
import vk.koris.demo.domain.Game;
import vk.koris.demo.domain.GameRepository;
import vk.koris.demo.domain.PlayerRepository;
import vk.koris.demo.domain.Team;
import vk.koris.demo.domain.TeamRepository;

@Controller
@RequestMapping("/gamelog")
public class GameController {
    @Autowired
    private GameRepository gamerepo;

    @Autowired 
    private PlayerRepository playerrepo;

    @Autowired
    private TeamRepository teamrepo;

    public List<Team> getAllTeams() {
        return (List<Team>) teamrepo.findAll();
    }

    @GetMapping("/{playerid}")
     public String getGameLog(@PathVariable("playerid") Long playerid, Model model) {
        Player player = playerrepo.findById(playerid).orElse(null);
        model.addAttribute("player", player);
        model.addAttribute("games", gamerepo.findByPlayerId(playerid));

        return "gamelog";
    }

    @GetMapping("/{playerid}/addgame")
     public String addGame(@PathVariable("playerid") Long playerid, Model model) {
        Player player = playerrepo.findById(playerid).orElse(null);
        List<Team> opponents = getAllTeams();
        model.addAttribute("player", player);
        model.addAttribute("game", new Game());
        model.addAttribute("opponents", opponents);
        return "addgame";
     }

	@PostMapping("/{playerid}/savegame")
     public String saveGame(@PathVariable("playerid") Long playerid, Game game, Model model) {
        Player player = playerrepo.findById(playerid).orElse(null);
        model.addAttribute("player", player);

        if (player != null) {
            game.setPlayer(player);
            gamerepo.save(game);
        }
        
        return "redirect:/gamelog/{playerid}";
     }

     @RequestMapping(value = "/{playerid}/editgame/{gameid}")
        public String editGame(@PathVariable("gameid") Long gameid, @PathVariable("playerid") Long playerid, Model model){
            Game game = gamerepo.findById(gameid).orElse(null);
            Player player = playerrepo.findById(playerid).orElse(null);
            model.addAttribute("game", game);
            model.addAttribute("player", player);
            return "editgame";
    }

    @RequestMapping("/{playerid}/deletegame/{gameid}")
    public String deletegame(@PathVariable("playerid") Long playerid, @PathVariable("gameid") Long gameid) {
        gamerepo.deleteById(gameid);
        return "redirect:/gamelog/{playerid}";
    }
}
