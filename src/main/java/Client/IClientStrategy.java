package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Strategy interface for client-server communication.
 * Defines how a client should communicate with a server using the provided streams.
 * @version 1.0
 * sapir aharoni
 */
public interface IClientStrategy {

    /**
     * Implements the communication protocol between client and server.
     *
     * @param inFromServer stream to read data from the server
     * @param outToServer stream to send data to the server
     */
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}