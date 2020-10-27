package by.balashevich.finalproject.controller.command.impl.pagecommand;

import by.balashevich.finalproject.controller.command.ActionCommand;
import by.balashevich.finalproject.controller.command.AttributeKey;
import by.balashevich.finalproject.exception.ServiceProjectException;
import by.balashevich.finalproject.model.entity.Order;
import by.balashevich.finalproject.model.entity.User;
import by.balashevich.finalproject.model.service.OrderService;
import by.balashevich.finalproject.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OrdersPageCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AttributeKey.USER);
        String page = switch (user.getRole()){
            case CLIENT -> PageName.CLIENT_ORDERS.getPath();
            case ADMIN ->  PageName.ADMIN_ORDERS.getPath();
        };

        if(user.getRole() == User.Role.CLIENT){
            OrderService orderService = new OrderServiceImpl();
            try{
                List<Order> orderList = orderService.findClientOrders(user.getUserId());
                session.setAttribute(AttributeKey.ORDER_LIST, orderList);
                if (orderList == null || orderList.isEmpty()) {
                    request.setAttribute(AttributeKey.ORDERS_FOUND, false);
                }
            } catch (ServiceProjectException e){
                logger.log(Level.ERROR, "error while searching client orders", e);
                page = PageName.ERROR_500.getPath();
            }
        }

        return page;
    }
}