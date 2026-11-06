package ch.zli.m223.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  // 1:n – ein Genre hat viele Games (FK genre_id liegt in game)
  @OneToMany(mappedBy = "genre")
  private Set<Game> games = new LinkedHashSet<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public Set<Game> getGames() { return games; }
  public void setGames(Set<Game> games) { this.games = games; }
}
