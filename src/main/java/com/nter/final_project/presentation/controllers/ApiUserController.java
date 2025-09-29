package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.ApiUserMapped;
import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.presentation.dto.BasicResponseDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDtoMini;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDtoOrders;
import com.nter.final_project.presentation.dto.country.CountryInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        /*
        Page<ApiUser> userPage = apiUserService.getAll(pageNumber, pageSize);
        Page<ApiUserOutDtoMini> userMiniPage = userPage.map(apiUserMapped::toDtoMini);
         */
        return ResponseEntity.ok(apiUserService.getAll(pageNumber, pageSize)
                .map(apiUserMapped::toDtoMini));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiUserOutDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.getById(id)));
    }

    /*
    @GetMapping("/{id}/orders")
    public ResponseEntity<ApiUserOutDtoOrders> getUserOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(apiUserMapped.toDtoOrders(apiUserService.getById(id)));
    }
     */

    @PostMapping
    public ResponseEntity<ApiUserOutDto> created(@RequestBody ApiUserInDto apiUser) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.created(apiUserMapped.toModel(apiUser))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiUserOutDto> update(@PathVariable Long id, @RequestBody ApiUserInDto apiUser) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.update(id, apiUserMapped.toModel(apiUser))));
    }

    @PutMapping("/{id}/country")
    public ResponseEntity<?> updateCountry(@PathVariable Long id, @RequestBody CountryInDto country) {
        return ResponseEntity.ok("update country, logica por hacer");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponseDto> deleted(@PathVariable Long id) {
        apiUserService.deleted(id);
        return ResponseEntity.ok(BasicResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Usuario eliminado")
                .build());
    }
}
