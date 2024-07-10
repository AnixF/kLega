package ru.Kirill.tgBot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.ClientOrder;
import ru.Kirill.tgBot.entity.OrderProduct;
import ru.Kirill.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel =  "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long>
{
    @Query("select op.product from OrderProduct op where op.clientOrder in :clientOrders")
    List<Product> findProducts(List<ClientOrder> clientOrders);


    @Query(value = "SELECT op.product, COUNT(op.product) AS product_count\n" +
            "FROM ORDER_PRODUCT op\n" +
            "GROUP BY op.product\n" +
            "ORDER BY product_count DESC\n" +
            "LIMIT ?1", nativeQuery = true)

    List<Product> getTopPopularProducts(Integer limit);
}
