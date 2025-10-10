package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.OrderProductMapper;
import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.OrderProduct;
import com.nter.final_project.persistence.entity.OrderProductId;
import com.nter.final_project.persistence.repository.OrderProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderProductServiceImplTest {

    @Mock
    private OrderProductRepository repository;

    @Mock
    private OrderProductMapper mapper;

    @InjectMocks
    private OrderProductServiceImpl orderProductService;


    @Test
    void getAll() {
        /*

        when(repository.findAll()).thenReturn(List.of());

        Set<OrderProduct> orderProductsResult = orderProductService.getAll();

        assertNotNull(orderProductsResult);
        assertTrue(orderProductsResult.isEmpty());

         */
    }

    @Test
    void getById() {
        OrderProductId orderProductId = DataProviders.orderProductIdMock();
        OrderProduct orderProduct = DataProviders.orderProductMock();
        when(repository.findById(orderProductId)).thenReturn(Optional.of(orderProduct));

        OrderProduct orderProductResutl = orderProductService.getById(orderProductId);

        assertNotNull(orderProductResutl);
    }

    @Test
    void getByIdException() {
        String message = "Orden no encontrada, OPS01";

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> orderProductService.getById(DataProviders.orderProductIdMock()));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void created() {
        Order order = DataProviders.orderMock();
        Set<OrderProduct> orderProductList = DataProviders.orderProductSetMock();
        order.setOrderProducts(orderProductList);
        when(repository.saveAll(orderProductList)).thenReturn(DataProviders.orderProductListMock());

        List<OrderProduct> listResult = orderProductService.created(order);

        assertNotNull(listResult);
    }

    @Test
    void createdException() {
        /*
        Order order = DataProviders.orderMock();
        Set<OrderProduct> orderProductList = DataProviders.orderProduct0Mock();
        order.setOrderProducts(orderProductList);
        String message = "com.nter.final_project.exception.ForbiddenOperationException: la candidad debe ser mayor de 0";
        Exception exception = assertThrows(ForbiddenOperationException.class,
                () -> orderProductService.created(order));

        assertEquals(message, exception);

         */
    }

    @Test
    void update() {
        OrderProductId orderProductId = DataProviders.orderProductIdMock();
        OrderProduct orderProduct = DataProviders.orderProductMock();


        when(repository.findById(orderProductId)).thenReturn(Optional.of(orderProduct));
        when(mapper.update(orderProduct, orderProduct)).thenReturn(orderProduct);

        OrderProduct orderProductResult = orderProductService.update(orderProductId, orderProduct);

        assertNotNull(orderProductResult);


    }

    @Test
    void delete() {


    }
}