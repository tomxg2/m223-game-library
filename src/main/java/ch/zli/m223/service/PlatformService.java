package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Game;
import ch.zli.m223.model.Platform;
import ch.zli.m223.repository.GameRepository;
import ch.zli.m223.repository.LibraryEntryRepository;
import ch.zli.m223.repository.PlatformRepository;

@ApplicationScoped
public class PlatformService {
    @Inject
    PlatformRepository platformRepository;

    @Inject
    GameRepository gameRepository;

    @Inject
    LibraryEntryRepository libraryEntryRepository;

    public List<Platform> findAll() {
        return platformRepository.listAll();
    }

    public Platform findById(Long id) {
        Platform platform = platformRepository.findById(id);
        if (platform == null) {
            throw new NotFoundException("Platform " + id + " nicht gefunden");
        }
        return platform;
    }

    @Transactional
    public Platform create(Platform platform) {
        platformRepository.persist(platform);
        return platform;
    }

    @Transactional
    public Platform update(Long id, Platform platform) {
        Platform existing = findById(id);
        existing.setName(platform.getName());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        Platform platform = findById(id);
        if (libraryEntryRepository.count("platform.id", id) > 0) {
            throw new BadRequestException("Platform wird noch in Bibliotheken verwendet");
        }
        // Game verwaltet die Join-Tabelle: Plattform zuerst aus allen Games entfernen
        List<Game> games = gameRepository
                .find("select g from Game g join g.platforms p where p.id = ?1", id)
                .list();
        for (Game game : games) {
            game.getPlatforms().remove(platform);
        }
        platformRepository.delete(platform);
    }
}
