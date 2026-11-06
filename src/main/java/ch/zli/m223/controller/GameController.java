package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.GameDto;
import ch.zli.m223.service.GameService;

@Path("/games")
@Tag(name = "Games", description = "Verwaltung der Games")
@Produces(MediaType.APPLICATION_JSON)
public class GameController {

    @Inject
    GameService gameService;

    @GET
    @Operation(summary = "Alle Games abrufen")
    public List<GameDto> index() {
        return gameService.findAll().stream().map(Mapper::toDto).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Ein Game abrufen")
    public GameDto get(@PathParam("id") Long id) {
        return Mapper.toDto(gameService.findById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Game erstellen", description = "Unbekannte genreId oder platformIds ergeben 404.")
    public GameDto create(@NotNull @Valid GameDto dto) {
        return Mapper.toDto(gameService.create(Mapper.toEntity(dto), dto.genreId, dto.platformIds));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Game ändern")
    public GameDto update(@PathParam("id") Long id, @NotNull @Valid GameDto dto) {
        return Mapper.toDto(gameService.update(id, Mapper.toEntity(dto), dto.genreId, dto.platformIds));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Game löschen")
    public void delete(@PathParam("id") Long id) {
        gameService.delete(id);
    }
}
