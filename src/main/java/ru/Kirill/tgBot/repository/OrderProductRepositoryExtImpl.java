package ru.Kirill.tgBot.repository;

import jakarta.persistence.EntityManager;
import ru.Kirill.tgBot.entity.Product;

import java.util.List;

public class OrderProductRepositoryExtImpl implements OrderProductRepositoryExt {
    private final EntityManager entityManager;

    public OrderProductRepositoryExtImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit) {
        return entityManager.createQuery("select op.product" +
                " from OrderProduct op " +
                "group by op.product " +
                "order by sum(op.countProduct) desc", Product.class)
                .setMaxResults(limit)
                .getResultList();
    }
}
