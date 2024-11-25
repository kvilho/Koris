package vk.koris.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

import vk.koris.demo.domain.GameRepository;
import vk.koris.demo.domain.Player;
import vk.koris.demo.domain.PlayerRepository;
import vk.koris.demo.domain.Team;
import vk.koris.demo.domain.TeamRepository;

@Controller
public class TeamController {
    @Autowired
    private TeamRepository teamrepo;

    @Autowired
    private PlayerRepository playerrepo;

    @Autowired
    private GameRepository gamerepo;

    @GetMapping("/addteam")
     public String addTeam(Model model) {
         model.addAttribute("team", new Team());
         model.addAttribute("players", playerrepo.findAll());
         return "addteam";
     }

	@GetMapping("/teamlist")
	public String getTeam(Model model) {
        model.addAttribute("teams", teamrepo.findAll());
		return "teamlist";
	}

	@PostMapping("/saveteam")
     public String saveTeam(Team team) {

        
         teamrepo.save(team);
        
         return "redirect:/teamlist";
     }

     @RequestMapping(value = "/editteam/{id}")
     public String editTeam(@PathVariable("id") Long teamid, Model model){
         Team team = teamrepo.findById(teamid).orElse(null);
         model.addAttribute("team", team);
         return "editteam";
 }

    @RequestMapping("/deleteteam/{id}")
    public String deleteTeam(@PathVariable("id") Long teamid) {
        teamrepo.deleteById(teamid);
        return "redirect:/teamlist";
    }
}
