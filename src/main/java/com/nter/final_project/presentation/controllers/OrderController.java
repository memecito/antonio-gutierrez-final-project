package com.nter.final_project.presentation.controllers;

import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.country.CountryInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok("get all user, logica por hacer");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok("get by id, logica por hacer");
    }

    @PostMapping
    public ResponseEntity<?> created(@RequestBody ApiUserInDto apiUser) {
        return ResponseEntity.ok("post user, logica por hacer");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ApiUserInDto apiUser) {
        return ResponseEntity.ok("update, logica por hacer");
    }

    @PutMapping("/{id}/country")
    public ResponseEntity<?> updateCountry(@PathVariable Long id, @RequestBody CountryInDto country) {
        return ResponseEntity.ok("update country, logica por hacer");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleted(@PathVariable Long id){
        return ResponseEntity.ok("delete user, logica por hacer");
    }
}
