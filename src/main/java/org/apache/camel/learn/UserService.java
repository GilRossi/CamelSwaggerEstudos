package org.apache.camel.learn;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static List<User> users = new ArrayList<>();

//    static {
//        // exemplo inicial (executado uma vez)
//        users.add(new User(1, "totti", "Totti", "Rossi", "Roma"));
//        users.add(new User(2, "paola", "Paola", "Hermosin", "Sevila"));
//    }

    public User getUser(int id) {
        User user = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        if (user == null) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        return user;
    }

    public User updateUser(User user) {
        // Gera ID se não existir
        if (user.getId() == 0) {
            int newId = users.stream().mapToInt(User::getId).max().orElse(0) + 1;
            user.setId(newId);
        }
        users.removeIf(u -> u.getId() == user.getId());
        users.add(user);
        return user;
    }

    public User[] listUsers() {
        return users.toArray(new User[0]);
    }
}