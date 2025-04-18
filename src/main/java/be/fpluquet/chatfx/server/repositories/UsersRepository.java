package be.fpluquet.chatfx.server.repositories;

import be.fpluquet.chatfx.common.models.User;

import java.util.ArrayList;
import java.util.List;


// simulation of a database

public class UsersRepository {

    List<User> users;
    int nextId;

    public UsersRepository() {
        this.users = new ArrayList<>();
    }

    public synchronized void addUser(User user) {
        if (!user.hasId()) {
            user.setId(this.nextId++);
        }
        this.users.add(user);
    }


}
