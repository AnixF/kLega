package ru.Kirill.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Kirill.tgBot.entity.Category;

@RepositoryRestResource(collectionResourceRel = "category", path = "category")
public interface CategoryRepository extends JpaRepository<Category, Long>
{

}
