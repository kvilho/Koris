package vk.koris.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vk.koris.demo.domain.AppUser;
import vk.koris.demo.domain.Player;
import vk.koris.demo.domain.PlayerRepository;
import vk.koris.demo.domain.Team;
import vk.koris.demo.domain.TeamRepository;
import vk.koris.demo.domain.AppUserRepository;

@Controller
@RequestMapping("/player")
public class PlayerController {
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

    @GetMapping("/{teamid}")
    public String getPlayer(@PathVariable("teamid") Long teamid, Model model) {
        Team team = teamrepo.findById(teamid).orElse(null);
        AppUser currentUser = getCurrentUser();
        model.addAttribute("team", team);
        model.addAttribute("players", playerrepo.findByTeamId(teamid));
        model.addAttribute("username", currentUser.getUsername());
        return "playerlist";
    }

    @GetMapping("/{teamid}/add")
    public String addPlayer(@PathVariable("teamid") Long teamid, Model model) {
        Team team = teamrepo.findById(teamid).orElse(null);
        AppUser currentUser = getCurrentUser();

        if (team == null || currentUser == null || 
            (!team.getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole()))) {
            throw new AccessDeniedException("You are not authorized to add players to this team.");
        }

        model.addAttribute("team", team);
        model.addAttribute("player", new Player());
        return "addplayer";
    }

    @PostMapping("/{teamid}/save")
    public String savePlayer(@PathVariable("teamid") Long teamid, Player player, Model model) {
        Team team = teamrepo.findById(teamid).orElse(null);
        AppUser currentUser = getCurrentUser();

        if (team == null || currentUser == null || 
            (!team.getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole()))) {
            throw new AccessDeniedException("You are not authorized to add players to this team.");
        }

        player.setTeam(team);
        playerrepo.save(player);
        return "redirect:/player/{teamid}";
    }

    @RequestMapping("/{teamid}/editplayer/{playerid}")
    public String editPlayer(@PathVariable("teamid") Long teamid, @PathVariable("playerid") Long playerid, Model model) {
        Team team = teamrepo.findById(teamid).orElse(null);
        Player player = playerrepo.findById(playerid).orElse(null);
        AppUser currentUser = getCurrentUser();

        if (team == null || player == null || currentUser == null || 
            (!team.getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole()))) {
            throw new AccessDeniedException("You are not authorized to edit players in this team.");
        }

        model.addAttribute("team", team);
        model.addAttribute("player", player);
        return "editplayer";
    }

    @RequestMapping("/{teamid}/deleteplayer/{playerid}")
    public String deletePlayer(@PathVariable("playerid") Long playerid, @PathVariable("teamid") Long teamid) {
        Team team = teamrepo.findById(teamid).orElse(null);
        AppUser currentUser = getCurrentUser();

        if (team == null || currentUser == null || 
            (!team.getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole()))) {
            throw new AccessDeniedException("You are not authorized to delete players from this team.");
        }

        playerrepo.deleteById(playerid);
        return "redirect:/player/{teamid}";
    }
}