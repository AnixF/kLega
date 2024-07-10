package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.Category;
import ru.Kirill.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel =  "products", path = "products")
public interface ProductRepository extends CrudRepository<Product, Long>
{

    List<Product> findByCategory(Category category);
}
