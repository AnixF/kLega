package ru.Kirill.tgBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.Kirill.tgBot.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Long>
{

}

