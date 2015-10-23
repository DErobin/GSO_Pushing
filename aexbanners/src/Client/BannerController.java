/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import shared.IEffectenbeurs;
import shared.IFonds;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 */
public class BannerController extends UnicastRemoteObject implements RemotePropertyListener {

    private AEXBanner banner;

    //RMI:
    private static final int port = 1099;
    private static final String bindName = "AEX";
    private Registry registry;
    //todo PAS DIT AAN
    private static final String ip = "192.168.0.8";

    private IEffectenbeurs effectenbeurs;
    //private Timer pollingTimer;

    public BannerController(AEXBanner banner) throws RemoteException{

        this.banner = banner;

        System.out.println("Client gestart. RMI IP:" + ip + ":" + port);

        //Registry:
        try {
            registry = LocateRegistry.getRegistry(ip, port);
        } catch (RemoteException e) {
            System.out.println("Kan registry niet vinden: " + e.getMessage());
        }

        //Bind de effectenbeurs:
        try {
            this.effectenbeurs = (IEffectenbeurs) registry.lookup(bindName);
        } catch (NotBoundException e) {
            System.out.println("Probleem bij het binden: " + e.getMessage());
        } catch (RemoteException e) {
            System.out.println("Probleem bij het binden: " + e.getMessage());
        }
        //Start polling timer: update banner every two seconds
        //pollingTimer = new Timer();
        //pollingTimer.scheduleAtFixedRate(new fondsTask(), 1000, 200);
        try {
            effectenbeurs.addListener(this, "koersUpdate");
        } catch (RemoteException e) {
            System.out.println("Probleem bij het toevoegen van listener!");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        System.out.println("Koersupdate ontvangen. O: " + evt.getOldValue() + " N: " + evt.getNewValue());
        try {
            banner.setKoersen(getKoers());
        } catch (RemoteException e) {
            System.out.println("Error bij ophalen koersen: " + e.getMessage());
        }
    }
    /*
    class fondsTask extends TimerTask {

        @Override
        public void run() {
            try {
                banner.setKoersen(getKoers());
            } catch (RemoteException e) {
                System.out.println("Error bij ophalen koersen: " + e.getMessage());
            }
            //banner.setKoersen(sb.toString());
        }
    }*/

    private String getKoers() throws RemoteException {
        StringBuilder sb = new StringBuilder("");

        for (IFonds f : effectenbeurs.getKoersen()) {
            sb.append(f.getNaam()).append(": ").append(f.getKoers()).append(" ");
        }
        return sb.toString();
    }

    // Stop banner controller
    /*
    public void stop() {
        pollingTimer.cancel();
    }*/
}
