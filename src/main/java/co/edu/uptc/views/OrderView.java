package co.edu.uptc.views;

import co.edu.uptc.controller.orders.OrderController;
import co.edu.uptc.infrastructure.MongoClientFactory;
import co.edu.uptc.infrastructure.orders.MongoOrderRepository;
import co.edu.uptc.model.Order;
import co.edu.uptc.model.OrderRepository;
import co.edu.uptc.model.Responsible;
import co.edu.uptc.utils.ServletUtils;
import com.mongodb.client.MongoClient;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that represents the view for the order.
 */
@WebServlet("/order")
public class OrderView extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String cashonDeliveryServlet = req.getParameter("isCashOn"); // on para si, y null para no
    String destinationAddress = req.getParameter("destinationAddress");
    String descriptionAddress = req.getParameter("descriptionAddress");
    String remitterName = req.getParameter("remitterName");
    String addresseeName = req.getParameter("addresseeName");
    String price = req.getParameter("price");
    String responsible = req.getParameter("responsible");
    boolean cashonDelivery = false;

    if (descriptionAddress == null || destinationAddress == null || remitterName == null
        || addresseeName == null || price == null || responsible == null) {

      req.setAttribute("errorMessage", "Error: All fields are required");
      ServletUtils.forward(req, resp, "/pages/addorder.jsp");
      return;
    }

    if (cashonDeliveryServlet != null && cashonDeliveryServlet.equals("on")) {
      cashonDelivery = true;
    } else {
      cashonDelivery = false;
    }

    if (!remitterName.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")
        || !addresseeName.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
      req.setAttribute("errorMessageString", "only letters");
      ServletUtils.forward(req, resp, "/pages/addorder.jsp");
      return;
    }

    MongoClient mongoClient =
        MongoClientFactory.createClient("orderView", "mongodb://localhost:27017");
    OrderRepository orderRepository = new MongoOrderRepository(mongoClient);
    OrderController orderController = new OrderController(orderRepository);

    // TODO: You must complete the spaces since you are only jarcodiating.

    Order order = new Order(LocalDate.now(), LocalDate.now(), "", destinationAddress, "",
        addresseeName, remitterName, Integer.parseInt(price), cashonDelivery, descriptionAddress,
        "", new Responsible(responsible, "", "", ""));
    orderController.add(order);

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    ServletUtils.forward(req, resp, "/pages/addorder.jsp");

  }



}