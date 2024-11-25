package vk.koris.demo.domain;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository <Player, Long>{
    Iterable<Player> findByTeamId(Long teamId);
}
