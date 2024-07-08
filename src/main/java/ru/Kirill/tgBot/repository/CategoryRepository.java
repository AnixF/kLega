package ru.Kirill.tgBot.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.Category;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long>
{

}
