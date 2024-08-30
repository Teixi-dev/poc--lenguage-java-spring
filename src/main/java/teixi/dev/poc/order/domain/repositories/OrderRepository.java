package teixi.dev.poc.order.domain.repositories;

import teixi.dev.poc.order.domain.exceptions.OrderNotFoundException;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.shared.domain.models.ClientCode;
import teixi.dev.poc.shared.domain.models.OrderCode;

import java.util.List;

public interface OrderRepository {
    public List<Order> searchByClientCode(ClientCode clientCode);

    public Order find(OrderCode orderCode) throws OrderNotFoundException;

    public void save(Order order);
}
