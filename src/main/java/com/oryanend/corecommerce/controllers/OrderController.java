package com.oryanend.corecommerce.controllers;

import com.oryanend.corecommerce.dto.OrderDTO;
import com.oryanend.corecommerce.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar pedido por ID",
            description = """
           Retorna os detalhes completos de um pedido específico.
           Requer um ID válido de produto cadastrado no sistema.
           """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping
    @Operation(summary = "Criar novo pedido",
            description = "Cria um novo pedido com os itens especificados")

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do pedido a ser criado (apenas os IDs dos produtos e quantidades são necessários)",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class),
                    examples = @ExampleObject(
                            name = "Exemplo Básico",
                            value = """
            {
                "items": [
                    {
                        "productId": 1,
                        "quantity": 2
                    },
                    {
                        "productId": 5,
                        "quantity": 1
                    }
                ]
            }
            """
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado com sucesso",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Apenas usuários admin podem executar essa ação",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Credenciais válidas mas sem permissão suficiente",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = service.insert(orderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrder.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdOrder);
    }
}
