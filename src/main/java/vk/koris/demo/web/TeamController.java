package vk.koris.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vk.koris.demo.domain.AppUser;
import vk.koris.demo.domain.GameRepository;
import vk.koris.demo.domain.PlayerRepository;
import vk.koris.demo.domain.Team;
import vk.koris.demo.domain.TeamRepository;
import vk.koris.demo.domain.AppUserRepository;

@Controller
public class TeamController {
    
    @Autowired
    private TeamRepository teamrepo;

    @Autowired
    private PlayerRepository playerrepo;

    @Autowired
    private GameRepository gamerepo;
    
    @Autowired
    private AppUserRepository userRepo;

    private AppUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return userRepo.findByUsername(((UserDetails) principal).getUsername());
        }
        throw new AccessDeniedException("User not authenticated");
    }

    @GetMapping("/addteam")
    public String addTeam(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("players", playerrepo.findAll());
        return "addteam";
    }

    @GetMapping("/teamlist")
    public String getTeam(Model model) {
        AppUser currentUser = getCurrentUser();
        model.addAttribute("teams", teamrepo.findAll());
        model.addAttribute("username", currentUser.getUsername());
        return "teamlist";
    }

    @PostMapping("/saveteam")
    public String saveTeam(Team team) {
        AppUser currentUser = getCurrentUser();
        team.setOwner(currentUser);
        teamrepo.save(team);
        return "redirect:/teamlist";
    }

    @RequestMapping("/editteam/{id}")
    public String editTeam(@PathVariable("id") Long teamId, Model model) {
        Team team = teamrepo.findById(teamId).orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));
        AppUser currentUser = getCurrentUser();

        if (!team.getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole())) {
            throw new AccessDeniedException("You are not authorized to edit this team.");
        }

        model.addAttribute("team", team);
        return "editteam";
    }

    @RequestMapping("/deleteteam/{id}")
    public String deleteTeam(@PathVariable("id") Long teamId) {
        Team team = teamrepo.findById(teamId).orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));
        AppUser currentUser = getCurrentUser();

        if (!team.getOwner().getId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRole())) {
            throw new AccessDeniedException("You are not authorized to delete this team.");
        }

        teamrepo.deleteById(teamId);
        return "redirect:/teamlist";
    }
}