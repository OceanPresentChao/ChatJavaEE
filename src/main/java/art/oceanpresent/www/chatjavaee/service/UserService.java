package art.oceanpresent.www.chatjavaee.service;

import art.oceanpresent.www.chatjavaee.jpa.UserJPA;
import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.Tool;

import java.util.List;

public class UserService {
    static UserJPA userJPA = new UserJPA();

    public static User login(String name, String password) {
        List<User> userList = userJPA.findByUsername(name);
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
        List<User> userList = userJPA.findByUsername(name);
        if (!userList.isEmpty()) {
            throw new CustomException("User already exists");
        }
        User user = new User();
        user.setUsername(name);
        user.setPassword(Tool.getMD5(password));
        User res = userJPA.create(user);
        return res;
    }

    public static User updateUser(Integer id, User user) {
        User u = getUser(id);
        user.setId(u.getId());
        return userJPA.update(user);
    }

    public static User updateUser(String username, User user) {
        User u = getUser(username);
        user.setId(u.getId());
        return userJPA.update(user);
    }

    public static User getUser(Integer id) {
        User u = userJPA.findById(id);
        if (u == null) {
            throw new CustomException("User not found");
        }
        return u;
    }

    public static User getUser(String name) {
        List<User> userList = userJPA.findByUsername(name);
        if (userList.isEmpty()) {
            throw new CustomException("User not found");
        }
        return userList.get(0);
    }

    public static void deleteUser(Integer id) {
        userJPA.delete(id);
    }

}
