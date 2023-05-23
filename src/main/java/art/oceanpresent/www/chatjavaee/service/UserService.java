package art.oceanpresent.www.chatjavaee.service;

import art.oceanpresent.www.chatjavaee.ejb.UserEJB;
import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.util.CustomException;

import java.util.List;

public class UserService {
    static UserEJB userEjb = new UserEJB();

    public static void login(String name, String password) {
        List<User> userList = userEjb.findByUsername(name);
        if (userList.isEmpty()) {
            throw new CustomException("User not found");
        }
        User theone = userList.get(0);
        if (!theone.getPassword().equals(password)) {
            throw new CustomException("Wrong password");
        }
    }

    public static User register(String name, String password) {
        List<User> userList = userEjb.findByUsername(name);
        if (!userList.isEmpty()) {
            throw new CustomException("User already exists");
        }
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        User res = userEjb.create(user);
        return res;
    }
}
