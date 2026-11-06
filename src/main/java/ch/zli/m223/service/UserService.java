package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.repository.ApplicationUserRepository;
import ch.zli.m223.repository.LibraryEntryRepository;

@ApplicationScoped
public class UserService {
    @Inject
    ApplicationUserRepository userRepository;

    @Inject
    LibraryEntryRepository libraryEntryRepository;

    public List<ApplicationUser> findAll() {
        return userRepository.listAll();
    }

    public ApplicationUser findById(Long id) {
        ApplicationUser user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("User " + id + " nicht gefunden");
        }
        return user;
    }

    @Transactional
    public ApplicationUser create(ApplicationUser user) {
        if (userRepository.count("username", user.getUsername()) > 0) {
            throw new BadRequestException("Benutzername ist bereits vergeben");
        }
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public ApplicationUser update(Long id, ApplicationUser user) {
        ApplicationUser existing = findById(id);
        existing.setUsername(user.getUsername());
        existing.setPassword(user.getPassword());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        // Mit dem User wird auch seine Bibliothek gelöscht
        libraryEntryRepository.delete("user.id", id);
        userRepository.deleteById(id);
    }
}
