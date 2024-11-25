package vk.koris.demo.domain;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository <Game, Long>{

    Iterable<Game> findByPlayerId(Long playerId);
}
