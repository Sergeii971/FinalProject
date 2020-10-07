package by.balashevich.finalproject.controller.command.impl;

import by.balashevich.finalproject.controller.SessionRequestContent;
import by.balashevich.finalproject.controller.command.ActionCommand;
import by.balashevich.finalproject.controller.command.PageName;

import javax.servlet.http.HttpSession;

import static by.balashevich.finalproject.controller.command.AttributeKey.*;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent requestContent) {
        HttpSession session = requestContent.getSession();
        session.setAttribute(USER, null);

        return PageName.HOME.getPath();
    }
}
