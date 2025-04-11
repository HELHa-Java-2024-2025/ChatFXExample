package be.fpluquet.chatfx.network.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectSocket {

    private final Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    public ObjectSocket(Socket socket) throws ObjectSocketCreationException {
        this.socket = socket;
        try {
            this.openStreams();
        } catch (IOException e) {
            throw new ObjectSocketCreationException(e);
        }
    }

    private void openStreams() throws IOException {
        try {
            this.outStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inStream = new ObjectInputStream(this.socket.getInputStream());
        }catch(IOException e) {
            if(this.outStream != null) {
                try {
                    this.outStream.close();
                }catch(IOException e1) {
                    // tant pis...
                }
            }
            throw e;
        }
    }

    public void write(Object object) throws IOException {
        this.outStream.writeObject(object);
        this.outStream.flush();
    }

    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) this.inStream.readObject();
    }

    public void close() {
        try {
            this.outStream.close();
        } catch (IOException e) {
            // tant pis...
        }
        try {
            this.inStream.close();
        } catch (IOException e) {
            // tant pis...
        }
        try {
            this.socket.close();
        } catch (IOException e) {
            // tant pis...
        }
    }
}
