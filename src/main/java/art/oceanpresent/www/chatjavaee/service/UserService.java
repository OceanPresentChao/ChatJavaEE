package art.oceanpresent.www.chatjavaee.service;

import art.oceanpresent.www.chatjavaee.ejb.UserEJB;
import art.oceanpresent.www.chatjavaee.entity.User;

public class UserService {
    static UserEJB userEjb = new UserEJB();
    void login(){

    }

    public static User register(String name, String password){
        User user = new User();
        user.setId(1);
        user.setUsername(name);
        user.setPassword(password);
        User res = userEjb.create(user);
        if(res == null) {
            throw new IllegalArgumentException("User already exists");
        }
        return res;
    }
}
