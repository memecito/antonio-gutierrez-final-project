package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.application.services.AuthService;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.UnauthorizedException;
import com.nter.final_project.persistence.entity.ApiUser;
import com.nter.final_project.persistence.entity.Order;
import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.entity.StatusOrder;
import com.nter.final_project.persistence.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

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
    private ApiUserServiceImpl userService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderProductServiceImpl orderProductService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(orderService, "authService", authService);
    }


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

        Pageable pageable = PageRequest.of(0, 5);
        Page<Order> orders = DataProviders.pageOrders();
        ApiUser user = DataProviders.userMock();
        when(userService.getByEmail(anyString())).thenReturn(user);

        when(authService.currentUser().getEmail()).thenReturn(user.getEmail());
        when(orderRepository.findByUser_Email(user.getEmail(), pageable)).thenReturn(orders);

        Page<Order> ordersResult = orderService.getUsersOrders(0, 5);

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
    void testGetById() {
        Long id = 1L;
        ApiUser user= DataProviders.userMock();
        user.setEmail("mail");
        Order order=DataProviders.orderMock();
        order.setUser(user);

        doNothing().when(authService).havePermision(id);
        when(authService.currentUser()).thenReturn(user);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order orderResult = orderService.getById(id);

        assertNotNull(orderResult);

    }

    @Test
    void getByIdException() {
        Long id = 1L;
        ApiUser user= DataProviders.userMock();
        Order order=DataProviders.orderMock();

        doNothing().when(authService).havePermision(id);
        when(authService.currentUser()).thenReturn(user);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThrows(UnauthorizedException.class,
                ()->orderService.getById(id));

    }

    @Test
    void created() {
        Order order = DataProviders.orderMock();


        doNothing().when(authService).havePermision(anyLong());
        when(orderRepository.save(order)).thenReturn(order);
        when(orderProductService.created(order)).thenReturn(DataProviders.orderProductListMock());


        Order orderResult = orderService.created(order);

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
        Long id = 1L;
        Order order = DataProviders.orderMock();
        ApiUser user = DataProviders.userMock();
        user.setId(id);
        order.setUser(user);
        order.setOrderProducts(DataProviders.orderProductSetMock());

        doNothing().when(authService).havePermision(anyLong());
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));


        Order orderResult = orderService.update(id, order);

        assertNotNull(orderResult);

    }


    @Test
    void testUpdateStatus() {
        Order order = DataProviders.orderMock();
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        String status = StatusOrder.PROCESSING.toString();


        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order orderResult = orderService.updateStatus(1L, status);

        assertNotNull(orderResult);

    }

    @Test
    void testDeleted() {

        Long id = 1L;
        ApiUser user= DataProviders.userMock();
        user.setEmail("mail");
        Order order=DataProviders.orderMock();
        order.setUser(user);
        order.setStatus(StatusOrder.CANCELLED);


        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));
        when(authService.currentUser()).thenReturn(user);
        doNothing().when(authService).havePermision(anyLong());
        when(orderRepository.save(any(Order.class))).thenReturn(null);

        orderService.deleted(id);
        verify(orderRepository, times(1)).save(order);

    }

    @Test
    void testCheckStatusException1() {
        Order order = DataProviders.orderMock();
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        String status = StatusOrder.SHIPPED.toString();

        String message = "Cambio de estado no valido, OS04";

        Exception exception = assertThrows(BadRequestException.class,
                () -> orderService.checkStatus(order, status));

        assertEquals(message, exception.getMessage());

    }

    @Test
    void testCheckStatusException2() {
        Order order = DataProviders.orderMock();
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        String status = "shiped";

        String message = "El estado 'shiped' no es válido., OS03";

        Exception exception = assertThrows(BadRequestException.class,
                () -> orderService.checkStatus(order, status));

        assertEquals(message, exception.getMessage());

    }

    @Test
    void testCheckStatus() {
        Order order = DataProviders.orderMock();
        order.setStatus(StatusOrder.PENDING_PAYMENT);
        String status = StatusOrder.PROCESSING.toString();

        Order orderResutl = orderService.checkStatus(order, status);

        assertNotNull(orderResutl);


    }
}