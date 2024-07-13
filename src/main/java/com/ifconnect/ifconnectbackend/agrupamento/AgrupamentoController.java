package com.ifconnect.ifconnectbackend.agrupamento;

import com.ifconnect.ifconnectbackend.exception.ErrorDetails;
import com.ifconnect.ifconnectbackend.models.Agrupamento;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.vavr.API.*;
import static io.vavr.Predicates.isIn;

@RestController
@RequestMapping("/api/v1/agrupamentos")
@RequiredArgsConstructor
@Tag(name = "Agrupamentos")
public class AgrupamentoController {

    public final AgrupamentoService service;

    @Operation(summary = "The list of grupos/pages Pageable.", description = "Returns the list of entities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Ok"),
            @ApiResponse(responseCode = "401", description = "Not authenticated agent (missing or invalid credentials)"),
            @ApiResponse(responseCode = "403", description = "Ops! You do not have permission to access this feature! :("),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))})})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("search")
    public ResponseEntity<Page<Agrupamento>> search(@RequestParam("searchTerm") String searchTerm,
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
        return ResponseEntity.ok(service.agrupamentosPageable(userId, cursoId, searchFilter));
    }
}
