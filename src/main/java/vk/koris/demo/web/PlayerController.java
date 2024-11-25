package vk.koris.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vk.koris.demo.domain.Player;
import vk.koris.demo.domain.PlayerRepository;
import vk.koris.demo.domain.Team;
import vk.koris.demo.domain.TeamRepository;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerRepository playerrepo;

    @Autowired
    private TeamRepository teamrepo;


	@GetMapping("/{teamid}")
	public String getPlayer(@PathVariable("teamid") Long teamid, Model model) {
        Team team = teamrepo.findById(teamid).orElse(null);
        model.addAttribute("team", team);
        model.addAttribute("players", playerrepo.findByTeamId(teamid));
		return "playerlist";
	}
    @GetMapping("/{teamid}/add")
     public String addplayer(@PathVariable("teamid") Long teamid, Model model) {
        Team team = teamrepo.findById(teamid).orElse(null);
        model.addAttribute("team", team);
        model.addAttribute("player", new Player());
        return "addplayer";
     }

	@PostMapping("/{teamid}/save")
     public String savePlayer(@PathVariable("teamid") Long teamid, Player player, Model model) {
        Team team = teamrepo.findById(teamid).orElse(null);
        model.addAttribute("team", team);
        player.setTeam(team);
        playerrepo.save(player);
        
        return "redirect:/player/{teamid}";
     }

     @RequestMapping(value = "/{teamid}/editplayer/{playerid}")
        public String editPlayer(@PathVariable("teamid") Long teamid, @PathVariable("playerid") Long playerid, Model model){
            Team team = teamrepo.findById(teamid).orElse(null);
            Player player = playerrepo.findById(playerid).orElse(null);
            model.addAttribute("team", team);
            model.addAttribute("player", player);
            return "editplayer";
    }

    @RequestMapping("/{teamid}/deleteplayer/{playerid}")
    public String deleteplayer(@PathVariable("playerid") Long playerid, @PathVariable("teamid") Long teamid) {
        playerrepo.deleteById(playerid);
        return "redirect:/players/{teamid}";
    }
}
