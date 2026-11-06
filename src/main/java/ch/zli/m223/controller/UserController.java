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

import ch.zli.m223.dto.UserDto;
import ch.zli.m223.service.UserService;

@Path("/users")
@Tag(name = "Users", description = "Verwaltung der Users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Operation(summary = "Alle Users abrufen")
    public List<UserDto> index() {
        return userService.findAll().stream().map(Mapper::toDto).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Ein User abrufen")
    public UserDto get(@PathParam("id") Long id) {
        return Mapper.toDto(userService.findById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User erstellen")
    public UserDto create(@NotNull @Valid UserDto dto) {
        return Mapper.toDto(userService.create(Mapper.toEntity(dto)));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User ändern")
    public UserDto update(@PathParam("id") Long id, @NotNull @Valid UserDto dto) {
        return Mapper.toDto(userService.update(id, Mapper.toEntity(dto)));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "User löschen")
    public void delete(@PathParam("id") Long id) {
        userService.delete(id);
    }
}
