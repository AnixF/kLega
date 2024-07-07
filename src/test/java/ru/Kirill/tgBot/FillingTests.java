package ru.Kirill.tgBot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Kirill.tgBot.entity.Category;
import ru.Kirill.tgBot.entity.Product;
import ru.Kirill.tgBot.repository.*;

import java.math.BigDecimal;

@SpringBootTest
class FillingTests
{


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void createMenu()
    {
        //Main categories
        Category pizza = new Category();
        pizza.setName("Пицца");
        categoryRepository.save(pizza);

        Category rolls = new Category();
        rolls.setName("Роллы");
        categoryRepository.save(rolls);

        Category burgers = new Category();
        burgers.setName("Бургеры");
        categoryRepository.save(burgers);

        Category drinks = new Category();
        drinks.setName("Напитки");
        categoryRepository.save(drinks);

        //Child categories for rolls
        Category classicalRolls = new Category();
        classicalRolls.setName("Классические роллы");
        classicalRolls.setParent(rolls);
        categoryRepository.save(classicalRolls);

        Category bakedRolls = new Category();
        bakedRolls.setName("Запечённые роллы");
        bakedRolls.setParent(rolls);
        categoryRepository.save(bakedRolls);

        Category sweetRolls = new Category();
        sweetRolls.setName("Сладкие роллы");
        sweetRolls.setParent(rolls);
        categoryRepository.save(sweetRolls);

        Category sets = new Category();
        sets.setName("Наборы");
        sets.setParent(rolls);
        categoryRepository.save(sets);

        //Child categories for burgers
        Category classicalBurgers = new Category();
        classicalBurgers.setName("Классические бургеры");
        classicalBurgers.setParent(burgers);
        categoryRepository.save(classicalBurgers);

        Category spicyBurgers = new Category();
        spicyBurgers.setName("Острые бургеры");
        spicyBurgers.setParent(burgers);
        categoryRepository.save(spicyBurgers);

        //Child categories for drinks
        Category sodas = new Category();
        sodas.setName("Газированные напитки");
        sodas.setParent(drinks);
        categoryRepository.save(sodas);

        Category energyDrinks = new Category();
        energyDrinks.setName("Энергетические напитки");
        energyDrinks.setParent(drinks);
        categoryRepository.save(energyDrinks);

        Category juices = new Category();
        juices.setName("Соки");
        juices.setParent(drinks);
        categoryRepository.save(juices);

        Category other = new Category();
        other.setName("Другие");
        other.setParent(drinks);
        categoryRepository.save(other);

        // Adding products
        addProductsToCategory(pizza, "Пицца Маргарита", "Классическая пицца с томатами и сыром.", 450.00);
        addProductsToCategory(pizza, "Пицца Пепперони", "Острая пицца с пепперони и сыром.", 500.00);
        addProductsToCategory(pizza, "Пицца Четыре Сыра", "Пицца с четырьмя видами сыра.", 550.00);

        addProductsToCategory(classicalRolls, "Филадельфия", "Классический ролл с лососем и сливочным сыром.", 300.00);
        addProductsToCategory(classicalRolls, "Калифорния", "Ролл с крабом и авокадо.", 350.00);
        addProductsToCategory(classicalRolls, "Спайси ролл", "Ролл с острым соусом.", 320.00);

        addProductsToCategory(bakedRolls, "Запечённый ролл с угрем", "Ролл с угрем и соусом унаги.", 400.00);
        addProductsToCategory(bakedRolls, "Запечённый ролл с лососем", "Ролл с лососем и сыром.", 420.00);
        addProductsToCategory(bakedRolls, "Запечённый ролл с тунцом", "Ролл с тунцом и сыром.", 410.00);

        addProductsToCategory(sweetRolls, "Сладкий ролл с бананом", "Ролл с бананом и шоколадом.", 250.00);
        addProductsToCategory(sweetRolls, "Сладкий ролл с клубникой", "Ролл с клубникой и сливочным сыром.", 260.00);
        addProductsToCategory(sweetRolls, "Сладкий ролл с манго", "Ролл с манго и кокосом.", 270.00);

        addProductsToCategory(sets, "Сет Ассорти", "Набор из различных роллов.", 1000.00);
        addProductsToCategory(sets, "Сет Филадельфия", "Набор роллов Филадельфия.", 1200.00);
        addProductsToCategory(sets, "Сет Веган", "Набор вегетарианских роллов.", 900.00);

        addProductsToCategory(classicalBurgers, "Классический бургер", "Бургер с говядиной и сыром.", 300.00);
        addProductsToCategory(classicalBurgers, "Чизбургер", "Бургер с говядиной и сыром чеддер.", 320.00);
        addProductsToCategory(classicalBurgers, "Гамбургер", "Бургер с говядиной.", 280.00);

        addProductsToCategory(spicyBurgers, "Острый бургер", "Бургер с острым соусом и перцем халапеньо.", 350.00);
        addProductsToCategory(spicyBurgers, "Острый чили бургер", "Бургер с острым чили соусом.", 360.00);
        addProductsToCategory(spicyBurgers, "Мексиканский бургер", "Бургер с мексиканскими специями.", 370.00);

        addProductsToCategory(sodas, "Кола", "Классическая газировка.", 100.00);
        addProductsToCategory(sodas, "Спрайт", "Лимонно-лаймовая газировка.", 100.00);
        addProductsToCategory(sodas, "Фанта", "Апельсиновая газировка.", 100.00);

        addProductsToCategory(energyDrinks, "Ред Булл", "Энергетический напиток.", 150.00);
        addProductsToCategory(energyDrinks, "Монстр", "Энергетический напиток.", 160.00);
        addProductsToCategory(energyDrinks, "Бёрн", "Энергетический напиток.", 140.00);

        addProductsToCategory(juices, "Апельсиновый сок", "Свежевыжатый апельсиновый сок.", 120.00);
        addProductsToCategory(juices, "Яблочный сок", "Свежевыжатый яблочный сок.", 110.00);
        addProductsToCategory(juices, "Вишнёвый сок", "Свежевыжатый вишнёвый сок.", 130.00);

        addProductsToCategory(other, "Минеральная вода", "Негазированная минеральная вода.", 50.00);
        addProductsToCategory(other, "Газированная вода", "Газированная минеральная вода.", 50.00);
        addProductsToCategory(other, "Чай в бутылке", "Охлаждённый чай.", 80.00);
    }

    private void addProductsToCategory(Category category, String name, String description, Double price)
    {
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(BigDecimal.valueOf(price));
        productRepository.save(product);
    }
}






