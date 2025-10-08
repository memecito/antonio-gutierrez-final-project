package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private JwtService jwtService;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void getAll() {
        Page<Order> orders= DataProviders.pageOrders();
        Pageable pageable = PageRequest.of(0,5);

        when(orderRepository.findAll(pageable)).thenReturn(orders);

        Page<Order> pageResutl= orderService.getAll(0,5);

        assertNotNull(pageResutl);
    }

    @Test
    void getUsersOrders() {

        String token= DataProviders.tokenMock();
        Pageable pageable= PageRequest.of(0,5);
        Page<Order> orders  = DataProviders.pageOrders();
        String user= "nombre";

        when(jwtService.extractUsername(anyString())).thenReturn(user);
        when(orderRepository.findByUser_Email(user,pageable)).thenReturn(orders);

        Page<Order> ordersResult= orderService.getUsersOrders(0,5,token);

        assertNotNull(ordersResult);

    }

    @Test
    void getByDate() {
    }

    @Test
    void getByProduct() {
    }

    @Test
    void getById() {
    }

    @Test
    void testGetById() {
    }

    @Test
    void created() {
    }

    @Test
    void update() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void deleted() {
    }

    @Test
    void checkStatus() {
    }

    @Test
    void testGetAll() {
    }

    @Test
    void testGetUsersOrders() {
    }

    @Test
    void testGetByDate() {
    }

    @Test
    void testGetByProduct() {
    }

    @Test
    void testGetById1() {
    }

    @Test
    void testGetById2() {
    }

    @Test
    void testCreated() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testUpdateStatus() {
    }

    @Test
    void testDeleted() {
    }

    @Test
    void testCheckStatus() {
    }
}