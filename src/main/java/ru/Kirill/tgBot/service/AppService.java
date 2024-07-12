package ru.Kirill.tgBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Kirill.tgBot.entity.Category;
import ru.Kirill.tgBot.entity.Client;
import ru.Kirill.tgBot.entity.ClientOrder;
import ru.Kirill.tgBot.entity.Product;
import ru.Kirill.tgBot.entity.OrderProduct;
import ru.Kirill.tgBot.repository.*;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AppService implements EntitiesService {
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
        return productRepository.findProductByCategoryId(id);
    }

    @Override
    public List<ClientOrder> getClientOrders(Long id) {
        return clientOrderRepository.findOrderByClientId(id);
    }

    @Override
    public List<Product> getClientProducts(Long id) {
        return orderProductRepository.findProductByClientId(id);
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit) {
        return orderProductRepository.getTopPopularProducts(limit);
    }

    @Override
    public List<Category> getCategoryByParentId(Long id) {
        return categoryRepository.findCategoryByParentId(id);
    }

    @Override
    public Client getClientByExternalId(Long externalId) {
        return clientRepository.getClientByExternalId(externalId);
    }

    public Client createClient(Long externalId) {
        Client client = new Client();
        client.setExternalId(externalId);
        client.setAddress("");
        client.setFullName("");
        client.setPhoneNumber("");
        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public ClientOrder createOrder(Client client) {
        ClientOrder order = new ClientOrder();
        order.setStatus(1);
        order.setTotal(BigDecimal.valueOf(0));
        order.setClient(client);
        return clientOrderRepository.save(order);
    }

    public OrderProduct addProductToOrder(ClientOrder order, Product product, int quantity) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setClientOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setCountProduct(quantity);
        orderProductRepository.save(orderProduct);

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        order.setTotal(order.getTotal().add(totalPrice));
        clientOrderRepository.save(order);

        return orderProduct;
    }

    public List<ClientOrder> getClientOrders(Client client) {
        return clientOrderRepository.findByClient(client);
    }

    public List<OrderProduct> getOrderProducts(ClientOrder order) {
        return orderProductRepository.findByClientOrder(order);
    }

    public void deleteOrderProducts(List<OrderProduct> orderProducts) {
        orderProductRepository.deleteAll(orderProducts);
    }

    public void deleteOrder(ClientOrder order) {
        clientOrderRepository.delete(order);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public ClientOrder getOrderById(Long orderId) {
        return clientOrderRepository.findById(orderId).orElse(null);
    }
}
