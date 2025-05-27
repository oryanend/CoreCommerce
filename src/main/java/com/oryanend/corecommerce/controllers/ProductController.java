package com.oryanend.corecommerce.controllers;

import com.oryanend.corecommerce.dto.ProductDTO;
import com.oryanend.corecommerce.dto.ProductMinDTO;
import com.oryanend.corecommerce.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar produto por ID",
            description = """
           Retorna os detalhes completos de um produto específico.
           Requer um ID válido de produto cadastrado no sistema.
           """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = productService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos paginados",
            description = "Retorna uma lista paginada de produtos com possibilidade de filtro por nome")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Parâmetros de paginação inválidos", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<Page<ProductMinDTO>> findAll(
            @Parameter(description = "Filtro por nome do produto")
            @RequestParam(name = "name", defaultValue = "") String name,
            @ParameterObject
            @PageableDefault(size = 12, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {

        Page<ProductMinDTO> dto = productService.findAll(name, pageable);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Cadastrar novo produto",
            description = "Cria um novo produto no sistema. Informe os dados do produto no schema JSON.")
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
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDTO) {
        productDTO = productService.insert(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(productDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente. Informe o Id do produto e defina a suas alterações no schema JSON.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Apenas usuários admin podem executar essa ação", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO){
        productDTO = productService.update(productDTO, id);
        return ResponseEntity.ok().body(productDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Excluir produto",
            description = "Remove um produto existente do sistema. Requer permissões de administrador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Falha de integridade referencial.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Apenas usuários admin podem executar essa ação",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Credenciais válidas mas sem permissão suficiente",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
