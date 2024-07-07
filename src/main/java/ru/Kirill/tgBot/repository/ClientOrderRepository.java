package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.Kirill.tgBot.entity.ClientOrder;

public interface ClientOrderRepository extends CrudRepository<ClientOrder, Long>
{

}