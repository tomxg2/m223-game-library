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

import ch.zli.m223.dto.PlatformDto;
import ch.zli.m223.service.PlatformService;

@Path("/platforms")
@Tag(name = "Platforms", description = "Verwaltung der Platforms")
@Produces(MediaType.APPLICATION_JSON)
public class PlatformController {

    @Inject
    PlatformService platformService;

    @GET
    @Operation(summary = "Alle Platforms abrufen")
    public List<PlatformDto> index() {
        return platformService.findAll().stream().map(Mapper::toDto).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Ein Platform abrufen")
    public PlatformDto get(@PathParam("id") Long id) {
        return Mapper.toDto(platformService.findById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Platform erstellen")
    public PlatformDto create(@NotNull @Valid PlatformDto dto) {
        return Mapper.toDto(platformService.create(Mapper.toEntity(dto)));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Platform ändern")
    public PlatformDto update(@PathParam("id") Long id, @NotNull @Valid PlatformDto dto) {
        return Mapper.toDto(platformService.update(id, Mapper.toEntity(dto)));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Platform löschen")
    public void delete(@PathParam("id") Long id) {
        platformService.delete(id);
    }
}
