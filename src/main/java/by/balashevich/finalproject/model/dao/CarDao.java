package by.balashevich.finalproject.model.dao;

import by.balashevich.finalproject.exception.DaoProjectException;
import by.balashevich.finalproject.model.entity.Car;

import java.util.List;
import java.util.Map;

/**
 * The interface Car dao.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public interface CarDao extends BaseDao<Car> {

    /**
     * Find available order cars list.
     *
     * @param carParameters the car parameters
     * @return the list
     * @throws DaoProjectException the dao project exception
     */
    List<Car> findAvailableOrderCars(Map<String, Object> carParameters) throws DaoProjectException;

    /**
     * Find cars by parameters list.
     *
     * @param carParameters the car parameters
     * @return the list
     * @throws DaoProjectException the dao project exception
     */
    List<Car> findCarsByParameters(Map<String, Object> carParameters) throws DaoProjectException;
}
