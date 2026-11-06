package ch.zli.m223.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  private Integer releaseYear;

  // n:1 – viele Games gehören zu einem Genre
  @ManyToOne
  @JoinColumn(name = "genre_id")
  private Genre genre;

  // n:m – ein Game ist auf vielen Plattformen erhältlich
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "game_platform",
      joinColumns = @JoinColumn(name = "game_id"),
      inverseJoinColumns = @JoinColumn(name = "platform_id"))
  private Set<Platform> platforms = new LinkedHashSet<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public Integer getReleaseYear() { return releaseYear; }
  public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

  public Genre getGenre() { return genre; }
  public void setGenre(Genre genre) { this.genre = genre; }

  public Set<Platform> getPlatforms() { return platforms; }
  public void setPlatforms(Set<Platform> platforms) { this.platforms = platforms; }
}
