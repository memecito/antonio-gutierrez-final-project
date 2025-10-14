package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.ApiUserMapped;
import com.nter.final_project.application.mappers.CountryMapped;
import com.nter.final_project.application.mappers.PageResponseMapped;
import com.nter.final_project.application.services.ApiUserService;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.presentation.dto.BasicResponseDto;
import com.nter.final_project.presentation.dto.PageResponse;
import com.nter.final_project.presentation.dto.apiuser.ApiUserInDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDto;
import com.nter.final_project.presentation.dto.apiuser.ApiUserOutDtoMini;
import com.nter.final_project.presentation.dto.apiuser.ApiUserUpdateDto;
import com.nter.final_project.presentation.dto.country.CountryUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ApiUserController {

    private final ApiUserService apiUserService;
    private final ApiUserMapped apiUserMapped;

    private final CountryMapped countryMapped;


    @GetMapping
    public ResponseEntity<PageResponse<ApiUserOutDtoMini>> getAll(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        Page<ApiUser> apiUsers = apiUserService.getAll(pageNumber, pageSize);
        return ResponseEntity.ok(new PageResponse<>(apiUsers
                .map(apiUserMapped::toDtoMini)));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<ApiUserOutDtoMini>> getAllActive(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        Page<ApiUser> apiUsers = apiUserService.getActive(pageNumber, pageSize);
        return ResponseEntity.ok(new PageResponse<>(apiUsers
                .map(apiUserMapped::toDtoMini)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiUserOutDto> getById(@PathVariable Long id,
                                                 HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.getById(id, authHeader)));
    }

    @PostMapping
    public ResponseEntity<ApiUserOutDto> created(@Valid @RequestBody ApiUserInDto apiUser) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.created(apiUserMapped.toModel(apiUser))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ApiUserUpdateDto apiUser) {
        ApiUser user = apiUserMapped.toModelUpdate(apiUser);
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.update(id, user)));
    }

    @PutMapping

    @PatchMapping("/{id}/country")
    public ResponseEntity<?> updateCountry(@PathVariable Long id, @Valid @RequestBody CountryUpdateDto country) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.updateCountry(id, countryMapped.toModelUpdate(country))));
    }

    @PutMapping("/{id}/desactived")
    public ResponseEntity<ApiUserOutDto> statusDesactived(@PathVariable Long id) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.statusDesactive(id)));
    }

    @PutMapping("/{id}/actived")
    public ResponseEntity<ApiUserOutDto> statusActived(@PathVariable Long id) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.statusActived(id)));
    }

    @PatchMapping("/{id}/become-admin")
    public ResponseEntity<ApiUserOutDto> becomeAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(apiUserMapped.toDto(apiUserService.updateAdmin(id)));
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
