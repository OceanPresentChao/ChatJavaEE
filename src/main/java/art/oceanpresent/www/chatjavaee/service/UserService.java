package art.oceanpresent.www.chatjavaee.service;

import art.oceanpresent.www.chatjavaee.ejb.UserEJB;
import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.Tool;

import java.util.List;

public class UserService {
    static UserEJB userEjb = new UserEJB();

    public static User login(String name, String password) {
        List<User> userList = userEjb.findByUsername(name);
        if (userList.isEmpty()) {
            throw new CustomException("User not found");
        }
        User theone = userList.get(0);
        if (!theone.getPassword().equals(Tool.getMD5(password))) {
            throw new CustomException("Wrong password");
        }
        return theone;
    }

    public static User register(String name, String password) {
        if (name == null || password == null) {
            throw new CustomException("Username or password cannot be empty");
        }
        List<User> userList = userEjb.findByUsername(name);
        if (!userList.isEmpty()) {
            throw new CustomException("User already exists");
        }
        User user = new User();
        user.setUsername(name);
        user.setPassword(Tool.getMD5(password));
        User res = userEjb.create(user);
        return res;
    }

    public static User updateUser(Integer id, User user) {
        User u = getUser(id);
        user.setId(u.getId());
        return userEjb.update(user);
    }

    public static User updateUser(String username, User user) {
        User u = getUser(username);
        user.setId(u.getId());
        return userEjb.update(user);
    }

    public static User getUser(Integer id) {
        User u = userEjb.findById(id);
        if (u == null) {
            throw new CustomException("User not found");
        }
        return u;
    }

    public static User getUser(String name) {
        List<User> userList = userEjb.findByUsername(name);
        if (userList.isEmpty()) {
            throw new CustomException("User not found");
        }
        return userList.get(0);
    }

    public static void deleteUser(Integer id) {
        User u = getUser(id);
        userEjb.delete(u);
    }

    public static void deleteUser(String username) {
        User u = getUser(username);
        userEjb.delete(u);
    }

}
