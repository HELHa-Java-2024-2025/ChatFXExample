package be.fpluquet.chatfx.common.network.commands;


import java.io.Serializable;

public abstract class AbstractCommand implements Serializable {
    public abstract void executeOn(CommandDispatch clientThread);
}
