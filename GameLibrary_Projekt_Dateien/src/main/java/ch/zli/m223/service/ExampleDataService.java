package ch.zli.m223.service;

import java.util.List;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Game;
import ch.zli.m223.model.Genre;
import ch.zli.m223.model.LibraryEntry;
import ch.zli.m223.model.LibraryStatus;
import ch.zli.m223.model.Platform;
import ch.zli.m223.repository.ApplicationUserRepository;
import ch.zli.m223.repository.GameRepository;
import ch.zli.m223.repository.GenreRepository;
import ch.zli.m223.repository.LibraryEntryRepository;
import ch.zli.m223.repository.PlatformRepository;

// Aufgabe 3.5: Testdaten beim Start laden (nur im Dev-Profil)
@ApplicationScoped
public class ExampleDataService {

    @Inject GenreRepository genreRepository;
    @Inject PlatformRepository platformRepository;
    @Inject GameRepository gameRepository;
    @Inject ApplicationUserRepository userRepository;
    @Inject LibraryEntryRepository libraryEntryRepository;

    @ConfigProperty(name = "library.example-data.enabled", defaultValue = "false")
    boolean enabled;

    void onStart(@Observes StartupEvent event) {
        if (enabled) {
            createExampleData();
        }
    }

    @Transactional
    public void createExampleData() {
        if (genreRepository.count() > 0 || platformRepository.count() > 0 || gameRepository.count() > 0
                || userRepository.count() > 0 || libraryEntryRepository.count() > 0) {
            return;
        }

        Genre shooter = genre("Shooter");
        Genre rpg = genre("RPG");
        Genre sport = genre("Sport");

        Platform pc = platform("PC");
        Platform ps5 = platform("PlayStation 5");
        Platform sw = platform("Nintendo Switch");

        Game cs2 = game("Counter-Strike 2", 2023, shooter, Set.of(pc));
        Game eldenRing = game("Elden Ring", 2022, rpg, Set.of(pc, ps5));
        Game zelda = game("The Legend of Zelda: Tears of the Kingdom", 2023, rpg, Set.of(sw));
        Game fc = game("EA Sports FC 25", 2024, sport, Set.of(pc, ps5, sw));

        ApplicationUser tom = user("tom", "passwort123");
        ApplicationUser lena = user("lena", "geheim12345");

        entry(tom, cs2, pc, 420, LibraryStatus.PLAYING, 9);
        entry(tom, eldenRing, ps5, 95, LibraryStatus.COMPLETED, 10);
        entry(tom, fc, ps5, 30, LibraryStatus.DROPPED, 5);
        entry(lena, zelda, sw, 120, LibraryStatus.COMPLETED, 10);
        entry(lena, eldenRing, pc, 0, LibraryStatus.BACKLOG, null);
    }

    private Genre genre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.persist(genre);
        return genre;
    }

    private Platform platform(String name) {
        Platform platform = new Platform();
        platform.setName(name);
        platformRepository.persist(platform);
        return platform;
    }

    private Game game(String title, int year, Genre genre, Set<Platform> platforms) {
        Game game = new Game();
        game.setTitle(title);
        game.setReleaseYear(year);
        game.setGenre(genre);
        game.getPlatforms().addAll(platforms);
        gameRepository.persist(game);
        return game;
    }

    private ApplicationUser user(String username, String password) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.persist(user);
        return user;
    }

    private void entry(ApplicationUser user, Game game, Platform platform, int hours, LibraryStatus status, Integer rating) {
        LibraryEntry entry = new LibraryEntry();
        entry.setUser(user);
        entry.setGame(game);
        entry.setPlatform(platform);
        entry.setPlaytimeHours(hours);
        entry.setStatus(status);
        entry.setRating(rating);
        libraryEntryRepository.persist(entry);
    }
}
