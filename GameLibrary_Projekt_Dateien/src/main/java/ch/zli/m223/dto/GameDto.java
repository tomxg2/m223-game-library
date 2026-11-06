package ch.zli.m223.dto;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class GameDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(readOnly = true)
    public Long id;

    @NotBlank(message = "Titel ist ein Pflichtfeld")
    public String title;

    @Min(value = 1970, message = "Erscheinungsjahr muss ab 1970 sein")
    @Max(value = 2100, message = "Erscheinungsjahr ist ungültig")
    public Integer releaseYear;

    // ID des Genres, darf null sein
    public Long genreId;

    // IDs der Plattformen
    public List<Long> platformIds = new ArrayList<>();
}
