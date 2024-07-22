package ru.Kirill.tgBot.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ClientOrder
{

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        CLOSED;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Client client;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
