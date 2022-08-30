package com.sing.herostory.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    /***用户信息 **/
    private static final Map<Integer, User> userMap = new ConcurrentHashMap<>();

    private UserManager() {

    }

    public static void addUser(User user) {
        if (user != null) {
            userMap.put(user.getUserId(), user);
        }
    }

    public static void removeUser(int userId) {
        userMap.remove(userId);
    }

    public static User getUser(int userId) {
        return userMap.get(userId);
    }

    public static Collection<User> listUser() {
        return userMap.values();
    }
}
