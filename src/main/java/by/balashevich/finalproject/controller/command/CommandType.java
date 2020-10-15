package by.balashevich.finalproject.controller.command;

import by.balashevich.finalproject.controller.command.impl.*;

public enum CommandType {
    LOG_IN_USER(new LoginCommand()),
    LOG_OUT_USER(new LogoutCommand()),
    MOVE_LOGIN_PAGE(new LoginPageCommand()),
    MOVE_REGISTER_PAGE(new RegisterPageCommand()),
    MOVE_HOME_PAGE(new HomePageCommand()),
    MOVE_CARS_PAGE(new CarsPageCommand()),
    MOVE_ORDER_PAGE(new OrderPageCommand()),
    MOVE_USER_OFFICE_PAGE(new UserOfficePageCommand()),
    REGISTER_CLIENT(new RegisterClientCommand()),
    SWITCH_LOCALE(new SwitchLocaleCommand()),
    ACTIVATE_CLIENT(new ActivateClientCommand()),
    FILTER_CARS(new FilterCarsCommand()),
    ORDER_CAR(new OrderCarCommand());

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
