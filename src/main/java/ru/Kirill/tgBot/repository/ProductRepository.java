package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.Product;
@RepositoryRestResource(collectionResourceRel =  "products", path = "products")
public interface ProductRepository extends CrudRepository<Product, Long>
{

}
