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

    //  @Query("select op.product from OrderProduct op group by op.product order by count(op.product) asc")

   // @Query(" select op.product from OrderProduct op group by op.product order by sum(op.countProduct) desc")
    @Query("select op.product from OrderProduct op group by op.product order by sum(op.countProduct) desc")
    List<Product> getTopPopularProducts();
    }
