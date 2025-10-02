package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.OrderMapped;
import com.nter.final_project.application.services.OrderProductService;
import com.nter.final_project.application.services.OrderService;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.StatusOrder;
import com.nter.final_project.persistence.repository.OrderRepository;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.StandardServletAsyncWebRequest;

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

    @Override
    public Page<Order> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getByDate(LocalDateTime starDate, LocalDateTime endDate, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findByCreatedAtBetween(starDate, endDate, pageable);
    }


    @Override
    public Set<Order> getByProduct(Long id) {
        return orderRepository.findByOrderProducts_OrderProductId_Product(productService.getById(id));
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Orden no encontrada, OS01")
        );
    }


    @Override
    @Transactional
    public Order created(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        orderProductService.created(order);
        return order;
    }

    @Override
    @Transactional
    public Order update(Long id, Order order) {
        Order orderFound = getById(id);
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
        return checkStatus(getById(id),status);
    }

    @Override
    @Transactional
    public void deleted(Long id) {
        Order order = getById(id);
        order.setStatus(StatusOrder.CANCELLED);
        //orderRepository.save(order);
    }

    public Order checkStatus(Order order, String status) {
        final String normalizedStatus = status.trim().toUpperCase();
        boolean isValidStatus = Arrays.stream(StatusOrder.values())
                .anyMatch(enumValue -> enumValue.name().equals(normalizedStatus));

        if (!isValidStatus) {
            throw new BadRequestException("El estado '" + status + "' no es válido.");
        }
        int saltos = order.getStatus().compareTo(StatusOrder.valueOf(normalizedStatus));
        if ((saltos < (-1) || saltos > 1) && !Objects.equals(normalizedStatus, StatusOrder.CANCELLED.toString())) {
            throw new BadRequestException("Cambio de estado no valido");
        }
        order.setStatus(StatusOrder.valueOf(normalizedStatus));
       return order;
    }
}
