package ru.Kirill.tgBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Kirill.tgBot.entity.ClientOrder;
import ru.Kirill.tgBot.entity.Product;
import ru.Kirill.tgBot.repository.*;


import java.util.List;

@Service
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
    public List<Product> getProductsByCategoryId(Long id)
    {
        return productRepository.findProductByCategoryId(id);
    }

    @Override
    public List<ClientOrder> getClientOrders(Long id)
    {
        return clientOrderRepository.findOrderByClientId(id);
    }

    @Override
    public List<Product> getClientProducts(Long id)
    {
        return orderProductRepository.findProductByClientId(id);
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit)
    {
        return orderProductRepository.getTopPopularProducts(limit);
    }
}

