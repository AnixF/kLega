package ru.Kirill.tgBot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.Kirill.tgBot.entity.*;
import ru.Kirill.tgBot.service.AppService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TgBotService {
    private final TelegramBot bot = new TelegramBot("7389058400:AAGCLH0HKlr3qd7-JbEyB0yhcA2VCD5YlLk");

    private final AppService appService;

    private ClientOrder currentOrder;
    private final Map<Long, Long> productSelectionMap = new HashMap<>();
    private final Map<Long, String> clientDataUpdateMap = new HashMap<>();

    @Autowired
    public TgBotService(AppService appService) {
        this.appService = appService;
    }

    @PostConstruct
    private void start() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void processUpdate(Update update) {
        if (update.message() != null) {
            Long chatId = update.message().chat().id();
            String messageText = update.message().text();
            System.out.println("ChatId: " + chatId);
            System.out.println("Message: " + messageText);
            if (messageText != null) {
                Client client = appService.getClientByExternalId(chatId);
                if (client == null) {
                    client = appService.createClient(chatId);
                }

                if (productSelectionMap.containsKey(chatId)) {
                    try {
                        int quantity = Integer.parseInt(messageText);
                        addProductToOrder(chatId, productSelectionMap.remove(chatId), quantity);
                    } catch (NumberFormatException e) {
                        sendReply(chatId, "Пожалуйста, введите корректное количество.");
                    }
                } else if (clientDataUpdateMap.containsKey(chatId)) {
                    updateClientData(chatId, client, messageText);
                } else {
                    switch (messageText) {
                        case "Привет" -> sendReply(chatId, "Привет!");
                        case "Оформить заказ" -> startNewOrder(chatId, client);
                        case "Мои заказы" -> showClientOrders(chatId, client);
                        case "Профиль" -> showClientProfile(chatId, client);
                        case "В основное меню" -> sendReply(chatId, "Вы в основном меню.");
                        default -> sendReply(chatId, "Неизвестная команда, попробуйте встроенную клавиатуру");
                    }
                }
                sendMainMenu(chatId);
            }
        } else if (update.callbackQuery() != null) {
            String callbackData = update.callbackQuery().data();
            Long chatId = update.callbackQuery().message().chat().id();
            System.out.println("CallbackData: " + callbackData);
            if (callbackData.startsWith("category:")) {
                Long categoryId = Long.parseLong(callbackData.split(":")[1]);
                showSubCategoriesOrProducts(chatId, categoryId);
            } else if (callbackData.startsWith("product:")) {
                Long productId = Long.parseLong(callbackData.split(":")[1]);
                askProductQuantity(chatId, productId);
            } else if (callbackData.startsWith("updateName")) {
                clientDataUpdateMap.put(chatId, "name");
                sendReply(chatId, "Введите новое имя:");
            } else if (callbackData.startsWith("updateAddress")) {
                clientDataUpdateMap.put(chatId, "address");
                sendReply(chatId, "Введите новый адрес:");
            } else if (callbackData.startsWith("updatePhone")) {
                clientDataUpdateMap.put(chatId, "phone");
                sendReply(chatId, "Введите новый номер телефона:");
            } else if (callbackData.startsWith("closeOrder:")) {
                Long orderId = Long.parseLong(callbackData.split(":")[1]);
                closeOrder(chatId, orderId);
            }
        }
    }

    private void sendReply(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        bot.execute(message);
    }

    private void sendMainMenu(Long chatId) {
        List<KeyboardButton> categories = appService.getCategoryByParentId(0L)
                .stream()
                .map(category -> new KeyboardButton(category.getName()))
                .collect(Collectors.toList());
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(categories.toArray(KeyboardButton[]::new));
        markup.resizeKeyboard(true);
        markup.addRow(new KeyboardButton("Оформить заказ"));
        markup.addRow(new KeyboardButton("Мои заказы"));
        markup.addRow(new KeyboardButton("Профиль"));
        markup.addRow(new KeyboardButton("В основное меню"));
        SendMessage message = new SendMessage(chatId, "Выберите опцию:").replyMarkup(markup);
        bot.execute(message);
    }

    private void startNewOrder(Long chatId, Client client) {
        currentOrder = appService.createOrder(client);
        showCategories(chatId);
    }

    private void showCategories(Long chatId) {
        List<Category> categories = appService.getCategoryByParentId(null);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        for (Category category : categories) {
            InlineKeyboardButton button = new InlineKeyboardButton(category.getName())
                    .callbackData(String.format("category:%d", category.getId()));
            markup.addRow(button);
        }
        SendMessage message = new SendMessage(chatId, "Выберите категорию:").replyMarkup(markup);
        bot.execute(message);
    }

    private void showSubCategoriesOrProducts(Long chatId, Long categoryId) {
        List<Category> subCategories = appService.getCategoryByParentId(categoryId);
        if (!subCategories.isEmpty()) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            for (Category subCategory : subCategories) {
                InlineKeyboardButton button = new InlineKeyboardButton(subCategory.getName())
                        .callbackData(String.format("category:%d", subCategory.getId()));
                markup.addRow(button);
            }
            SendMessage message = new SendMessage(chatId, "Выберите подкатегорию:").replyMarkup(markup);
            bot.execute(message);
        } else {
            showProducts(chatId, categoryId);
        }
    }

    private void showProducts(Long chatId, Long categoryId) {
        System.out.println("Category ID: " + categoryId);
        List<Product> products = appService.getProductsByCategoryId(categoryId);
        System.out.println("Products: " + products);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        for (Product product : products) {
            InlineKeyboardButton button = new InlineKeyboardButton(String.format("%s. Цена %.2f руб.", product.getName(), product.getPrice()))
                    .callbackData(String.format("product:%d", product.getId()));
            markup.addRow(button);
        }
        SendMessage message = new SendMessage(chatId, "Товары:").replyMarkup(markup);
        bot.execute(message);
    }

    private void askProductQuantity(Long chatId, Long productId) {
        productSelectionMap.put(chatId, productId);
        SendMessage message = new SendMessage(chatId, "Введите количество продукта:");
        bot.execute(message);
    }

    private void addProductToOrder(Long chatId, Long productId, int quantity) {
        if (currentOrder != null) {
            Product product = appService.getProductById(productId);
            if (product != null) {
                appService.addProductToOrder(currentOrder, product, quantity);

                BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
                SendMessage message = new SendMessage(chatId, String.format("Добавлен продукт: %s. Количество: %d. Цена: %.2f руб.", product.getName(), quantity, totalPrice));
                bot.execute(message);
            } else {
                sendReply(chatId, "Продукт не найден.");
            }
        } else {
            sendReply(chatId, "Сначала оформите заказ.");
        }
    }

    private void showClientOrders(Long chatId, Client client) {
        List<ClientOrder> orders = appService.getClientOrders(client);
        if (orders.isEmpty()) {
            sendReply(chatId, "У вас нет заказов.");
        } else {
            for (ClientOrder order : orders) {
                StringBuilder orderDetails = new StringBuilder("Заказ #" + order.getId() + ":\n");
                List<OrderProduct> orderProducts = appService.getOrderProducts(order);
                for (OrderProduct orderProduct : orderProducts) {
                    orderDetails.append(orderProduct.getProduct().getName())
                            .append(" - ")
                            .append(orderProduct.getCountProduct())
                            .append(" шт. - ")
                            .append(orderProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(orderProduct.getCountProduct())))
                            .append(" руб.\n");
                }
                orderDetails.append("Итого: ").append(order.getTotal()).append(" руб.\n");

                InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Закрыть заказ").callbackData("closeOrder:" + order.getId())
                );
                SendMessage message = new SendMessage(chatId, orderDetails.toString()).replyMarkup(markup);
                bot.execute(message);
            }
        }
    }

    private void closeOrder(Long chatId, Long orderId) {
        ClientOrder order = appService.getOrderById(orderId);
        if (order != null) {
            List<OrderProduct> orderProducts = appService.getOrderProducts(order);
            appService.deleteOrderProducts(orderProducts);
            appService.deleteOrder(order);
            sendReply(chatId, "Заказ #" + orderId + " был закрыт.");
        } else {
            sendReply(chatId, "Заказ не найден.");
        }
        showClientOrders(chatId, appService.getClientByExternalId(chatId));
    }

    private void showClientProfile(Long chatId, Client client) {
        String profileDetails = String.format("Ваш профиль:\nИмя: %s\nАдрес: %s\nТелефон: %s",
                client.getFullName(), client.getAddress(), client.getPhoneNumber());
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Изменить имя").callbackData("updateName"),
                new InlineKeyboardButton("Изменить адрес").callbackData("updateAddress"),
                new InlineKeyboardButton("Изменить телефон").callbackData("updatePhone")
        );
        SendMessage message = new SendMessage(chatId, profileDetails).replyMarkup(markup);
        bot.execute(message);
    }

    private void updateClientData(Long chatId, Client client, String newData) {
        String updateType = clientDataUpdateMap.remove(chatId);
        switch (updateType) {
            case "name" -> {
                client.setFullName(newData);
                sendReply(chatId, "Имя обновлено.");
            }
            case "address" -> {
                client.setAddress(newData);
                sendReply(chatId, "Адрес обновлен.");
            }
            case "phone" -> {
                client.setPhoneNumber(newData);
                sendReply(chatId, "Телефон обновлен.");
            }
        }
        appService.updateClient(client);
        showClientProfile(chatId, client);
    }
}
