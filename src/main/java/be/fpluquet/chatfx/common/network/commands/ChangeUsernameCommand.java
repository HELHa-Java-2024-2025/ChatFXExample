package be.fpluquet.chatfx.common.network.commands;

import be.fpluquet.chatfx.server.ClientThread;

public class ChangeUsernameCommand extends AbstractCommand {

    private final String newUsername;
    private final String oldUsername;
    private final int userId;

    public ChangeUsernameCommand(String newUsername, String oldUsername, int userId) {
        this.newUsername = newUsername;
        this.oldUsername = oldUsername;
        this.userId = userId;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public void executeOn(CommandDispatch clientThread) {
        clientThread.onCommand(this);
    }
}
