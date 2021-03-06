package by.balashevich.finalproject.model.service.impl;

import by.balashevich.finalproject.exception.DaoProjectException;
import by.balashevich.finalproject.exception.ServiceProjectException;
import by.balashevich.finalproject.model.dao.impl.UserDaoImpl;
import by.balashevich.finalproject.model.entity.Client;
import by.balashevich.finalproject.model.entity.User;
import by.balashevich.finalproject.model.service.UserService;
import by.balashevich.finalproject.util.PasswordEncryption;
import by.balashevich.finalproject.validator.UserValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.balashevich.finalproject.util.ParameterKey.*;

/**
 * The type User service.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    private static final String EMPTY_VALUE = "";
    private static final String PUNCTUATION = "[\\p{Punct}\\p{Space}]";
    /**
     * The User dao.
     */
    private UserDaoImpl userDao = UserDaoImpl.getInstance();

    @Override
    public boolean add(Map<String, String> clientParameters) throws ServiceProjectException {
        boolean isClientAdded = false;

        if (UserValidator.validateClientParameters(clientParameters)) {
            try {
                Map<String, Object> preparedClientParameters = new HashMap<>();
                String encryptedPassword = PasswordEncryption.encryptPassword(clientParameters.get(PASSWORD));
                if (!encryptedPassword.isEmpty()) {
                    String normalizedPhone = clientParameters.get(PHONE_NUMBER).replaceAll(PUNCTUATION, EMPTY_VALUE);
                    long phoneNumber = Long.parseLong(normalizedPhone);
                    preparedClientParameters.put(EMAIL, clientParameters.get(EMAIL).toLowerCase());
                    preparedClientParameters.put(PASSWORD, encryptedPassword);
                    preparedClientParameters.put(ROLE, User.Role.CLIENT);
                    preparedClientParameters.put(FIRST_NAME, clientParameters.get(FIRST_NAME));
                    preparedClientParameters.put(SECOND_NAME, clientParameters.get(SECOND_NAME));
                    preparedClientParameters.put(DRIVER_LICENSE, clientParameters.get(DRIVER_LICENSE));
                    preparedClientParameters.put(PHONE_NUMBER, phoneNumber);
                    preparedClientParameters.put(CLIENT_STATUS, Client.Status.PENDING);
                    isClientAdded = userDao.add(preparedClientParameters);
                }
            } catch (DaoProjectException e) {
                throw new ServiceProjectException(e);
            }
        }

        return isClientAdded;
    }

    @Override
    public boolean activateClient(String email) throws ServiceProjectException {
        boolean isParameterUpdated = false;

        try {
            Client.Status currentStatus = userDao.findStatusByEmail(email);
            if (currentStatus != null) {
                if (currentStatus == Client.Status.PENDING) {
                    isParameterUpdated = userDao.updateClientStatus(email, Client.Status.ACTIVE);
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isParameterUpdated;
    }

    @Override
    public boolean updateClientStatus(Client updatingClient, String statusData) throws ServiceProjectException {
        boolean isStatusUpdated = false;

        try {
            if (UserValidator.validateClientStatus(statusData)) {
                Client.Status status = Client.Status.valueOf(statusData.toUpperCase());
                isStatusUpdated = userDao.updateClientStatus(updatingClient.getEmail(), status);
                if (isStatusUpdated) {
                    updatingClient.setStatus(status);
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isStatusUpdated;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceProjectException {
        Optional<User> targetUser;

        try {
            if (UserValidator.validateEmail(email)) {
                targetUser = userDao.findByEmail(email);
            } else {
                targetUser = Optional.empty();
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetUser;
    }

    @Override
    public List<Client> findClientsByStatus(String clientStatusData) throws ServiceProjectException {
        List<Client> targetClients;

        try {
            if (UserValidator.validateClientStatus(clientStatusData)) {
                targetClients = userDao.findClientsByStatus(Client.Status.valueOf(clientStatusData.toUpperCase()));
            } else {
                targetClients = userDao.findAllClients();
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return targetClients;
    }

    @Override
    public boolean authorizeUser(String email, String password) throws ServiceProjectException {
        boolean isApproved = false;

        try {
            if (UserValidator.validateEmail(email.toLowerCase())) {
                String userPassword = userDao.findPasswordByEmail(email);
                if (userPassword != null && !userPassword.isEmpty()) {
                    String verifyingPassword = PasswordEncryption.encryptPassword(password);
                    if (!verifyingPassword.isEmpty() && userPassword.equals(verifyingPassword)) {
                        isApproved = true;
                    }
                }
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isApproved;
    }

    @Override
    public boolean existUser(String email) throws ServiceProjectException {
        boolean isExist = false;

        try {
            if (UserValidator.validateEmail(email)) {
                isExist = !userDao.existEmail(email).isEmpty();
            }
        } catch (DaoProjectException e) {
            throw new ServiceProjectException(e);
        }

        return isExist;
    }
}
