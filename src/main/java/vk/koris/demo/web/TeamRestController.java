package vk.koris.demo.web;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vk.koris.demo.domain.Team;
import vk.koris.demo.domain.TeamRepository;

@CrossOrigin
@Controller
@RequestMapping("/api/teams")
public class TeamRestController {

    @Autowired
    private TeamRepository teamRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Team> getAllTeams() {
        return (List<Team>) teamRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Team> getTeamById(@PathVariable("id") Long teamId) {
        return teamRepository.findById(teamId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Team saveTeam(@RequestBody Team team) {
        return teamRepository.save(team);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTeam(@PathVariable("id") Long teamId) {
        teamRepository.deleteById(teamId);
    }
}

