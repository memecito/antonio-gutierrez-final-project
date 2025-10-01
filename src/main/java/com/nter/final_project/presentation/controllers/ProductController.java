package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.ProductMapped;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.repository.ProductRepository;
import com.nter.final_project.presentation.dto.BasicResponseDto;
import com.nter.final_project.presentation.dto.product.ProductInDto;
import com.nter.final_project.presentation.dto.product.ProductOutDto;
import com.nter.final_project.presentation.dto.product.ProductOutDtoMIni;
import com.nter.final_project.presentation.dto.product.ProductUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapped productMapped;

    @GetMapping
    public ResponseEntity<Page<ProductOutDtoMIni>> getAllAvailable(@RequestParam(defaultValue = "0", required = false) int page,
                                                                   @RequestParam(defaultValue = "10", required = false) int size) {

        return ResponseEntity.ok(productService.getAllAvailable(page, size)
                .map(productMapped::toDtoMini));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductOutDtoMIni>> getAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                          @RequestParam(defaultValue = "10", required = false) int size) {

        return ResponseEntity.ok(productService.getAll(page, size)
                .map(productMapped::toDtoMini));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductOutDtoMIni>> getSearch(@RequestParam Map<String, String> params,
                                                             @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(
                productService.getByCriteria(params, page, size).map(productMapped::toDtoMini));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOutDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productMapped.toDto(productService.getById(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductOutDto> getByName(@PathVariable String name) {
        return ResponseEntity.ok(productMapped.toDto(productService.getByName(name)));
    }

    @PostMapping
    public ResponseEntity<ProductOutDto> created(@Valid @RequestBody ProductInDto product) {
        return ResponseEntity.ok(productMapped.toDto(productService.created(productMapped.toModel(product))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOutDto> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDto product) {
        return ResponseEntity.ok(productMapped.toDto(productService.update(id, productMapped.toModelUpdate(product))));
    }

    @PutMapping("/name/{name}/available")
    public ResponseEntity<ProductOutDto> updateStatus(@PathVariable String name) {
        return ResponseEntity.ok(productMapped.toDto(productService.updateStatus(name)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponseDto> deleted(@PathVariable Long id) {
        productService.deleted(id);
        return ResponseEntity.ok(BasicResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Producto eliminado")
                .build());
    }
}
