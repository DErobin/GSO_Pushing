/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Hovsep
 */
import Client.RemotePropertyListener;
import java.rmi.RemoteException;
import shared.IEffectenbeurs;
import shared.IFonds;
import java.util.ArrayList;

public class Effectenbeurs implements IEffectenbeurs {

    ArrayList<IFonds> koersen;

    @Override
    public ArrayList<IFonds> getKoersen() {
        return koersen;
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
