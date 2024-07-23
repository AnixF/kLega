package ru.Kirill.tgBot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.ClientOrder;
import ru.Kirill.tgBot.entity.OrderProduct;
import ru.Kirill.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel =  "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long>,  OrderProductRepositoryExt
{
    @Query("SELECT op.product FROM " +
            "OrderProduct op JOIN " +
            "op.clientOrder co WHERE " +
            "co.client.id = :id")
    List<Product> findProductByClientId(Long id);

    List<Product> getTopPopularProducts(Integer limit);

    List<OrderProduct> findByClientOrder(ClientOrder order);
}
