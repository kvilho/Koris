package vk.koris.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vk.koris.demo.domain.AppUser;
import vk.koris.demo.domain.Game;
import vk.koris.demo.domain.GameRepository;
import vk.koris.demo.domain.Player;
import vk.koris.demo.domain.PlayerRepository;
import vk.koris.demo.domain.Team;
import vk.koris.demo.domain.TeamRepository;
import vk.koris.demo.domain.AppUserRepository;

@Controller
@RequestMapping("/gamelog")
public class GameController {
    @Autowired
    private GameRepository gamerepo;

    @Autowired 
    private PlayerRepository playerrepo;

    @Autowired
    private TeamRepository teamrepo;

    @Autowired
    private AppUserRepository userRepo;

    private AppUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return userRepo.findByUsername(((UserDetails) principal).getUsername());
        }
        throw new AccessDeniedException("User not authenticated");
    }

    public List<Team> getAllTeams() {
        return (List<Team>) teamrepo.findAll();
    }

    @GetMapping("/{playerid}")
    public String getGameLog(@PathVariable("playerid") Long playerid, Model model) {
        Player player = playerrepo.findById(playerid).orElse(null);
        AppUser currentUser = getCurrentUser();
        model.addAttribute("player", player);
        model.addAttribute("games", gamerepo.findByPlayerId(playerid));
        model.addAttribute("username", currentUser.getUsername());
        return "gamelog";
    }

    @GetMapping("/{playerid}/addgame")
    public String addGame(@PathVariable("playerid") Long playerid, Model model) {
        Player player = playerrepo.findById(playerid).orElse(null);
        AppUser currentUser = getCurrentUser();

        if (player == null || currentUser == null || 
            (!player.getTeam().getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole()))) {
            throw new AccessDeniedException("You are not authorized to add games for this player.");
        }

        List<Team> opponents = getAllTeams();
        model.addAttribute("player", player);
        model.addAttribute("game", new Game());
        model.addAttribute("opponents", opponents);
        return "addgame";
    }

    @PostMapping("/{playerid}/savegame")
    public String saveGame(@PathVariable("playerid") Long playerid, Game game, Model model) {
        Player player = playerrepo.findById(playerid).orElse(null);
        AppUser currentUser = getCurrentUser();

        if (player == null || currentUser == null || 
            (!player.getTeam().getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole()))) {
            throw new AccessDeniedException("You are not authorized to add games for this player.");
        }

        game.setPlayer(player);
        gamerepo.save(game);
        return "redirect:/gamelog/{playerid}";
    }

    @RequestMapping(value = "/{playerid}/editgame/{gameid}")
    public String editGame(@PathVariable("gameid") Long gameid, @PathVariable("playerid") Long playerid, Model model) {
        Game game = gamerepo.findById(gameid).orElse(null);
        Player player = playerrepo.findById(playerid).orElse(null);
        AppUser currentUser = getCurrentUser();

        if (game == null || player == null || currentUser == null || 
            (!player.getTeam().getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole()))) {
            throw new AccessDeniedException("You are not authorized to edit games for this player.");
        }

        model.addAttribute("game", game);
        model.addAttribute("player", player);
        return "editgame";
    }

    @RequestMapping("/{playerid}/deletegame/{gameid}")
    public String deleteGame(@PathVariable("playerid") Long playerid, @PathVariable("gameid") Long gameid) {
        Player player = playerrepo.findById(playerid).orElse(null);
        AppUser currentUser = getCurrentUser();

        if (player == null || currentUser == null || 
            (!player.getTeam().getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole()))) {
            throw new AccessDeniedException("You are not authorized to delete games for this player.");
        }

        gamerepo.deleteById(gameid);
        return "redirect:/gamelog/{playerid}";
    }
}