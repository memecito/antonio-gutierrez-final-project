package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.OrderMapped;
import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.exception.UnauthorizedException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.StatusOrder;
import com.nter.final_project.persistence.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapped orderMapped;

    private final OrderProductService orderProductService;
    private final ProductService productService;

    @Autowired
    private JwtService jwtService;

    @Override
    public Page<Order> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getUsersOrders(int pageNumber, int pageSize, String token) {
        ApiUser user = jwtService.extractUser(token);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> orderPage= orderRepository.findByUser_Id(user.getId(), pageable);
        return orderPage;
    }

    @Override
    public Page<Order> getByDate(LocalDateTime starDate,
                                 LocalDateTime endDate,
                                 int pageNumber,
                                 int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findByCreatedAtBetween(starDate, endDate, pageable);
    }


    @Override
    public Set<Order> getByProduct(Long id) {
        return orderRepository.findByOrderProducts_OrderProductId_Product(productService.getById(id));
    }

    @Override
    public Order getById(Long id,String token) {
        ApiUser user = jwtService.extractUser(token);
        Order order= orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Orden no encontrada, OS01")
        );
        if(!Objects.equals(user.getId(),order.getUser().getId())){
            throw new UnauthorizedException("No tiene permisos para ver esta Orden, OS02");
        }
        return order;
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Orden no encontrada, OS01")
        );
    }

    @Override
    @Transactional
    public Order created(Order order, String token) {
        jwtService.authorization(order.getUser().getId(),token);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        orderProductService.created(order);
        return order;
    }

    @Override
    @Transactional
    public Order update(Long id, Order order, String token) {
        jwtService.authorization(order.getUser().getId(),token);

        Order orderFound = getById(id,token);
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
        return checkStatus(getById(id), status);
    }

    @Override
    @Transactional
    public void deleted(Long id, String token) {
        Order order = getById(id);
        jwtService.authorization(order.getUser().getId(),token);
        order.setStatus(StatusOrder.CANCELLED);
    }

    public Order checkStatus(Order order, String status) {
        final String normalizedStatus = status.trim().toUpperCase();
        boolean isValidStatus = Arrays.stream(StatusOrder.values())
                .anyMatch(enumValue -> enumValue.name().equals(normalizedStatus));

        if (!isValidStatus) {
            throw new BadRequestException("El estado '" + status + "' no es válido., OS03");
        }
        int saltos = order.getStatus().compareTo(StatusOrder.valueOf(normalizedStatus));
        if ((saltos < (-1) || saltos > 1) && !Objects.equals(normalizedStatus, StatusOrder.CANCELLED.toString())) {
            throw new BadRequestException("Cambio de estado no valido, OS04");
        }
        order.setStatus(StatusOrder.valueOf(normalizedStatus));
        return order;
    }
}
