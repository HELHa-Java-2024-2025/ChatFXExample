package be.fpluquet.chatfx.common.models;

import java.io.Serializable;

public class User implements Serializable {
    private String pseudo;
    private int id = -1;

    public User(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean hasId() {
        return id != -1;
    }

    public Object getId() {
        return id;
    }
}
