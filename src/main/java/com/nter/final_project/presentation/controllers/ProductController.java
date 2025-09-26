package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.ProductMapped;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.presentation.dto.BasicResponseDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.country.CountryInDto;
import com.nter.final_project.presentation.dto.product.ProductInDto;
import com.nter.final_project.presentation.dto.product.ProductOutDtoMIni;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapped productMapped;
    @GetMapping
    public ResponseEntity<Page<ProductOutDtoMIni>> getAll(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                          @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(productService.getAll(pageNumber, pageSize).map(productMapped::toDtoMini));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOutDtoMIni> getById(@PathVariable Long id) {

        return ResponseEntity.ok(productMapped.toDto(productService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<?> created(@RequestBody ProductInDto product) {
        return ResponseEntity.ok("post user, logica por hacer");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductInDto product) {
        return ResponseEntity.ok("update, logica por hacer");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleted(@PathVariable Long id){
        productService.deleted(id);
        return ResponseEntity.ok(BasicResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("usuario borrado")
                .build());
    }
}
