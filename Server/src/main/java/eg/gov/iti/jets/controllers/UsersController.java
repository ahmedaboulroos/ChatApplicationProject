package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.implementations.UserDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.views.models.UserViewModel;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class UsersController {

    UserDao userDao = UserDaoImpl.getInstance();
    List<UserViewModel> userViewModelList;

    public List<UserViewModel> getAllUsers() throws RemoteException {
        userViewModelList = new ArrayList<>();
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userViewModelList.add(new UserViewModel(user.getUserId(), user.getPhoneNumber(),
                    user.getUsername(), user.getPassword(), user.getEmail(),
                    user.getCountry(), user.getBio(), user.isCurrentlyLoggedIn()));
            //System.out.println(user.toString());
        }
        return userViewModelList;

    }
}
