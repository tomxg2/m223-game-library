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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.LibraryEntryDto;
import ch.zli.m223.model.LibraryEntry;
import ch.zli.m223.service.LibraryEntryService;

@Path("/library-entries")
@Tag(name = "Library Entries", description = "Games in der Bibliothek eines Users")
@Produces(MediaType.APPLICATION_JSON)
public class LibraryEntryController {

    @Inject
    LibraryEntryService libraryEntryService;

    @GET
    @Operation(summary = "Bibliothekseinträge abrufen", description = "Optional mit ?userId=1 nur die Einträge eines Users.")
    public List<LibraryEntryDto> index(@QueryParam("userId") Long userId) {
        List<LibraryEntry> entries = userId == null
                ? libraryEntryService.findAll()
                : libraryEntryService.findByUser(userId);
        return entries.stream().map(Mapper::toDto).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Einen Bibliothekseintrag abrufen")
    public LibraryEntryDto get(@PathParam("id") Long id) {
        return Mapper.toDto(libraryEntryService.findById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Game zur Bibliothek hinzufügen", description = "Unbekannte userId, gameId oder platformId ergeben 404.")
    public LibraryEntryDto create(@NotNull @Valid LibraryEntryDto dto) {
        return Mapper.toDto(libraryEntryService.create(Mapper.toEntity(dto), dto.userId, dto.gameId, dto.platformId));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Bibliothekseintrag ändern")
    public LibraryEntryDto update(@PathParam("id") Long id, @NotNull @Valid LibraryEntryDto dto) {
        return Mapper.toDto(libraryEntryService.update(id, Mapper.toEntity(dto), dto.userId, dto.gameId, dto.platformId));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Bibliothekseintrag löschen")
    public void delete(@PathParam("id") Long id) {
        libraryEntryService.delete(id);
    }
}
