package be.fpluquet.chatfx.common.models;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int id = -1;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean hasId() {
        return id != -1;
    }

    public int getId() {
        return id;
    }

    public void setName(String newUsername) {
        this.name = newUsername;
    }
}
