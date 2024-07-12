package ru.Kirill.tgBot.repository;


import com.pengrad.telegrambot.model.request.KeyboardButton;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.Category;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(collectionResourceRel =  "categories", path = "categories")
public interface CategoryRepository extends CrudRepository<Category, Long>
{


    List<Category> findCategoryByParentId(Long id);
}
