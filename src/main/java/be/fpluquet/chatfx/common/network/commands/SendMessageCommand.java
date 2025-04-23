package be.fpluquet.chatfx.common.network.commands;

import be.fpluquet.chatfx.common.models.Message;

public class SendMessageCommand extends AbstractCommand {
    private Message message;

    public SendMessageCommand(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public void executeOn(CommandDispatch clientThread) {
        clientThread.onCommand(this);
    }
}
