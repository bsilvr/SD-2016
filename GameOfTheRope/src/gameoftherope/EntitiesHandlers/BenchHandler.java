/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftherope.EntitiesHandlers;

import gameoftherope.EndOfTransactionException;
import gameoftherope.Protocols.BenchProtocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Bruno Silva <brunomiguelsilva@ua.pt>
 */
public class BenchHandler extends Thread{
    private Socket socket;
    private BenchProtocol protocol;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private ServerSocket listeningSocket;

    public BenchHandler(Socket commSocket, BenchProtocol bp, ServerSocket listeningSocket) {
        socket = commSocket;
        protocol = bp;
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
