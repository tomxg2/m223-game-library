package ch.zli.m223.controller;

import ch.zli.m223.dto.GameDto;
import ch.zli.m223.dto.GenreDto;
import ch.zli.m223.dto.LibraryEntryDto;
import ch.zli.m223.dto.PlatformDto;
import ch.zli.m223.dto.UserDto;
import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Game;
import ch.zli.m223.model.Genre;
import ch.zli.m223.model.LibraryEntry;
import ch.zli.m223.model.Platform;

// Mapping DTO <-> Entity. Beziehungen (IDs) werden im Service aufgelöst.
public final class Mapper {

    private Mapper() {
    }

    public static Genre toEntity(GenreDto dto) {
        Genre genre = new Genre();
        genre.setName(dto.name);
        return genre;
    }

    public static GenreDto toDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.id = genre.getId();
        dto.name = genre.getName();
        return dto;
    }

    public static Platform toEntity(PlatformDto dto) {
        Platform platform = new Platform();
        platform.setName(dto.name);
        return platform;
    }

    public static PlatformDto toDto(Platform platform) {
        PlatformDto dto = new PlatformDto();
        dto.id = platform.getId();
        dto.name = platform.getName();
        return dto;
    }

    public static Game toEntity(GameDto dto) {
        Game game = new Game();
        game.setTitle(dto.title);
        game.setReleaseYear(dto.releaseYear);
        return game;
    }

    public static GameDto toDto(Game game) {
        GameDto dto = new GameDto();
        dto.id = game.getId();
        dto.title = game.getTitle();
        dto.releaseYear = game.getReleaseYear();
        dto.genreId = game.getGenre() == null ? null : game.getGenre().getId();
        dto.platformIds = game.getPlatforms().stream().map(Platform::getId).toList();
        return dto;
    }

    public static ApplicationUser toEntity(UserDto dto) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(dto.username);
        user.setPassword(dto.password);
        return user;
    }

    public static UserDto toDto(ApplicationUser user) {
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        return dto;
    }

    public static LibraryEntry toEntity(LibraryEntryDto dto) {
        LibraryEntry entry = new LibraryEntry();
        entry.setPlaytimeHours(dto.playtimeHours);
        entry.setStatus(dto.status);
        entry.setRating(dto.rating);
        return entry;
    }

    public static LibraryEntryDto toDto(LibraryEntry entry) {
        LibraryEntryDto dto = new LibraryEntryDto();
        dto.id = entry.getId();
        dto.userId = entry.getUser().getId();
        dto.gameId = entry.getGame().getId();
        dto.platformId = entry.getPlatform().getId();
        dto.playtimeHours = entry.getPlaytimeHours();
        dto.status = entry.getStatus();
        dto.rating = entry.getRating();
        return dto;
    }
}
