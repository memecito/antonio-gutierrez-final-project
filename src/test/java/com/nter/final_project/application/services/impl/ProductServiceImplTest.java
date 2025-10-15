package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.ProductMapped;
import com.nter.final_project.application.resources.DataProviders;
import com.nter.final_project.exception.EntityDuplicateException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.entity.StatusProduct;
import com.nter.final_project.persistence.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    public ProductRepository productRepository;
    @Mock
    public ProductMapped productMapped;

    @InjectMocks
    public ProductServiceImpl productService;

    @Test
    void getAllAvailable() {
        Page<Product> productPage = DataProviders.pageProductMok();
        Pageable pageable = PageRequest.of(0, 5);
        when(productRepository.findByStatusNot(StatusProduct.DISCONTINUED, pageable)).thenReturn(productPage);

        Page<Product> pageResult = productService.getAllAvailable(0, 5);

        assertNotNull(pageResult);
    }

    @Test
    void getAll() {

        Page<Product> productPage = DataProviders.pageProductMok();
        Pageable pageable = PageRequest.of(0, 5);

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<Product> pageResult = productService.getAll(0, 5);

        assertNotNull(pageResult);
        assertFalse(pageResult.isEmpty());
    }

    @Test
    void getById() {

        Long id = 1L;
        Product product = DataProviders.productMock();

        when(productRepository.findById(id)).thenReturn(DataProviders.productOptionalMock());

        Product productResult = productService.getById(id);

        assertNotNull(productResult);


    }

    @Test
    void getByIdException() {
        Long id = 1L;
        String message = "Producto no encontrado, PS01";

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.getById(id));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void getByName() {
        String name = "name";
        Product product = DataProviders.productMock();
        when(productRepository.findByName(name)).thenReturn(Optional.of(product));

        Product productResult = productService.getByName(name);

        assertNotNull(productResult);
    }

    @Test
    void getByNameException() {
        String name = "name";
        String message = "Producto no encontrado, PS02";

        when(productRepository.findByName(name)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.getByName(name));

        assertEquals(message, exception.getMessage());
    }

    @Test
    void getByCriteria() {
        Page<Product> products = DataProviders.pageProductMok();
        Pageable pageable = PageRequest.of(0, 5);

        Map<String, String> params = new HashMap<>();
        params.put("name", "elena.navarro");
        params.put("pricemax", "5.0");
        params.put("price", "100.0");

        when(productRepository.findProductByCustomParam(params, pageable)).thenReturn(products);

        Page<Product> pageResutl= productService.getByCriteria(params,0,5);
        assertNotNull(pageResutl);
    }

    @Test
    void created() {
        String name= "name";
        Product product= DataProviders.productMock();
        product.setName(name);
        when(productRepository.save(product)).thenReturn(product);

        Product productResult= productService.created(product);
        assertNotNull(productResult);
    }

    @Test
    void createdException(){
        String name="name";
        String message="Ya existe un producto con este nombre";
        Product product= DataProviders.productMock();
        product.setName(name);

        when(productRepository.findByName(anyString()))
                .thenReturn(Optional.of(product));

        Exception exception= assertThrows(EntityDuplicateException.class,
                ()-> productService.created(product));

        assertEquals(message,exception.getMessage());
    }

    @Test
    void update() {
        Long id= 1L;
        Product product= DataProviders.productMock();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapped.update(product,product)).thenReturn(product);

        Product productResult= productService.update(id,product);

        assertNotNull(productResult);
    }

    @Test
    void updateStatus() {
        Long id= 1L;
        String status= StatusProduct.AVAILABLE.toString();
        Product product= DataProviders.productMock();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product productResutl= productService.updateStatus(id,status);

        assertEquals(status, productResutl.getStatus().toString());




    }

    @Test
    void getActived() {
        String name= "nombre";
        Product product= DataProviders.productMock();
        product.setStatus(StatusProduct.AVAILABLE);

        when(productRepository.findByName(name)).thenReturn(Optional.of(product));

        Product productResult= productService.getActived(name);

        assertEquals(StatusProduct.AVAILABLE, productResult.getStatus());

    }

    @Test
    void deleted() {
        Long id=1L;
        Product product= DataProviders.productMock();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productService.deleted(id);

        verify(productRepository,times(1)).save(product);
    }
}