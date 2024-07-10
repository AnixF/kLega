package ru.Kirill.tgBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Kirill.tgBot.entity.Category;
import ru.Kirill.tgBot.entity.Client;
import ru.Kirill.tgBot.entity.ClientOrder;
import ru.Kirill.tgBot.entity.Product;
import ru.Kirill.tgBot.repository.*;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppService  implements EntitiesService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
       return productRepository.findByCategory(category);
    }

    @Override
    public List<ClientOrder> getClientOrders(Long id) {
        Client client = clientRepository.findById(id).orElseThrow();
        return clientOrderRepository.findByClient(client);
    }

    @Override
    public List<Product> getClientProducts(Long id) {
    List<ClientOrder> clientOrders = getClientOrders(id);
        return orderProductRepository.findProducts(clientOrders);
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit) {
        List<Product> topPopularProducts = orderProductRepository.getTopPopularProducts();
        if (topPopularProducts.size() > limit) {
        topPopularProducts = topPopularProducts.subList(0,limit-1);}
        return topPopularProducts;
    }

    @Override
    public List<Client> searchClientsByName(String name) {
        return EntitiesService.super.searchClientsByName(name);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return EntitiesService.super.searchProductsByName(name);
    }
}

