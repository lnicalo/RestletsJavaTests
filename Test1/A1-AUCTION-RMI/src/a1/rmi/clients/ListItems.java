package a1.rmi.clients;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import a1.rmi.item.IItemManager;

public class ListItems {
	private static final String host = "L9008";
	private static final int port = 2000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(host,port);
            IItemManager stub = (IItemManager) registry.lookup("IItemManager");
            String lista = stub.listItems();
            System.out.println(lista);
        } 
        catch (RemoteException e) {
        	 System.err.println("Client exception: " + e.toString());
             e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
		}
	}

}
