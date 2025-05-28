package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Strategy interface for defining server communication protocols.
 * Implementations of this interface define how the server handles
 * client requests and responses.
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public interface IServerStrategy {

    /**
     * Applies the specific strategy for handling client communication.
     *
     * @param inFromClient the input stream from the client
     * @param outToClient the output stream to the client
     */
    void applyStrategy(InputStream inFromClient, OutputStream outToClient);
}