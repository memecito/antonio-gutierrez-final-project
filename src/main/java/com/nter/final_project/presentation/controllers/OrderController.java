package com.nter.final_project.presentation.controllers;

import com.nter.final_project.application.mappers.OrderMapped;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.presentation.dto.BasicResponseDto;
import com.nter.final_project.presentation.dto.order.OrderInDto;
import com.nter.final_project.presentation.dto.order.OrderOutDto;
import com.nter.final_project.presentation.dto.order.OrderOutDtoMIni;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapped orderMapped;

    @GetMapping
    public ResponseEntity<Page<OrderOutDtoMIni>> getAll(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                       @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(orderService.getAll(pageNumber,pageSize)
                .map(orderMapped::toDtoMini));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(orderMapped.toDto(orderService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderOutDto> created(@RequestBody OrderInDto order) {
        Order ord= orderMapped.toModel(order);
        return ResponseEntity.ok(orderMapped.toDto(orderService.created(ord)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOutDto> update(@PathVariable Long id, @RequestBody OrderInDto order) {
        return ResponseEntity.ok(orderMapped.toDto(orderService.update(id,orderMapped.toModel(order))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponseDto> deleted(@PathVariable Long id) {
        orderService.deleted(id);
        return ResponseEntity.ok(BasicResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Orden eliminada")
                .build());
    }
}
