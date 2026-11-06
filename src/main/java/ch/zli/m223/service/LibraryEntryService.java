package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Game;
import ch.zli.m223.model.LibraryEntry;
import ch.zli.m223.model.Platform;
import ch.zli.m223.repository.ApplicationUserRepository;
import ch.zli.m223.repository.GameRepository;
import ch.zli.m223.repository.LibraryEntryRepository;
import ch.zli.m223.repository.PlatformRepository;

@ApplicationScoped
public class LibraryEntryService {
    @Inject
    LibraryEntryRepository libraryEntryRepository;

    @Inject
    ApplicationUserRepository userRepository;

    @Inject
    GameRepository gameRepository;

    @Inject
    PlatformRepository platformRepository;

    public List<LibraryEntry> findAll() {
        return libraryEntryRepository.listAll();
    }

    public List<LibraryEntry> findByUser(Long userId) {
        return libraryEntryRepository.list("user.id", userId);
    }

    public LibraryEntry findById(Long id) {
        LibraryEntry entry = libraryEntryRepository.findById(id);
        if (entry == null) {
            throw new NotFoundException("LibraryEntry " + id + " nicht gefunden");
        }
        return entry;
    }

    @Transactional
    public LibraryEntry create(LibraryEntry entry, Long userId, Long gameId, Long platformId) {
        setRelations(entry, userId, gameId, platformId);
        libraryEntryRepository.persist(entry);
        return entry;
    }

    @Transactional
    public LibraryEntry update(Long id, LibraryEntry entry, Long userId, Long gameId, Long platformId) {
        LibraryEntry existing = findById(id);
        existing.setPlaytimeHours(entry.getPlaytimeHours());
        existing.setStatus(entry.getStatus());
        existing.setRating(entry.getRating());
        setRelations(existing, userId, gameId, platformId);
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        if (!libraryEntryRepository.deleteById(id)) {
            throw new NotFoundException("LibraryEntry " + id + " nicht gefunden");
        }
    }

    // IDs aus dem DTO in Entities auflösen, unbekannte IDs -> 404
    private void setRelations(LibraryEntry entry, Long userId, Long gameId, Long platformId) {
        ApplicationUser user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("User " + userId + " nicht gefunden");
        }
        Game game = gameRepository.findById(gameId);
        if (game == null) {
            throw new NotFoundException("Game " + gameId + " nicht gefunden");
        }
        Platform platform = platformRepository.findById(platformId);
        if (platform == null) {
            throw new NotFoundException("Platform " + platformId + " nicht gefunden");
        }
        entry.setUser(user);
        entry.setGame(game);
        entry.setPlatform(platform);
    }
}
