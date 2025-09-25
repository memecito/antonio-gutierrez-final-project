package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.ApiUserMapped;
import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDtoMini;
import com.nter.final_project.presentation.dto.country.CountryInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ApiUserController {

    private final ApiUserService apiUserService;
    private final ApiUserMapped apiUserMapped;

    @GetMapping
    public ResponseEntity<Page<ApiUserOutDtoMini>> getAll(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        Page<ApiUser> userPage= apiUserService.getAll(pageNumber,pageSize);
        Page<ApiUserOutDtoMini> userMiniPage=  userPage.map(apiUserMapped::toDtoMini);
        return ResponseEntity.ok(userMiniPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.getById(id)));
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
    public ResponseEntity<?> deleted(@PathVariable Long id) {
        return ResponseEntity.ok("delete user, logica por hacer");
    }
}
