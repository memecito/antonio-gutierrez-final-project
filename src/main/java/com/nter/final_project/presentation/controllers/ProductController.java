package com.nter.final_project.presentation.controllers;

import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.country.CountryInDto;
import com.nter.final_project.presentation.dto.product.ProductInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                    @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok("get all user, logica por hacer");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok("get by id, logica por hacer");
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
        return ResponseEntity.ok("delete user, logica por hacer");
    }
}
