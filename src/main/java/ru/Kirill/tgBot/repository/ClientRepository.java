package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.Client;

@RepositoryRestResource(collectionResourceRel =  "clients", path = "clients")
public interface ClientRepository extends CrudRepository<Client, Long>
{

}

