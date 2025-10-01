package com.nter.final_project.persistence.repository.impl;

import com.nter.final_project.persistence.entity.Product;
import com.nter.final_project.persistence.repository.ProductCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl implements ProductCriteriaRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public Page<Product> findProductByCustomParam(Map<String, String> params, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Product> dataQuery = cb.createQuery(Product.class);
        Root<Product> dataRoot = dataQuery.from(Product.class);
        List<Predicate> dataPredicates = new ArrayList<>();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        List<Predicate> countPredicates = new ArrayList<>();

        params.forEach((key, value) -> {
            if (value == null || value.isEmpty() || key.equals("orderby")) {
                return;
            }
            switch (key) {
                case "name":
                    dataPredicates.add(cb.like(dataRoot.get(key), "%" + value + "%"));
                    countPredicates.add(cb.like(countRoot.get(key), "%" + value + "%"));
                    break;
                case "pricemin":
                    dataPredicates.add(cb.greaterThanOrEqualTo(dataRoot.get(key), value));
                    dataPredicates.add(cb.greaterThanOrEqualTo(dataRoot.get(key), value));
                    break;
                case "pricemax":
                    dataPredicates.add(cb.lessThanOrEqualTo(dataRoot.get(key), value));
                    dataPredicates.add(cb.lessThanOrEqualTo(dataRoot.get(key), value));
                case "status":
                case "created":
                    dataPredicates.add(cb.equal(dataRoot.get(key), value));
                    countPredicates.add(cb.equal(countRoot.get(key), value));
                    break;
            }
        });
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        if (total == 0) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        dataQuery.where(dataPredicates.toArray(new Predicate[0]));

        String orderByField = params.get("orderBy");
        if (orderByField != null && !orderByField.isEmpty()) {
            dataQuery.orderBy(cb.asc(dataRoot.get(orderByField)));
        }

        List<Product> personList = em.createQuery(dataQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(personList, pageable, total);
    }
}
