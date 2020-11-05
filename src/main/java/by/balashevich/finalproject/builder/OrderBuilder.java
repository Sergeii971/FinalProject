package by.balashevich.finalproject.builder;

import by.balashevich.finalproject.model.entity.Client;
import by.balashevich.finalproject.model.entity.Order;

import java.time.LocalDate;
import java.util.Map;

import static by.balashevich.finalproject.util.ParameterKey.*;

public class OrderBuilder {
    private OrderBuilder() {
    }

    public static Order buildOrder(Map<String, Object> orderParameters) {
        Order order = new Order();
        order.setOrderId((long) orderParameters.get(ORDER_ID));
        order.setDateFrom((LocalDate) orderParameters.get(DATE_FROM));
        order.setDateTo((LocalDate) orderParameters.get(DATE_TO));
        order.setAmount((int) orderParameters.get(AMOUNT));
        order.setStatus((Order.Status) orderParameters.get(ORDER_STATUS));
        order.setCar(CarBuilder.buildCar(orderParameters));
        order.setClient((Client) UserBuilder.buildUser(orderParameters));

        return order;
    }
}
