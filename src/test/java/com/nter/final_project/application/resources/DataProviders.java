package com.nter.final_project.application.resources;

import com.nter.final_project.persistence.entity.*;
import com.nter.final_project.presentation.dto.PageResponse;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class DataProviders {


    public static Page<ApiUser> pageApiUserMock() {
        List<ApiUser> userList = List.of(
                new ApiUser("Elena Navarro", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "elena.navarro@example.com", "ES", true, "2024-11-20 10:30:00", true),
                new ApiUser("John Smith", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "john.smith@example.com", "US", true, "2025-01-15 14:22:00", false),
                new ApiUser("Marie Dubois", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "marie.dubois@example.com", "FR", false, "2025-03-10 09:00:00", false),
                new ApiUser("Lukas Müller", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "lukas.muller@example.com", "DE", true, "2025-05-01 18:45:30", false),
                new ApiUser("Carlos Rodriguez", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "carlos.r@example.com", "MX", true, "2024-09-05 20:15:00", false),
                new ApiUser("Sophia Rossi", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "sophia.rossi@example.com", "IT", true, "2025-02-18 11:00:00", false),
                new ApiUser("Olivia Chen", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "olivia.chen@example.com", "AU", false, "2025-06-22 05:30:00", false),
                new ApiUser("Liam O''Sullivan", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "liam.osullivan@example.com", "IE", true, "2024-12-30 13:00:00", false)
        );
        Pageable pageable = PageRequest.of(0, 5);
        return new PageImpl<>(userList, pageable, userList.size());
    }

    public static Optional<List<ApiUser>> userOptionalListMock() {
        return Optional.of(List.of(
                new ApiUser("Elena Navarro", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "elena.navarro@example.com", "ES", true, "2024-11-20 10:30:00", true),
                new ApiUser("John Smith", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "john.smith@example.com", "US", true, "2025-01-15 14:22:00", false),
                new ApiUser("Marie Dubois", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "marie.dubois@example.com", "FR", false, "2025-03-10 09:00:00", false),
                new ApiUser("Lukas Müller", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "lukas.muller@example.com", "DE", true, "2025-05-01 18:45:30", false),
                new ApiUser("Carlos Rodriguez", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "carlos.r@example.com", "MX", true, "2024-09-05 20:15:00", false),
                new ApiUser("Sophia Rossi", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "sophia.rossi@example.com", "IT", true, "2025-02-18 11:00:00", false),
                new ApiUser("Olivia Chen", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "olivia.chen@example.com", "AU", false, "2025-06-22 05:30:00", false),
                new ApiUser("Liam O''Sullivan", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "liam.osullivan@example.com", "IE", true, "2024-12-30 13:00:00", false)
        ));
    }

    public static List<ApiUser> userlListMock() {
        return List.of(
                new ApiUser("Elena Navarro", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "elena.navarro@example.com", "ES", true, "2024-11-20 10:30:00", true),
                new ApiUser("John Smith", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "john.smith@example.com", "US", true, "2025-01-15 14:22:00", false),
                new ApiUser("Marie Dubois", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "marie.dubois@example.com", "FR", false, "2025-03-10 09:00:00", false),
                new ApiUser("Lukas Müller", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "lukas.muller@example.com", "DE", true, "2025-05-01 18:45:30", false),
                new ApiUser("Carlos Rodriguez", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "carlos.r@example.com", "MX", true, "2024-09-05 20:15:00", false),
                new ApiUser("Sophia Rossi", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "sophia.rossi@example.com", "IT", true, "2025-02-18 11:00:00", false),
                new ApiUser("Olivia Chen", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "olivia.chen@example.com", "AU", false, "2025-06-22 05:30:00", false),
                new ApiUser("Liam O''Sullivan", "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW", "liam.osullivan@example.com", "IE", true, "2024-12-30 13:00:00", false)
        );
    }

    public static Optional<ApiUser> userOptionalMock() {
        return Optional.of(new ApiUser(
                "Elena Navarro",
                "elena.navarro@example.com",
                "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW",

                "ES",
                true,
                "2024-11-20 10:30:00",
                true)
        );
    }

    public static String tokenMock() {
        return "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJlbGVuYS5uYXZhcnJvQGV4YW1wbGUuY29tIiwiaWF0IjoxNzU5ODI5MTk4LCJleHAiOjE3NTk4MzI3OTgsImFjY2Vzc190eXBlIjoiYWNjZXNzX3Rva2VuIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.Wp2ePIqF1fbhXfPDJPm2eqJAK0LmzDuS_a_O4Yx5ursHte0Q2Cm9NVrUPEHthGQ";
    }

    public static ApiUser userMock() {
        return new ApiUser(
                "Elena Navarro",
                "elena.navarro@example.com",
                "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW",

                "ES",
                true,
                "2024-11-20 10:30:00",
                true
        );
    }

    public static ApiUser userMockUser() {
        return new ApiUser(
                "Elena Navarro",
                "elena.navarro@example.com",
                "$2a$10$hs/gZcOJGFaXTIRVCMNhquBQczdQEb644Rk3MQGn/Q6JUBFkgLxGW",

                "ES",
                true,
                "2024-11-20 10:30:00",
                false
        );
    }

    public static UserDetails userDetailsMock() {
        ApiUser user = userMock();
        return User.builder()
                .username("email")
                .password("password")
                .roles(new String[]{"ADMIN", "USER"})
                .build();
    }

    public static String[] getRoles(boolean rol) {
        if (rol)
            return new String[]{"ADMIN", "USER"};

        return new String[]{"USER"};
    }

    public static Page<Country> pageCountryMock() {
        List<Country> countries = List.of(
                new Country("AL", "Albania"),
                new Country("AD", "Andorra"),
                new Country("AM", "Armenia"),
                new Country("AT", "Austria"),
                new Country("BY", "Bielorrusia"),
                new Country("BE", "Bélgica"),
                new Country("BA", "Bosnia y Herzegovina"),
                new Country("BG", "Bulgaria"),
                new Country("HR", "Croacia"),
                new Country("CY", "Chipre"),
                new Country("CZ", "República Checa"),
                new Country("DK", "Dinamarca"),
                new Country("EE", "Estonia"),
                new Country("FI", "Finlandia"),
                new Country("FR", "Francia"),
                new Country("DE", "Alemania")
        );
        Pageable pageable = PageRequest.of(0, 5);
        return new PageImpl<>(countries, pageable, countries.size());
    }

    public static Optional<Country> countryOptionalMock() {
        return Optional.of(new Country("DE", "Alemania"));
    }

    public static Country countryMock() {
        return new Country("ES", "España");
    }

    public static Page<Product> pageProductMok() {
        List<Product> products = List.of(
                new Product("Laptop Pro X15", 1250.99, "IN_STOCK", "2024-01-10 09:00:00"),
                new Product("Gaming Mouse G502", 75.50, "AVAILABLE", "2024-01-11 10:30:00"),
                new Product("Mechanical Keyboard K8", 130.00, "IN_STOCK", "2024-01-12 11:45:00"),
                new Product("4K Monitor 27", 450.80, "OUT_STOCK", "2024-02-05 14:00:00"),
                new Product("Webcam HD 1080p", 60.00, "IN_STOCK", "2024-02-06 15:20:00"),
                new Product("SSD NVMe 1TB", 110.25, "AVAILABLE", "2024-02-07 16:00:00"),
                new Product("RAM DDR4 16GB Kit", 85.00, "IN_STOCK", "2024-03-15 09:30:00"),
                new Product("CPU Core i9", 580.90, "IN_STOCK", "2024-03-16 10:00:00"),
                new Product("GPU RTX 4070", 650.00, "IN_STOCK", "2024-03-17 11:10:00"),
                new Product("Motherboard Z790", 220.40, "AVAILABLE", "2024-04-01 12:00:00"),
                new Product("Power Supply 750W Gold", 115.00, "DISCONTINUED", "2024-04-02 13:00:00"),
                new Product("PC Case Mid-Tower", 95.99, "IN_STOCK", "2024-04-03 14:30:00"),
                new Product("Wireless Headset H7", 150.00, "AVAILABLE", "2024-05-10 09:00:00"),
                new Product("External Hard Drive 2TB", 70.00, "IN_STOCK", "2024-05-11 10:45:00"),
                new Product("USB-C Hub 8-in-1", 45.50, "IN_STOCK", "2024-05-12 11:00:00"),
                new Product("Graphics Tablet Pro", 320.00, "AVAILABLE", "2024-06-20 14:00:00"),
                new Product("Laptop Cooler Pad", 35.20, "IN_STOCK", "2024-06-21 15:00:00")
        );
        Pageable pageable = PageRequest.of(0, 5);
        return new PageImpl<>(products, pageable, products.size());
    }

    public static Product productMock() {
        return new Product("Laptop Pro X15", 1250.99, "IN_STOCK", "2024-01-10 09:00:00");

    }

    public static Optional<Product> productOptionalMock() {
        return Optional.of(
                new Product(
                        "Laptop Pro X15",
                        1250.99,
                        "IN_STOCK",
                        "2024-01-10 09:00:00"));

    }


    public static Page<Order> pageOrders() {
        List<Order> orderList = List.of(
                new Order(1L, "COMPLETED", "2025-01-15 10:30:00"),
                new Order(2L, "SHIPPED", "2025-09-20 11:00:00"),
                new Order(5L, "PROCESSING", "2025-09-24 09:15:00"),
                new Order(3L, "COMPLETED", "2025-03-05 14:00:00"),
                new Order(4L, "CANCELLED", "2025-02-10 18:45:00"),
                new Order(1L, "RETURNED", "2025-04-22 12:00:00"),
                new Order(6L, "DELIVERED", "2025-08-01 16:20:00"),
                new Order(8L, "PROCESSING", "2025-09-23 13:00:00"),
                new Order(11L, "SHIPPED", "2025-09-21 17:00:00"),
                new Order(12L, "CANCELLED", "2025-06-30 20:00:00"),
                new Order(15L, "COMPLETED", "2025-07-11 09:00:00"),
                new Order(9L, "PROCESSING", "2025-09-22 10:10:00")
        );
        Pageable pageable = PageRequest.of(0, 5);
        return new PageImpl<>(orderList, pageable, orderList.size());
    }

    public static Order orderMock() {
        return new Order(1L, "PROCESSING", "2025-01-15 10:30:00");

    }

    public static Set<Order> orderSetMock() {
        return new HashSet<>() {{
            add(new Order(1L, "COMPLETED", "2025-01-15 10:30:00"));
            add(new Order(2L, "COMPLETED", "2025-01-15 10:30:00"));
            add(new Order(3L, "COMPLETED", "2025-01-15 10:30:00"));
            add(new Order(4L, "COMPLETED", "2025-01-15 10:30:00"));
        }};
    }

    public static OrderProduct orderProductMock() {
        return new OrderProduct(new OrderProductId(), 5);
    }

    public static Set<OrderProduct> orderProductSetMock() {
        return new HashSet<>() {{
            add(new OrderProduct(orderProductIdMock(), 5));
            }};
    }

    public static OrderProductId orderProductIdMock(){
        Product product= new Product();
        product.setId(1L);
        Order order= new Order();
        order.setId(1L);
        return new OrderProductId(
                product,
                order
        );
    }
}
