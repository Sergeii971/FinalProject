package by.balashevich.finalproject.model.dao.impl;

import by.balashevich.finalproject.exception.DaoProjectException;
import by.balashevich.finalproject.factory.UserFactory;
import by.balashevich.finalproject.model.entity.Client;
import by.balashevich.finalproject.model.pool.ConnectionPool;
import by.balashevich.finalproject.model.dao.BaseDao;
import by.balashevich.finalproject.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.balashevich.finalproject.util.ParameterKey.*;

public class UserDaoImpl implements BaseDao<User> {
    private static final String EMPTY_VALUE = "";
    private static final String CHANGE_PASSWORD = "UPDATE users SET password = (?) where email =";
    private static final String FIND_BY_EMAIL = "SELECT user_id, email, user_role, first_name, second_name," +
            "driver_license, phone_number, status FROM users WHERE email = ?";
    private static final String FIND_PASSWORD_BY_EMAIL = "SELECT password FROM users WHERE email = ?";
    private static final String FIND_EMAIL = "SELECT email FROM users WHERE email = ?";
    private static final String ADD_CLIENT = "INSERT INTO users(email, password, user_role, first_name, " +
            "second_name, driver_license, phone_number, status)VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public boolean add(Map<String, String> userParameters) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        boolean isClientAdded;
        User.Role role = User.Role.valueOf(userParameters.get(ROLE));
        String addUserQuery = (role == User.Role.CLIENT) ? ADD_CLIENT : null; //todo Manager query

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(addUserQuery)){
            statement.setString(1, userParameters.get(EMAIL));
            statement.setString(2, userParameters.get(PASSWORD));
            statement.setInt(3, role.ordinal());
            if (role == User.Role.CLIENT) {
                statement.setString(4, userParameters.get(FIRST_NAME));
                statement.setString(5, userParameters.get(SECOND_NAME));
                statement.setString(6, userParameters.get(DRIVER_LICENSE));
                statement.setInt(7, Integer.parseInt(userParameters.get(PHONE_NUMBER)));
                statement.setInt(8, Client.Status.valueOf(userParameters.get(STATUS)).ordinal());
            }
            isClientAdded = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoProjectException("Error during Dao adding client into database", e);
        }

        return isClientAdded;
    }

    @Override
    public boolean remove(User user) throws DaoProjectException {
        return false;
    }

    @Override
    public User update(User user) throws DaoProjectException {
        return null;
    }

    @Override
    public User findById(long id) throws DaoProjectException {
        return null;
    }

    public boolean updatePassword(User user, String changingPassword) throws DaoProjectException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        String changePasswordFullQuery = CHANGE_PASSWORD + user.getEmail();
        boolean isPasswordChanged;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(changePasswordFullQuery)) {
            statement.setString(1, changingPassword);
            isPasswordChanged = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoProjectException("Error during changing password in database", e);
        }

        return isPasswordChanged;
    }

    public Optional<User> findByEmail(String targetEmail) throws DaoProjectException {
        Optional<User> targetUser = Optional.empty();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL)) {
            statement.setString(1, targetEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Map<String, Object> userParameters = new HashMap<>();
                userParameters.put(USER_ID, resultSet.getLong(USER_ID));
                userParameters.put(EMAIL, resultSet.getString(EMAIL));
                userParameters.put(ROLE, User.Role.getUserRole(resultSet.getInt(ROLE)));
                userParameters.put(FIRST_NAME, resultSet.getString(FIRST_NAME));
                userParameters.put(SECOND_NAME, resultSet.getString(SECOND_NAME));
                userParameters.put(DRIVER_LICENSE, resultSet.getString(DRIVER_LICENSE));
                userParameters.put(PHONE_NUMBER, resultSet.getLong(PHONE_NUMBER));
                userParameters.put(STATUS, Client.Status.getClientStatus(resultSet.getInt(STATUS)));

                targetUser = Optional.of(UserFactory.createUser(userParameters));
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error during searching user by email", e);
        }

        return targetUser;
    }

    public String findPasswordByEmail(String targetEmail) throws DaoProjectException {
        String userPassword = EMPTY_VALUE;
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PASSWORD_BY_EMAIL)) {
            statement.setString(1, targetEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userPassword = resultSet.getString(PASSWORD);
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error during searching password by email", e);
        }

        return userPassword;
    }

    public String existEmail(String targetEmail) throws DaoProjectException {
        String email = EMPTY_VALUE;
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_EMAIL)) {
            statement.setString(1, targetEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                email = resultSet.getString(EMAIL);
            }
        } catch (SQLException e) {
            throw new DaoProjectException("Error during searching email in database", e);
        }

        return email;
    }
}
