package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.services.AuthService;
import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.exception.UnauthorizedException;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.StatusOrder;
import com.nter.final_project.persistence.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderProductService orderProductService;
    private final ProductService productService;
    
    @Lazy
    @Autowired
    private AuthService authService;

    @Override
    public Page<Order> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getUsersOrders(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findByUser_Email(authService.currentUser().getEmail(), pageable);
    }

    @Override
    public Set<Order> getByUser(String name) {
        authService.havePermision(name);
        return orderRepository.findByUser_Email(name);
    }

    @Override
    public Set<Order> getByProduct(Long id) {
        return orderRepository.findByOrderProducts_OrderProductId_Product(productService.getById(id));
    }

    @Override
    public Order getById(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Orden no encontrada, OS01")
        );
        authService.havePermision(order.getUser().getId());

        if (!Objects.equals(authService.currentUser().getEmail(), order.getUser().getEmail())) {
            throw new UnauthorizedException("No tiene permisos para ver esta Orden, OS02");
        }
        return order;
    }

    /*
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Orden no encontrada, OS01")
        );
    }

     */

    @Override
    @Transactional
    public Order created(Order order) {
        authService.havePermision(order.getUser().getId());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        orderRepository.save(order);
        orderProductService.created(order);
        return order;
    }

    @Override
    @Transactional
    public Order update(Long id, Order order) {


        Order orderFound = getById(id);
        authService.havePermision(orderFound.getUser().getId());

        order.getOrderProducts().forEach(
                orderProduct -> {
                    orderProduct.getOrderProductId().setOrder(orderFound);
                    orderProductService.update(orderProduct.getOrderProductId(), orderProduct);
                }
        );
        return orderFound;
    }

    @Override
    @Transactional
    public Order updateStatus(Long id, String status) {
        Order orderFound = getById(id);
        if (Objects.equals(orderFound.getStatus(), StatusOrder.CANCELLED))
            throw new BadRequestException("Orden cancelada no se puede modificar");
        authService.havePermision(orderFound.getUser().getId());
        return checkStatus(getById(id), status);
    }

    @Override
    @Transactional
    public void deleted(Long id) {
        Order order = getById(id);
        authService.havePermision(order.getUser().getId());
        if (Objects.equals(order.getStatus(), StatusOrder.COMPLETED))
            throw new BadRequestException("Orden completada no se puede eliminar");
        order.setStatus(StatusOrder.CANCELLED);
        orderRepository.save(order);
    }

    public Order checkStatus(Order order, String status) {
        String normalizedStatus = status.trim().toUpperCase();
        boolean isValidStatus = Arrays.stream(StatusOrder.values())
                .anyMatch(enumValue -> enumValue.name().equals(normalizedStatus));
        if (!isValidStatus) {
            throw new BadRequestException("El estado '" + status + "' no es válido., OS03");
        }
        if (Objects.equals(normalizedStatus, StatusOrder.CANCELLED.toString())) {
            deleted(order.getId());
        }
        int saltos = order.getStatus().compareTo(StatusOrder.valueOf(normalizedStatus));
        if ((saltos < (-1) || saltos > 1)) {
            throw new BadRequestException("Cambio de estado no valido, OS04");
        }

        order.setStatus(StatusOrder.valueOf(normalizedStatus));
        return order;
    }
}
