package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.CountryMapped;
import com.nter.final_project.application.services.CountryService;
import com.nter.final_project.persistence.entity.Country;
import com.nter.final_project.presentation.dto.BasicResponseDto;
import com.nter.final_project.presentation.dto.country.CountryInDto;
import com.nter.final_project.presentation.dto.country.CountryOutDto;
import com.nter.final_project.presentation.dto.country.CountryOutDtoMini;
import com.nter.final_project.presentation.dto.country.CountryUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    private final CountryMapped countryMapped;

    @GetMapping
    public ResponseEntity<Page<CountryOutDtoMini>> getAll(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                          @RequestParam(defaultValue = "10", required = false) int pageSize) {
        Page<Country> countryPage = countryService.getAll(pageNumber, pageSize);
        Page<CountryOutDtoMini> countryMini = countryPage.map(countryMapped::toDtoMini);
        return ResponseEntity.ok(countryMini);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CountryOutDto> getById(@PathVariable String code) {
        return ResponseEntity.ok(
                countryMapped.toDto(countryService.getByCode(code)));
    }

    @PostMapping
    public ResponseEntity<?> created(@RequestBody CountryInDto country) {
        return ResponseEntity.ok(countryMapped.toDto(countryService.created(countryMapped.toModel(country))));
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> update(@PathVariable String code,@Valid @RequestBody CountryUpdateDto country) {
        return ResponseEntity.ok(countryMapped.toDto(
                countryService.update(code, countryMapped.toModelUpdate(country))));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleted(@PathVariable String code) {
        countryService.deleted(code);
        return ResponseEntity.ok(BasicResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Pais eliminado")
                .build());
    }
}
