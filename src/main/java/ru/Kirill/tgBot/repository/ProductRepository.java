package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.Kirill.tgBot.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>
{

}
