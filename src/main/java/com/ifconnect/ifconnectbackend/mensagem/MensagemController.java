package com.ifconnect.ifconnectbackend.mensagem;

import com.ifconnect.ifconnectbackend.exception.ErrorDetails;
import com.ifconnect.ifconnectbackend.models.Mensagem;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static io.vavr.API.*;
import static io.vavr.Predicates.isIn;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/mensagens")
@RequiredArgsConstructor
@Tag(name = "Mensagem")
public class MensagemController {

    private final MensagemService service;

    @Operation(summary = "Mensagens by id.", description = "Return the mensagens by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    public ResponseEntity<Mensagem> getOne(@PathVariable(value = "id") Integer id) {
        return ok().body(service.findById(id));
    }

    @Operation(summary = "List of mensagens.", description = "List of mensagens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/get-all")
    public ResponseEntity<List<Mensagem>> getAll() {
        return ok().body(service.findAll());
    }

    @Operation(summary = "The list of mensagens Pageable.", description = "Returns the list of entities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/search")
    public ResponseEntity<Page<Mensagem>> search(@RequestParam("encontroId") Integer encontroId,
                                                 @RequestParam(value = "order", required = false, defaultValue = "data") String order,
                                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        String finalOrder = order.startsWith("-") ? order.substring(1) : order;
        SearchFilter searchFilter = Match(finalOrder.toLowerCase()).of(
                Case($(isIn("id", "data")), new SearchFilter("", order.toLowerCase(), page, size)),
                Case($(), () -> {
                    throw new IllegalArgumentException("The sent sorting field is invalid. Available fields: 'id' and 'data.");
                })
        );
        return ResponseEntity.ok(service.mensagemPageable(encontroId, searchFilter));
    }

    @Operation(summary = "Update mensagens ", description = "Return Update mensagens ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public ResponseEntity<?> updateMensagem(@PathVariable(value = "id") Integer id, @RequestBody @Valid Mensagem entity) {
        if (!Objects.equals(entity.getId(), id)) {
            return badRequest().body("Ops! Id of entity is not equals as param 'id'! :(");
        } else {
            service.saveOrUpdate(entity);
            return noContent().build();
        }
    }

    @Operation(summary = "Delete mensagens", description = "Return Delete mensagens by id (Long)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(service.findById(id));
        return noContent().build();
    }

}
