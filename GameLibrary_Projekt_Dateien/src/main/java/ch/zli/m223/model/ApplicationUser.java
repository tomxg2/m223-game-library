package ch.zli.m223.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

// "User" ist in vielen Datenbanken ein reserviertes Wort, darum ApplicationUser
@Entity
public class ApplicationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  // Wird in Block 5 (Authentifizierung) gehasht
  @Column(nullable = false)
  private String password;

  // 1:n – ein User hat viele Einträge in seiner Bibliothek
  @OneToMany(mappedBy = "user")
  private Set<LibraryEntry> libraryEntries = new LinkedHashSet<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  public Set<LibraryEntry> getLibraryEntries() { return libraryEntries; }
  public void setLibraryEntries(Set<LibraryEntry> libraryEntries) { this.libraryEntries = libraryEntries; }
}
