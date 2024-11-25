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

import vk.koris.demo.domain.Game;
import vk.koris.demo.domain.GameRepository;

@CrossOrigin
@Controller
@RequestMapping("/api/games")
public class GameRestController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Game> getAllGames() {
        return (List<Game>) gameRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Game> getGameById(@PathVariable("id") Long gameId) {
        return gameRepository.findById(gameId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Game saveGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteGame(@PathVariable("id") Long gameId) {
        gameRepository.deleteById(gameId);
    }
}

