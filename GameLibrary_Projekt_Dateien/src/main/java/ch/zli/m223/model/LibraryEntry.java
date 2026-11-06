package ch.zli.m223.model;

import jakarta.persistence.*;

// Ein Game in der Bibliothek eines Users, auf einer bestimmten Plattform
@Entity
public class LibraryEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int playtimeHours;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LibraryStatus status;

  // 1 bis 10, darf leer sein (noch nicht bewertet)
  private Integer rating;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private ApplicationUser user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  @ManyToOne(optional = false)
  @JoinColumn(name = "platform_id", nullable = false)
  private Platform platform;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public int getPlaytimeHours() { return playtimeHours; }
  public void setPlaytimeHours(int playtimeHours) { this.playtimeHours = playtimeHours; }

  public LibraryStatus getStatus() { return status; }
  public void setStatus(LibraryStatus status) { this.status = status; }

  public Integer getRating() { return rating; }
  public void setRating(Integer rating) { this.rating = rating; }

  public ApplicationUser getUser() { return user; }
  public void setUser(ApplicationUser user) { this.user = user; }

  public Game getGame() { return game; }
  public void setGame(Game game) { this.game = game; }

  public Platform getPlatform() { return platform; }
  public void setPlatform(Platform platform) { this.platform = platform; }
}
