package com.ifconnect.ifconnectbackend.curso;

import com.ifconnect.ifconnectbackend.exception.ErrorDetails;
import com.ifconnect.ifconnectbackend.models.Curso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService service;

    @Operation(summary = " Save cursos", description = "Return save cursos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Curso> save(@RequestBody @Valid Curso entity) {
        return created(fromCurrentRequestUri().path(service.saveOrUpdate(entity).getId().toString()).build().toUri()).body(entity);
    }

    @Operation(summary = "Cursos by id.", description = "Return the cursos by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    public ResponseEntity<Curso> getOne(@PathVariable(value = "id") Integer id) {
        return ok().body(service.findById(id));
    }
    @Operation(summary = "List of cursos.", description = "List of cursos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<Curso>> findAll() {
        return ok().body(service.findAll());
    }


    @Operation(summary = "Update cursos ", description = "Return Update cursos ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public ResponseEntity updateCurso(@PathVariable(value = "id") Long id, @RequestBody @Valid Curso entity) {
        if (!Objects.equals(entity.getId(), id)) {
            return badRequest().body("Ops! Id of entity is not equals as param 'id'! :(");
        } else {
            service.saveOrUpdate(entity);
            return noContent().build();
        }
    }

    @Operation(summary = "Delete cursos", description = "Return Delete cursos by id (Long)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        service.delete(service.findById(id));
        return noContent().build();
    }
}
