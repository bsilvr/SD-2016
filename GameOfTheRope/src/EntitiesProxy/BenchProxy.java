/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntitiesProxy;

import gameoftherope.ConfigRepository;
import gameoftherope.Interfaces.IBenchCoach;
import gameoftherope.Interfaces.IBenchPlayer;
import gameoftherope.Interfaces.IBenchRef;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 *
 * @author Bruno Silva [brunomiguelsilva@ua.pt]
 * @author Bernardo Ferreira [bernardomrf@ua.pt]
 */
public class BenchProxy implements IBenchCoach, IBenchPlayer, IBenchRef{
    
    /**
     *
     */
    public BenchProxy(){
        Map<String, Integer> benchConfigs = ConfigRepository.getBenchConfigs();
        int port = benchConfigs.get("benchPort");
        String hostName = "localhost";
        
        Socket benchSocket;
        try {
            benchSocket = new Socket(hostName, port);
        } catch (IOException ex) {
        }
    }
    
    /**
     *
     * @param team
     */
    @Override
    public void reviewNotes(String team) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param team
     * @return
     */
    @Override
    public int[] callContestants(String team) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void waitForRefCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    public boolean hasMatchFinished() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param team
     */
    @Override
    public void playersReady(String team) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param team
     * @param id
     * @return
     */
    @Override
    public boolean seatAtTheBench(String team, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param team
     */
    @Override
    public void seatDown(String team) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param team
     */
    @Override
    public void followCoachAdvice(String team) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void signalCoaches() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void setMatchFinish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
