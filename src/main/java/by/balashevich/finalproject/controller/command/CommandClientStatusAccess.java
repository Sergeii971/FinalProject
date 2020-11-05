package by.balashevich.finalproject.controller.command;

import java.util.EnumSet;
import java.util.Set;

import static by.balashevich.finalproject.controller.command.CommandType.*;

public enum CommandClientStatusAccess {
    PENDING(EnumSet.of(MOVE_HOME_PAGE,
            MOVE_CARS_PAGE,
            LOG_OUT_USER,
            SWITCH_LOCALE,
            ACTIVATE_CLIENT,
            FIND_AVAILABLE_CARS,
            PAGINATION)),
    ACTIVE(EnumSet.of(MOVE_HOME_PAGE,
            MOVE_CARS_PAGE,
            MOVE_CAR_CARD_PAGE,
            MOVE_ORDERS_PAGE,
            MOVE_PAYMENT_PAGE,
            LOG_OUT_USER,
            SWITCH_LOCALE,
            FIND_AVAILABLE_CARS,
            ORDER_CAR,
            MAKE_ORDER_PAYMENT,
            UPDATE_ORDER_STATUS,
            PAGINATION)),
    BLOCKED(EnumSet.of(MOVE_HOME_PAGE,
            LOG_OUT_USER,
            SWITCH_LOCALE));

    private Set<CommandType> accessCommands;

    CommandClientStatusAccess(Set<CommandType> accessCommands) {
        this.accessCommands = accessCommands;
    }

    public Set<CommandType> getAccessCommands() {
        return this.accessCommands;
    }
}