package ch.zli.m223.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.zli.m223.model.LibraryStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LibraryEntryDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(readOnly = true)
    public Long id;

    @NotNull(message = "userId ist ein Pflichtfeld")
    public Long userId;

    @NotNull(message = "gameId ist ein Pflichtfeld")
    public Long gameId;

    @NotNull(message = "platformId ist ein Pflichtfeld")
    public Long platformId;

    @Min(value = 0, message = "Spielzeit darf nicht negativ sein")
    public int playtimeHours;

    @NotNull(message = "Status ist ein Pflichtfeld")
    public LibraryStatus status;

    @Min(value = 1, message = "Bewertung muss zwischen 1 und 10 liegen")
    @Max(value = 10, message = "Bewertung muss zwischen 1 und 10 liegen")
    public Integer rating;
}
