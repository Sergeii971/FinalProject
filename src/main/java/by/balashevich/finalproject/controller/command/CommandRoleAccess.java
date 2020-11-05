package by.balashevich.finalproject.controller.command;

import java.util.EnumSet;
import java.util.Set;

import static by.balashevich.finalproject.controller.command.CommandType.*;

public enum CommandRoleAccess {
    GUEST(EnumSet.of(MOVE_LOGIN_PAGE,
            MOVE_REGISTER_PAGE,
            LOG_IN_USER,
            REGISTER_CLIENT,
            MOVE_HOME_PAGE,
            MOVE_CARS_PAGE,
            SWITCH_LOCALE,
            ACTIVATE_CLIENT,
            FIND_AVAILABLE_CARS,
            PAGINATION)),

    CLIENT(EnumSet.of(MOVE_HOME_PAGE,
            MOVE_CARS_PAGE,
            MOVE_CAR_CARD_PAGE,
            MOVE_ORDERS_PAGE,
            MOVE_PAYMENT_PAGE,
            LOG_OUT_USER,
            SWITCH_LOCALE,
            ACTIVATE_CLIENT,
            FIND_AVAILABLE_CARS,
            ORDER_CAR,
            MAKE_ORDER_PAYMENT,
            UPDATE_ORDER_STATUS,
            PAGINATION)),

    ADMIN(EnumSet.of(MOVE_HOME_PAGE,
            MOVE_CARS_PAGE,
            MOVE_USERS_PAGE,
            MOVE_ORDERS_PAGE,
            MOVE_CREATE_CAR_PAGE,
            LOG_OUT_USER,
            SWITCH_LOCALE,
            FILTER_CARS,
            UPDATE_CAR_PROPERTY,
            ADD_CAR,
            FILTER_ORDERS,
            FILTER_USERS,
            DECLINE_ORDER,
            UPDATE_CLIENT_STATUS,
            UPDATE_ORDER_STATUS,
            PAGINATION));

    private Set<CommandType> accessCommands;

    CommandRoleAccess(Set<CommandType> accessCommands){
        this.accessCommands = accessCommands;
    }

    public Set<CommandType> getAccessCommands(){
        return this.accessCommands;
    }
}