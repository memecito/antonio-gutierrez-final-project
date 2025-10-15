package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.exception.UnauthorizedException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.entity.StatusOrder;
import com.nter.final_project.persistence.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private OrderProductServiceImpl orderProductService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void getAll() {
        Page<Order> orders = DataProviders.pageOrders();
        Pageable pageable = PageRequest.of(0, 5);

        when(orderRepository.findAll(pageable)).thenReturn(orders);

        Page<Order> pageResutl = orderService.getAll(0, 5);

        assertNotNull(pageResutl);
    }

    @Test
    void getUsersOrders() {

        String token = DataProviders.tokenMock();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Order> orders = DataProviders.pageOrders();
        ApiUser user= DataProviders.userMock();

        when(jwtService.extractUsername(anyString())).thenReturn(user.getEmail());
        when(orderRepository.findByUser_Email(user.getEmail(), pageable)).thenReturn(orders);

        Page<Order> ordersResult = orderService.getUsersOrders(0, 5, token);

        assertNotNull(ordersResult);

    }

    @Test
    void getByProduct() {

        Long id = 1L;

        when(productService.getById(id)).thenReturn(DataProviders.productMock());

        when(orderRepository.findByOrderProducts_OrderProductId_Product(any(Product.class)))
                .thenReturn(DataProviders.orderSetMock());

        Set<Order> ordersResult = orderService.getByProduct(id);

        assertNotNull(ordersResult);
    }

    @Test
    void getByIdToken() {
        Long id = 1L;
        String token = DataProviders.tokenMock();
        ApiUser user = DataProviders.userMock();
        Order order= DataProviders.orderMock();
        order.setUser(user);

        when(jwtService.extractUsername(token.substring(7))).thenReturn(user.getEmail());
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));


        Order orderResult = orderService.getById(id, token);

        assertNotNull(orderResult);
    }
    @Test
    void getByIdTokenExceptionUnauthorized() {
        Long id = 1L;
        String token = DataProviders.tokenMock();
        ApiUser user = DataProviders.userMock();

        String message= "No tiene permisos para ver esta Orden, OS02";

        when(jwtService.extractUsername(token.substring(7))).thenReturn(user.getEmail());
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(DataProviders.orderMock()));

        Exception exception= assertThrows(UnauthorizedException.class,
                ()->orderService.getById(id,token));

        assertEquals(message,exception.getMessage());


    }

    @Test
    void testGetById() {
        Long id = 1L;

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(DataProviders.orderMock()));

        Order orderResult = orderService.getById(id);

        assertNotNull(orderResult);

    }

    @Test
    void getByIdException() {

        String message = "Orden no encontrada, OS01";

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> orderService.getById(anyLong()));

        assertEquals(message, exception.getMessage());

    }

    @Test
    void created() {
        Order order = DataProviders.orderMock();
        String token = DataProviders.tokenMock();

        //when(jwtService.authorization(order.getUser().getId(), token)).thenReturn(true);

        when(orderRepository.save(order)).thenReturn(order);
        when(orderProductService.created(order)).thenReturn(DataProviders.orderProductListMock());


        Order orderResult = orderService.created(order, token);

        assertNotNull(orderResult);

    }

    @Test
    void testGetByProduct() {
        Long id = 1L;
        when(productService.getById(anyLong())).thenReturn(DataProviders.productMock());
        when(orderRepository.findByOrderProducts_OrderProductId_Product(any(Product.class)))
                .thenReturn(DataProviders.orderSetMock());

        Set<Order> ordersResult = orderService.getByProduct(id);

        assertNotNull(ordersResult);
    }

    @Test
    void testUpdate() {
        Long id= 1L;
        Order order= DataProviders.orderMock();
        ApiUser user= DataProviders.userMock();
        user.setId(id);
        order.setUser(user);
        order.setOrderProducts(DataProviders.orderProductSetMock());
        String token= DataProviders.tokenMock();

        //when(jwtService.authorization(id,token)).thenReturn(true);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));



        Order orderResult=orderService.update(id,order,token);

        assertNotNull(orderResult);

    }


    @Test
    void testUpdateStatus() {
        Order order = DataProviders.orderMock();
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        String status = StatusOrder.PROCESSING.toString();
        String token= DataProviders.tokenMock();


        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order orderResult = orderService.updateStatus(1L, status,token);

        assertNotNull(orderResult);

    }

    @Test
    void testDeleted() {

        Long id= 1L;
        String token= DataProviders.tokenMock();
        Order order= DataProviders.orderMock();
        order.setStatus(StatusOrder.CANCELLED);


        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));
        //when(jwtService.authorization(anyLong(),anyString())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenReturn(null);

        orderService.deleted(id,token);
        verify(orderRepository,times(1)).save(order);

    }

    @Test
    void testCheckStatusException1() {
        Order order = DataProviders.orderMock();
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        String status = StatusOrder.SHIPPED.toString();
        String token= DataProviders.tokenMock();

        String message = "Cambio de estado no valido, OS04";

        Exception exception = assertThrows(BadRequestException.class,
                () -> orderService.checkStatus(order, status,token));

        assertEquals(message, exception.getMessage());

    }

    @Test
    void testCheckStatusException2() {
        Order order = DataProviders.orderMock();
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        String status = "shiped";
        String token= DataProviders.tokenMock();

        String message = "El estado 'shiped' no es válido., OS03";

        Exception exception = assertThrows(BadRequestException.class,
                () -> orderService.checkStatus(order, status, token));

        assertEquals(message, exception.getMessage());

    }

    @Test
    void testCheckStatus() {
        Order order = DataProviders.orderMock();
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        String status = StatusOrder.PROCESSING.toString();
        String token= DataProviders.tokenMock();

        Order orderResutl = orderService.checkStatus(order, status,token);

        assertNotNull(orderResutl);


    }
}