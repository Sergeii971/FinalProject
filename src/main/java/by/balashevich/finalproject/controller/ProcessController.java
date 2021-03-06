package by.balashevich.finalproject.controller;

import by.balashevich.finalproject.controller.command.ActionCommand;
import by.balashevich.finalproject.controller.command.CommandProvider;
import by.balashevich.finalproject.model.pool.ConnectionPool;
import by.balashevich.finalproject.util.ParameterKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.balashevich.finalproject.controller.command.AttributeKey.*;

/**
 * The type Process controller.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
@WebServlet(urlPatterns = "/process_controller")
public class ProcessController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process request.
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionCommand command = CommandProvider.defineCommand(request.getParameter(ParameterKey.COMMAND));
        Router router = command.execute(request);
        String page = router.getPage();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, page);

        if (router.getTransition() == Router.Transition.FORWARD) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            response.sendRedirect(page);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
