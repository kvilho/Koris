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

import vk.koris.demo.domain.Player;
import vk.koris.demo.domain.PlayerRepository;

@CrossOrigin
@Controller
@RequestMapping("/api/players")
public class PlayerRestController {

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Player> getAllPlayers() {
        return (List<Player>) playerRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Player> getPlayerById(@PathVariable("id") Long playerId) {
        return playerRepository.findById(playerId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Player savePlayer(@RequestBody Player player) {
        return playerRepository.save(player);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePlayer(@PathVariable("id") Long playerId) {
        playerRepository.deleteById(playerId);
    }
}

