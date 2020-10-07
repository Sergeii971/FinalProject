package by.balashevich.finalproject.controller;

import by.balashevich.finalproject.controller.command.ActionCommand;
import by.balashevich.finalproject.controller.command.CommandProvider;
import by.balashevich.finalproject.util.ParameterKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.balashevich.finalproject.controller.command.AttributeKey.*;

@WebServlet(urlPatterns = "/process_controller")
public class ProcessController extends HttpServlet {
    Logger logger = LogManager.getLogger();
    private static final String COMMAND_PARAMETER = "command";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionCommand command = CommandProvider.defineCommand(request.getParameter(COMMAND_PARAMETER));
        SessionRequestContent sessionRequestContent = new SessionRequestContent(request);
        String page = command.execute(sessionRequestContent);
        HttpSession session = sessionRequestContent.getSession();

        session.setAttribute(CURRENT_PAGE, page);
        sessionRequestContent.restoreRequest(request);

        request.getRequestDispatcher(page).forward(request, response);
    }
}
