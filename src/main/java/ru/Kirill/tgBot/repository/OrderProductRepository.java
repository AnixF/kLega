package ru.Kirill.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.OrderProduct;

@RepositoryRestResource(collectionResourceRel = "orderProduct", path = "orderProduct")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>
{

}
