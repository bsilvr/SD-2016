/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftherope.EntitiesHandlers;

import gameoftherope.EndOfTransactionException;
import gameoftherope.Protocols.ConfigServerProtocol;
import gameoftherope.Regions.ConfigRepository;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class for the Config Repository handler.
 * 
 * @author Bruno Silva [brunomiguelsilva@ua.pt]
 * @author Bernardo Ferreira [bernardomrferreira@ua.pt]
 */
public class ConfigHandler extends Thread{
    private Socket socket;
    private ConfigServerProtocol protocol;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private ServerSocket listeningSocket;

    /**
     * Constructor for Config Repository Handler class.
     *
     * @param commSocket Socket - Socket for establishing connection with the client.
     * @param configRepo ConfigRepository - ConfigRepository instance.
     * @param listeningSocket ServerSocket - Instance of Server Socket used to end simulation with a close command.
     */
    public ConfigHandler(Socket commSocket, ConfigRepository configRepo, ServerSocket listeningSocket) {
        socket = commSocket;
        protocol = new ConfigServerProtocol(configRepo);
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
