package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.Kirill.tgBot.entity.OrderProduct;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long>
{

}
