package be.fpluquet.chatfx.common.models;

import java.io.Serializable;

public class Message implements Serializable {

    private String content;
    private User sender;

    public Message(String content, User sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", sender=" + sender +
                '}';
    }
}
