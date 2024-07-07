package ru.Kirill.tgBot.repository;


import org.springframework.data.repository.CrudRepository;
import ru.Kirill.tgBot.entity.Category;


public interface CategoryRepository extends CrudRepository<Category, Long>
{

}
