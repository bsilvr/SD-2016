/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftherope.EntitiesHandlers;

import gameoftherope.EndOfTransactionException;
import gameoftherope.Protocols.PlaygroundProtocol;
import gameoftherope.Regions.Playground;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class for the Playground handler.
 * 
 * @author Bruno Silva [brunomiguelsilva@ua.pt]
 * @author Bernardo Ferreira [bernardomrferreira@ua.pt]
 */
public class PlaygroundHandler extends Thread {

    private Socket socket;
    private PlaygroundProtocol protocol;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private ServerSocket listeningSocket;

    /**
     * Constructor for Playground Handler class.
     *
     * @param commSocket Socket - Socket for establishing connection with the client.
     * @param playground Playground - Playground instance.
     * @param listeningSocket ServerSocket - Instance of Server Socket used to end simulation with a close command.
     */
    public PlaygroundHandler(Socket commSocket, Playground playground, ServerSocket listeningSocket) {
        socket = commSocket;
        protocol = new PlaygroundProtocol(playground);
        this.listeningSocket = listeningSocket;
        
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        }
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        boolean end = false;
        Object inputLine = null; 
        Object outputLine = null;
        
        while (!end) {
            try {
                inputLine = (String) in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
            }
            try {
                outputLine = protocol.processInput((String)inputLine);
            } catch (UnsupportedOperationException ex) {
            } catch (EndOfTransactionException ex) {
                try {
                    if(!listeningSocket.isClosed()){
                        listeningSocket.close();
                    }
                } catch (IOException ex1) {
                }
                break;
            }
            inputLine = null;
            try {
                out.writeObject(outputLine);
            } catch (IOException ex) {
            }
        }
    }
    
}
