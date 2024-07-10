package ru.Kirill.tgBot.repository;

import ru.Kirill.tgBot.entity.Product;

import java.util.List;

public interface OrderProductRepositoryExt {
    List<Product> getTopPopularProducts(Integer limit);
}
