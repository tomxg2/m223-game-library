package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Genre;
import ch.zli.m223.repository.GameRepository;
import ch.zli.m223.repository.GenreRepository;

@ApplicationScoped
public class GenreService {
    @Inject
    GenreRepository genreRepository;

    @Inject
    GameRepository gameRepository;

    public List<Genre> findAll() {
        return genreRepository.listAll();
    }

    public Genre findById(Long id) {
        Genre genre = genreRepository.findById(id);
        if (genre == null) {
            throw new NotFoundException("Genre " + id + " nicht gefunden");
        }
        return genre;
    }

    @Transactional
    public Genre create(Genre genre) {
        genreRepository.persist(genre);
        return genre;
    }

    @Transactional
    public Genre update(Long id, Genre genre) {
        Genre existing = findById(id);
        existing.setName(genre.getName());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        // Games behalten, aber ohne Genre
        gameRepository.update("genre = null where genre.id = ?1", id);
        genreRepository.deleteById(id);
    }
}
