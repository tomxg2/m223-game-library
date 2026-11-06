package ch.zli.m223.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(readOnly = true)
    public Long id;

    @NotBlank(message = "Benutzername ist ein Pflichtfeld")
    public String username;

    // Nur beim Erstellen/Ändern einlesen, nie zurückgeben
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(writeOnly = true)
    @NotBlank(message = "Passwort ist ein Pflichtfeld")
    @Size(min = 8, message = "Passwort muss mindestens 8 Zeichen haben")
    public String password;
}
