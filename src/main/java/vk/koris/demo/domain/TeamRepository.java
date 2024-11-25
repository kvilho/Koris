package vk.koris.demo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository <Team, Long>{
    List<Team> findByName(String name);
}
