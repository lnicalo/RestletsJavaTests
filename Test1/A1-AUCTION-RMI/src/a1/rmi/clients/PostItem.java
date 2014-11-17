package a1.rmi.clients;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import a1.rmi.item.IItemManager;

public class PostItem {
	final static String host = "L9008";
	final static int port = 2000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(host,port);
            IItemManager stub = (IItemManager) registry.lookup("IItemManager");
            int id_item = stub.postItem(args[0], args[1]);
            System.out.println("Item posted successfully");
            System.out.println("Item ID: " + id_item);
        } 
        catch (RemoteException e) {
             System.err.println("Client exception: " + e.toString());
             e.printStackTrace();
		} 
        catch (NotBoundException e) {
			System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
		}
        
        // Excepciones debidas argumentos incorrectos
        catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println("Usage: <name> <seller>");
        }
	}
}
