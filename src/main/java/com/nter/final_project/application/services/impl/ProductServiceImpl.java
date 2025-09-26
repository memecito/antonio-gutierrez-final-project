package com.nter.final_project.application.services.impl;

import com.nter.final_project.application.mappers.ProductMapped;
import com.nter.final_project.application.services.ProductService;
import com.nter.final_project.exception.EntityDuplicateException;
import com.nter.final_project.exception.EntityNotFoundException;
import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.entity.StatusProduct;
import com.nter.final_project.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapped productMapped;

    @Override
    public Page<Product> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return (Page<Product>) productRepository.findByStatusNot(StatusProduct.DISCONTINUED,pageable);
    }

    @Override
    public Product getById(Long id) {

        return productRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Producto no encontrado, PS01")
        );
    }

    @Override
    public Set<Product> getByName(String name) {

        return productRepository.findByNameLike(name);
    }

    @Override
    public Product created(Product product) {
        if(productRepository.findByName(product.getName()).isPresent())
            throw new EntityDuplicateException("El producto ya existe, PS02");
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product Product) {
        return null;
    }

    @Override
    public void deleted(Long id) {
        Product product= getById(id);
        product.setStatus(StatusProduct.DISCONTINUED);
        productRepository.save(product);

    }
}
