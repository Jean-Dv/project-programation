package co.edu.uptc.controller.orders;

import co.edu.uptc.model.Order;
import co.edu.uptc.model.OrderRepository;
import java.util.List;

/**
 * Class that represents the controller for the order.
 */
public class OrderController {
  private OrderRepository repository;

  public OrderController(OrderRepository repository) {
    this.repository = repository;
  }

  /**
   * Method that allows to create a new order.
   *
   * @param order - Order to be created.
   */
  public void add(Order order) {
    this.repository.save(order);
  }

  /**
   * Method that allows to find all the orders.
   *
   * @return List of orders.
   */
  public List<Order> getAll() {
    return this.repository.findAll();
  }
}
