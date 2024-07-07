package ru.Kirill.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.ClientOrder;

@RepositoryRestResource(collectionResourceRel = "clientOrder", path = "clientOrder")
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long>
{

}