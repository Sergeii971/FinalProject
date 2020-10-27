package by.balashevich.finalproject.controller.command.impl;

import by.balashevich.finalproject.controller.command.ActionCommand;
import by.balashevich.finalproject.controller.command.AttributeKey;
import by.balashevich.finalproject.controller.command.impl.pagecommand.PageName;
import by.balashevich.finalproject.exception.ServiceProjectException;
import by.balashevich.finalproject.model.entity.Car;
import by.balashevich.finalproject.model.service.CarService;
import by.balashevich.finalproject.model.service.impl.CarServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static by.balashevich.finalproject.util.ParameterKey.*;

public class UpdateCarPropertyCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        CarService carService = new CarServiceImpl();
        Map<String, String> carParameters = new HashMap<>();
        carParameters.put(RENT_COST, request.getParameter(RENT_COST));
        carParameters.put(CAR_AVAILABLE, request.getParameter(CAR_AVAILABLE));
        int carIndex = Integer.parseInt(request.getParameter(CAR_INDEX));
        HttpSession session = request.getSession();
        Car updatingCar = ((ArrayList<Car>) session.getAttribute(AttributeKey.CAR_LIST)).get(carIndex);
        String page;

        try {
            if (carService.updateCar(updatingCar, carParameters)){
                request.setAttribute(AttributeKey.CAR_UPDATED,true);
            } else{
                session.removeAttribute(AttributeKey.CAR_LIST);
                request.setAttribute(AttributeKey.CAR_UPDATED,false);
            }
            page = PageName.ADMIN_CARS.getPath();
        } catch (ServiceProjectException e) {
            session.removeAttribute(AttributeKey.CAR_LIST);
            logger.log(Level.ERROR, "error while updating car", e); // FIXME: 19.10.2020 handle exception
            page = PageName.ERROR_500.getPath();
        }

        return page;
    }
}
