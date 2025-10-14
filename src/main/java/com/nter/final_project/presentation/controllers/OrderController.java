package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.OrderMapped;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.presentation.dto.BasicResponseDto;
import com.nter.final_project.presentation.dto.PageResponse;
import com.nter.final_project.presentation.dto.order.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapped orderMapped;

    @GetMapping
    public ResponseEntity<PageResponse<OrderOutDtoMIni>> getUsersOrders(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                                        @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                                        Principal principal) {
        return ResponseEntity.ok(new PageResponse<>(orderService.getUsersOrders(pageNumber, pageSize, principal.getName())
                .map(orderMapped::toDtoMini)));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<OrderOutDtoMIni>> getAll(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                                @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(new PageResponse<>(orderService.getAll(pageNumber, pageSize)
                .map(orderMapped::toDtoMini)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @SecurityService.isOwner(authentication, @id)")
    public ResponseEntity<OrderOutDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderMapped.toDto(orderService.getById(id)));
    }

    @GetMapping("/user/{name}")
    @PreAuthorize("hasRole('ADMIN') or @SecurityService.isOwner(authentication, @id)")
    public ResponseEntity<Set<OrderOutDtoMIni>> getByUser(@PathVariable String name) {
        return ResponseEntity.ok( orderService.getByUser(name)
                .stream()
                .map(orderMapped::toDtoMini)
                .collect(Collectors.toSet()));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Set<OrderOutDtoMIni>> getByProduct(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getByProduct(id).stream()
                .map(orderMapped::toDtoMini)
                .collect(Collectors.toSet()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or @SecurityService.isOwner(authentication, @id)")
    public ResponseEntity<OrderOutDto> created(@Valid @RequestBody OrderInDto orderInDto){
        Order order = orderMapped.toModel(orderInDto);
        return ResponseEntity.ok(orderMapped.toDto(orderService.created(order)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @SecurityService.isOwner(authentication, @id)")
    public ResponseEntity<OrderOutDto> update(@PathVariable Long id,
                                              @Valid @RequestBody OrderUpdateDto orderUpdateDto,
                                              @NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(orderMapped.toDto(orderService.update(id, orderMapped.toModelUpdate(orderUpdateDto))));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or @SecurityService.isOwner(authentication, @id)")
    public ResponseEntity<OrderOutDto> updateStatus(@PathVariable Long id,
                                                    @RequestBody OrderStatusInDto status,
                                                    @NonNull HttpServletRequest request) {
        String statusStr = orderMapped.toModelStatus(status);
        return ResponseEntity.ok(orderMapped.toDto(orderService.updateStatus(id, statusStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponseDto> deleted(@PathVariable Long id,
                                                    @NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        orderService.deleted(id);
        return ResponseEntity.ok(BasicResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Orden cancelada")
                .build());
    }
}
