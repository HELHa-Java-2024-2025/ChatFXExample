package be.fpluquet.chatfx.common.network.commands;

public interface CommandDispatch {
    void onCommand(ChangeUsernameCommand c);
    void onCommand(SendMessageCommand c);
}
