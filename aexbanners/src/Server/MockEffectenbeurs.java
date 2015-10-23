/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Client.RemotePropertyListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import shared.IEffectenbeurs;
import shared.IFonds;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs {

    ArrayList<Fonds> koersen;
    Timer timer;
    private BasicPublisher publisher;

    public MockEffectenbeurs(BasicPublisher publisher) throws RemoteException 
    {
        koersen = new ArrayList<Fonds>();
        koersen.add(new Fonds("Anaal B.V.", 666));
        koersen.add(new Fonds("Sander's Plagiaatshop", 100));
        koersen.add(new Fonds("Bert's Tenenkaaswinkel", 60));
        //koersen.add(new Fonds("Samsung", 80));
        //koersen.add(new Fonds("Asus", 65));
        timer = new Timer();
        timer.scheduleAtFixedRate(new MockTask(), 0, 200);
        this.publisher = publisher;
    }

    @Override
    public List<IFonds> getKoersen() throws RemoteException
    {
        return new ArrayList<IFonds>(koersen);
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }

    class MockTask extends TimerTask {

        @Override
        public void run() {
            for (Fonds f : koersen) {
                if (Math.floor(Math.random() * 101) == 0) {
                    double oldVal = f.getKoers();
                    f.setKoers(f.getKoers() + Math.floor(Math.random() * 56 - 10));
                    double newVal = f.getKoers();
                    publisher.inform(this, "koersUpdate", oldVal, newVal);
                }
            }
            /*
            try
            {    
                System.out.println("Koersen: " + getKoersen().toString());
            }
            catch(RemoteException e)
            {
                System.out.println(e.getMessage());
            }*/
        }
    }
}
