package vk.koris.demo;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import vk.koris.demo.domain.AppUser;
import vk.koris.demo.domain.AppUserRepository;
import vk.koris.demo.domain.Team;
import vk.koris.demo.domain.TeamRepository;
import vk.koris.demo.domain.Player;
import vk.koris.demo.domain.PlayerRepository;
import vk.koris.demo.domain.Position;
import vk.koris.demo.domain.Game;
import vk.koris.demo.domain.GameRepository;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AppUserRepository repository, TeamRepository teamRepository,
                                  PlayerRepository playerRepository, GameRepository gameRepository) {
        return (args) -> {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

            // Create admin user if not exists
            if (repository.findByUsername("admin") == null) {
                String hashPwd = bc.encode("adminpassword");

                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPasswordHash(hashPwd);
                admin.setEmail("admin@example.com");
                admin.setRole("ADMIN");

                repository.save(admin);

                Team lakers = new Team();
                lakers.setName("Los Angeles Lakers");
                lakers.setOwner(admin);
                teamRepository.save(lakers);

                Player lebron = new Player();
                lebron.setFname("LeBron");
                lebron.setLname("James");
                lebron.setDob(LocalDate.of(1984, 12, 30));
                lebron.setHeight(206);
                lebron.setWeight(113);
                lebron.setPosition(Position.SMALL_FORWARD);
                lebron.setTeam(lakers);
                playerRepository.save(lebron);

                Game lebronGame = new Game();
                lebronGame.setPlayer(lebron);
                lebronGame.setDate(LocalDate.of(2024, 12, 15));
                lebronGame.setOpponent("Golden State Warriors");
				lebronGame.setMins(34);
                lebronGame.setPoints(28);
                lebronGame.setRebounds(8);
                lebronGame.setAssists(10);
                lebronGame.setBlocks(2);
                lebronGame.setSteals(1);
                lebronGame.setFgm(10);
                lebronGame.setFga(18);
                gameRepository.save(lebronGame);
            }

            // Create regular user if not exists
            if (repository.findByUsername("user") == null) {
                String userHashPwd = bc.encode("userpassword");

                AppUser user = new AppUser();
                user.setUsername("user");
                user.setPasswordHash(userHashPwd);
                user.setEmail("user@example.com");
                user.setRole("USER");

                repository.save(user);

                Team warriors = new Team();
                warriors.setName("Golden State Warriors");
                warriors.setOwner(user);
                teamRepository.save(warriors);

                Player curry = new Player();
                curry.setFname("Stephen");
                curry.setLname("Curry");
                curry.setDob(LocalDate.of(1988, 3, 14));
                curry.setHeight(191);
                curry.setWeight(84);
                curry.setPosition(Position.POINT_GUARD);
                curry.setTeam(warriors);
                playerRepository.save(curry);

                Game curryGame = new Game();
                curryGame.setPlayer(curry);
                curryGame.setDate(LocalDate.of(2024, 11, 20)); // Random date
                curryGame.setOpponent("Los Angeles Lakers");
				curryGame.setMins(33);
                curryGame.setPoints(35);
                curryGame.setRebounds(5);
                curryGame.setAssists(7);
                curryGame.setBlocks(0);
                curryGame.setSteals(3);
                curryGame.setFgm(12);
                curryGame.setFga(20);
                gameRepository.save(curryGame);
            }
        };
    }
}