/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

/**
 *
 * @author Hovsep
 */
import Server.RemotePublisher;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IEffectenbeurs extends Remote, RemotePublisher{

	List<IFonds> getKoersen() throws RemoteException;

}