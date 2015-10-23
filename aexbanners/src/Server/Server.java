/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import static javafx.application.Application.launch;
import shared.*;
//
/**
 *
 * @author Robin
 */
public class Server 
{
    private static final int port = 1099;
    private static final String bindName = "AEX";
    private Registry registry;
    private IEffectenbeurs beurs;
    private BasicPublisher publisher;
    
    public Server()
    {
        System.out.println("Start RMI Server..");
        String[] properties = new String[1];
        properties[0] = "koersUpdate";
        this.publisher = new BasicPublisher(properties);
        //Maak de beurs aan
        try
        {
            beurs = new MockEffectenbeurs(publisher);
        }
        catch(RemoteException e)
        {
            System.out.println("Remote ex. bij aanmaken beurs: " + e.getMessage());
        }
        
        //Maak het registry aan en bind de beurs
        try
        {
            registry = LocateRegistry.createRegistry(port);
            registry.rebind(bindName, beurs);
        }
        catch(RemoteException e)
        {
            System.out.println("Remote ex. bij aanmaken registry: "+e.getMessage());
        }
        System.out.println("Server running!");
    }
    
    public static void main(String[] args) 
    {
        Server server = new Server();
        printServerIP();
        Scanner input = new Scanner(System.in);
        int exit = 0;
        while(true)
        {
            System.out.println("Type 1 om af te sluiten");
            exit = input.nextInt();
            //System.out.println(exit);
            if(exit == 1)
            {
                System.out.println("Server wordt afgesloten..");
                System.exit(1);
            }
            else
                continue;
        }
    }
    
    private static void printServerIP()
    {
        try 
        {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
        }
        catch(java.net.UnknownHostException e)
        {
             System.out.println("Probleem met server IP printen: "+e.getMessage());
        }
    }
}
