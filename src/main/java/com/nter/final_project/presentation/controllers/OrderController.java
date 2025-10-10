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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapped orderMapped;

    @GetMapping
    public ResponseEntity<PageResponse<OrderOutDtoMIni>> getUsersOrders(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                                        @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                                        HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(new PageResponse<>(orderService.getUsersOrders(pageNumber, pageSize, authHeader.substring(7))
                .map(orderMapped::toDtoMini)));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<OrderOutDtoMIni>> getAll(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                                @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(new PageResponse<>(orderService.getAll(pageNumber, pageSize)
                .map(orderMapped::toDtoMini)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutDto> getById(@PathVariable Long id, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(orderMapped.toDto(orderService.getById(id, authHeader.substring(7))));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getByProduct(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getByProduct(id));
    }

    @PostMapping
    public ResponseEntity<OrderOutDto> created(@Valid @RequestBody OrderInDto order,
                                               @NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Order ord = orderMapped.toModel(order);
        return ResponseEntity.ok(orderMapped.toDto(orderService.created(ord, authHeader.substring(7))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOutDto> update(@PathVariable Long id, @Valid @RequestBody OrderUpdateDto orderUpdateDto,
                                              @NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(orderMapped.toDto(orderService.update(id, orderMapped.toModelUpdate(orderUpdateDto), authHeader.substring(7))));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderOutDto> updateStatus(@PathVariable Long id, @RequestBody OrderStatusInDto status) {
        String statusStr = orderMapped.toModelStatus(status);
        return ResponseEntity.ok(orderMapped.toDto(orderService.updateStatus(id, statusStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponseDto> deleted(@PathVariable Long id,
                                                    @NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        orderService.deleted(id, authHeader.substring(7));
        return ResponseEntity.ok(BasicResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Orden cancelada")
                .build());
    }
}
