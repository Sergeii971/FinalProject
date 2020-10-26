package by.balashevich.finalproject.controller.command;

import by.balashevich.finalproject.controller.command.impl.*;
import by.balashevich.finalproject.controller.command.impl.pagecommand.*;

public enum CommandType {
    MOVE_LOGIN_PAGE(new LoginPageCommand()),
    MOVE_REGISTER_PAGE(new RegisterPageCommand()),
    MOVE_HOME_PAGE(new HomePageCommand()),
    MOVE_CARS_PAGE(new CarsPageCommand()),
    MOVE_ORDER_PAGE(new CarCardPageCommand()),
    MOVE_USERS_PAGE(new UsersPageCommand()),
    MOVE_ORDERS_PAGE(new OrdersPageCommand()),
    MOVE_CREATE_CARS_PAGE(new CreateCarPageCommand()),
    LOG_IN_USER(new LoginCommand()),
    LOG_OUT_USER(new LogoutCommand()),
    REGISTER_CLIENT(new RegisterClientCommand()),
    SWITCH_LOCALE(new SwitchLocaleCommand()),
    ACTIVATE_CLIENT(new ActivateClientCommand()),
    FIND_AVAILABLE_CARS(new FindAvailableCarsCommand()),
    FILTER_CARS(new FilterCarsCommand()),
    ORDER_CAR(new OrderCarCommand()),
    UPDATE_CAR_PROPERTY(new UpdateCarPropertyCommand()),
    ADD_CAR(new AddCarCommand()),
    FILTER_ORDERS(new FilterOrdersCommand()),
    FILTER_USERS(new FilterUsersCommand()),
    DECLINE_ORDER(new DeclineOrderCommand()),
    UPDATE_CLIENT_STATUS(new UpdateClientStatusCommand()),
    UPDATE_ORDER_STATUS(new UpdateOrderStatusCommand());

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
