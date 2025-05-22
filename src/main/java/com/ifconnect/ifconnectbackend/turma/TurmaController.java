package com.ifconnect.ifconnectbackend.turma;

import com.ifconnect.ifconnectbackend.exception.ErrorDetails;
import com.ifconnect.ifconnectbackend.models.Turma;
import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static io.vavr.API.*;
import static io.vavr.Predicates.isIn;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas")
public class TurmaController {

    private final TurmaService service;

    @Operation(summary = " Save turmas", description = "Return save turmas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Turma> save(@RequestBody @Valid Turma entity) {
        return created(fromCurrentRequestUri().path(service.saveOrUpdate(entity).getId().toString()).build().toUri()).body(entity);
    }

    @Operation(summary = "Turmas by id.", description = "Return the turmas by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    public ResponseEntity<Turma> getOne(@PathVariable(value = "id") Integer id) {
        return ok().body(service.findById(id));
    }

    @Operation(summary = "List of turmas.", description = "List of turmas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/get-all")
    public ResponseEntity<List<Turma>> getAll() {
        return ok().body(service.findAll());
    }

    @Operation(summary = "List of turmas of an admin.", description = "List of turmas of an admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/get-by-admin/{id}")
    public ResponseEntity<List<Turma>> getByAdmin(@PathVariable(value = "id") Integer id) {
        return ok().body(service.findByAdmin(id));
    }

    @Operation(summary = "The list of turmas Pageable.", description = "Returns the list of entities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/search")
    public ResponseEntity<Page<Turma>> search(@RequestParam("searchTerm") String searchTerm,
                                              @RequestParam(value = "userId", required = false) Integer userId,
                                              @RequestParam(value = "cursoId", required = false) Integer cursoId,
                                              @RequestParam(value = "order", required = false, defaultValue = "nome") String order,
                                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        String finalOrder = order.startsWith("-") ? order.substring(1) : order;
        SearchFilter searchFilter = Match(finalOrder.toLowerCase()).of(
                Case($(isIn("id", "nome")), new SearchFilter(searchTerm, order.toLowerCase(), page, size)),
                Case($(), () -> {
                    throw new IllegalArgumentException("The sent sorting field is invalid. Available fields: 'id' and 'nome.");
                })
        );
        return ResponseEntity.ok(service.turmasPageable(userId, cursoId, searchFilter));
    }

    @Operation(summary = "Update turma", description = "Return Update turma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTurma(Authentication authentication,
                                         @PathVariable(value = "id") Integer id,
                                         @RequestBody @Valid Turma entity) {
        if (!Objects.equals(entity.getId(), id)) {
            return badRequest().body("Ops! Id of entity is not equals as param 'id'! :(");
        } else if (!validaUsuarioAdmin(authentication, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Ops! You do not have permission to update this group!");
        } else {
            service.saveOrUpdate(entity);
            return noContent().build();
        }
    }

    @Operation(summary = "Delete turmas", description = "Return Delete turmas by id (Long)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(Authentication authentication, @PathVariable("id") Integer id) {
        if (!validaUsuarioAdmin(authentication, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Ops! You do not have permission to delete this group!");
        } else {
            service.delete(service.findById(id));
            return noContent().build();
        }
    }

    boolean validaUsuarioAdmin(Authentication authentication, Integer idTurma) {
        // Recupera o usuario
        Integer usuarioId = ((Usuario)authentication.getPrincipal()).getId();
        // Recupera o turma existente do banco de dados
        Turma existingGroup = service.findById(idTurma);
        // Verifica se o turma existe
        if (existingGroup == null) {
            return false;
        }
        return existingGroup.getAdmin().getId().equals(usuarioId);
    }
}

