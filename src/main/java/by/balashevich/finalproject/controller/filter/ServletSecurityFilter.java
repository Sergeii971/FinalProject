package by.balashevich.finalproject.controller.filter;

import by.balashevich.finalproject.controller.command.*;
import by.balashevich.finalproject.controller.command.impl.EmptyCommand;
import by.balashevich.finalproject.model.entity.User;
import by.balashevich.finalproject.util.ParameterKey;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * The type Servlet security filter.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
@WebFilter(urlPatterns = "/CarBook")
public class ServletSecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User.Role role = (User.Role) session.getAttribute(AttributeKey.USER_ROLE);
        String commandName = request.getParameter(ParameterKey.COMMAND);

        if (role == null) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + PageName.INDEX.getPath());
            return;
        }

        ActionCommand command = CommandProvider.defineCommand(commandName);
        if (command.getClass() != EmptyCommand.class) {
            Set<CommandType> commandTypeSet = switch (role) {
                case GUEST -> CommandRoleAccess.GUEST.getAccessCommands();
                case CLIENT -> CommandRoleAccess.CLIENT.getAccessCommands();
                case ADMIN -> CommandRoleAccess.ADMIN.getAccessCommands();
            };
            if (commandTypeSet != null && !commandTypeSet.contains(CommandType.valueOf(commandName.toUpperCase()))) {
                request.setAttribute(AttributeKey.ACCESS_DENIED, true);
                request.getRequestDispatcher(PageName.NOTIFICATION.getPath()).forward(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) {
    }
}
