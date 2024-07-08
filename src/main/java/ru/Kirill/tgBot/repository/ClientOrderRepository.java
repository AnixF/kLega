package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.ClientOrder;

@RepositoryRestResource(collectionResourceRel =  "clientOrders", path = "clientOrders")
public interface ClientOrderRepository extends CrudRepository<ClientOrder, Long>
{

}