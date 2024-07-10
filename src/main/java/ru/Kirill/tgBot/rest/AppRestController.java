package ru.Kirill.tgBot.rest;

import org.springframework.web.bind.annotation.*;
import ru.Kirill.tgBot.entity.ClientOrder;
import ru.Kirill.tgBot.entity.Product;
import ru.Kirill.tgBot.service.AppService;

import java.util.List;

@RestController
public class AppRestController {
    private final AppService appService;

    public AppRestController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping(path = "/rest/products/search")
    List<Product> getProductsByCategoryId(@RequestParam(name = "categoryId") Long id) {
        return appService.getProductsByCategoryId(id);

    }

    @GetMapping(path = "/rest/clients/{id}/orders")
    List<ClientOrder> getClientOrders(@PathVariable Long id)
    {
        return appService.getClientOrders(id);
    }

    @GetMapping(path = "/rest/clients/{id}/products")
    List<Product> getClientProducts(@PathVariable Long id)
    {
        return appService.getClientProducts(id);
    }
    @GetMapping(path = "rest/products/popular")
    List<Product> getTopPopularProducts(@RequestParam(name = "limit") Integer limit)
    {
        return appService.getTopPopularProducts(limit);
    }
}

/**
 * Получить указанное кол-во самых популярных (наибольшее
 * количество штук в заказах) товаров среди клиентов
 * @param limit максимальное кол-во товаров
 */