package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.ProductMapped;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.exception.BadRequestException;
import com.nter.final_project.exception.EntityDuplicateException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.entity.StatusOrder;
import com.nter.final_project.persistence.entity.StatusProduct;
import com.nter.final_project.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapped productMapped;


    @Override
    public Page<Product> getAllAvailable(int pageNumber, int pageSize) {
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        return productRepository.findByStatusNot(StatusProduct.DISCONTINUED, pageable);
    }

    @Override
    public Page<Product> getAll(int pageNumber,int pageSize) {
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getById(Long id) {

        Product p= productRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Producto no encontrado, PS01")
        );
        if(Objects.equals(p.getStatus(),StatusProduct.DISCONTINUED))
            throw new EntityNotFoundException("Producto descatalogado");
        return p;
    }

    @Override
    public Product getByName(String name) {

        return productRepository.findByName(name).orElseThrow(
                ()-> new EntityNotFoundException("Producto no encontrado, PS02")
        ) ;
    }

    @Override
    public Page<Product> getByCriteria(Map<String, String> params, int pageNumber, int pageSize) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        return productRepository.findProductByCustomParam(params,pageable);
    }

    @Override
    @Transactional
    public Product created(Product product) {
        if(productRepository.findByName(product.getName()).isPresent())
            throw new EntityDuplicateException("Ya existe un producto con este nombre");
        product.setStatus(StatusProduct.AVAILABLE);
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long id, Product product) {
        Product prorductFound= getById(id);
        return productMapped.update(prorductFound, product);
    }

    @Override
    @Transactional
    public Product updateStatus(Long  id, String status) {
        Product product = getById(id);
        final String normalizedStatus = status.trim().toUpperCase();
        boolean isValidStatus = Arrays.stream(StatusOrder.values())
                .anyMatch(enumValue -> enumValue.name().equals(normalizedStatus));

        if (!isValidStatus) {
            throw new BadRequestException("El estado '" + status + "' no es válido.");
        }
        product.setStatus(StatusProduct.valueOf(normalizedStatus));
        return product;
    }

    @Override
    public Product getActived(String name) {
        Product product = getByName(name);
        product.setStatus(StatusProduct.AVAILABLE);
        return product;
    }

    @Override
    @Transactional
    public void deleted(Long id) {
        Product productFound= getById(id);
        productFound.setStatus(StatusProduct.DISCONTINUED);
        productRepository.save(productFound);

    }
}
