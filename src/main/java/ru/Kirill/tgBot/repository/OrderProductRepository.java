package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.OrderProduct;
@RepositoryRestResource(collectionResourceRel =  "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long>
{

}
