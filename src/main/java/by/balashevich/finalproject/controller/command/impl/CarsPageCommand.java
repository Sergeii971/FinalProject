package by.balashevich.finalproject.controller.command.impl;

import by.balashevich.finalproject.controller.command.ActionCommand;
import by.balashevich.finalproject.controller.command.PageName;

import javax.servlet.http.HttpServletRequest;

public class CarsPageCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return PageName.CARS.getPath();
    }
}