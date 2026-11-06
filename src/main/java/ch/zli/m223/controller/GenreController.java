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

import ch.zli.m223.dto.GenreDto;
import ch.zli.m223.service.GenreService;

@Path("/genres")
@Tag(name = "Genres", description = "Verwaltung der Genres")
@Produces(MediaType.APPLICATION_JSON)
public class GenreController {

    @Inject
    GenreService genreService;

    @GET
    @Operation(summary = "Alle Genres abrufen")
    public List<GenreDto> index() {
        return genreService.findAll().stream().map(Mapper::toDto).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Ein Genre abrufen")
    public GenreDto get(@PathParam("id") Long id) {
        return Mapper.toDto(genreService.findById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Genre erstellen")
    public GenreDto create(@NotNull @Valid GenreDto dto) {
        return Mapper.toDto(genreService.create(Mapper.toEntity(dto)));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Genre ändern")
    public GenreDto update(@PathParam("id") Long id, @NotNull @Valid GenreDto dto) {
        return Mapper.toDto(genreService.update(id, Mapper.toEntity(dto)));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Genre löschen")
    public void delete(@PathParam("id") Long id) {
        genreService.delete(id);
    }
}
