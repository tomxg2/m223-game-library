package ch.zli.m223.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Game;
import ch.zli.m223.model.Genre;
import ch.zli.m223.model.Platform;
import ch.zli.m223.repository.GameRepository;
import ch.zli.m223.repository.GenreRepository;
import ch.zli.m223.repository.LibraryEntryRepository;
import ch.zli.m223.repository.PlatformRepository;

@ApplicationScoped
public class GameService {
    @Inject
    GameRepository gameRepository;

    @Inject
    GenreRepository genreRepository;

    @Inject
    PlatformRepository platformRepository;

    @Inject
    LibraryEntryRepository libraryEntryRepository;

    public List<Game> findAll() {
        return gameRepository.listAll();
    }

    public Game findById(Long id) {
        Game game = gameRepository.findById(id);
        if (game == null) {
            throw new NotFoundException("Game " + id + " nicht gefunden");
        }
        return game;
    }

    @Transactional
    public Game create(Game game, Long genreId, List<Long> platformIds) {
        game.setGenre(loadGenre(genreId));
        game.setPlatforms(loadPlatforms(platformIds));
        gameRepository.persist(game);
        return game;
    }

    @Transactional
    public Game update(Long id, Game game, Long genreId, List<Long> platformIds) {
        Game existing = findById(id);
        existing.setTitle(game.getTitle());
        existing.setReleaseYear(game.getReleaseYear());
        existing.setGenre(loadGenre(genreId));
        existing.setPlatforms(loadPlatforms(platformIds));
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        if (libraryEntryRepository.count("game.id", id) > 0) {
            throw new BadRequestException("Game wird noch in Bibliotheken verwendet");
        }
        gameRepository.deleteById(id);
    }

    private Genre loadGenre(Long genreId) {
        if (genreId == null) {
            return null;
        }
        Genre genre = genreRepository.findById(genreId);
        if (genre == null) {
            throw new NotFoundException("Genre " + genreId + " nicht gefunden");
        }
        return genre;
    }

    private Set<Platform> loadPlatforms(List<Long> platformIds) {
        Set<Platform> platforms = new LinkedHashSet<>();
        if (platformIds == null) {
            return platforms;
        }
        for (Long platformId : platformIds) {
            Platform platform = platformRepository.findById(platformId);
            if (platform == null) {
                throw new NotFoundException("Platform " + platformId + " nicht gefunden");
            }
            platforms.add(platform);
        }
        return platforms;
    }
}
