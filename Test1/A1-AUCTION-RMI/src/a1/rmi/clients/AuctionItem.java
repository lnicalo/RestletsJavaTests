package a1.rmi.clients;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import a1.rmi.auction.BadAuctionException;
import a1.rmi.item.IItemManager;
import a1.rmi.item.NoItemException;

public class AuctionItem {
	final static String host = "L9008";
	final static int port = 2000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
            Registry registry = LocateRegistry.getRegistry(host,port);
            IItemManager stub = (IItemManager) registry.lookup("IItemManager");
            stub.auctionItem(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            System.out.println("Item auctioned successfully");
        } 
		catch (NoItemException e) {
			System.out.println(e.getMessage());
		}
		catch (BadAuctionException e) {
			System.out.println(e.getMessage());
		} 
		
		catch (RemoteException e) {
			System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
		} 
		catch (NotBoundException e) {
			System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
		}
		
		// Excepciones debidas argumentos del programa incorrectos
        catch (NumberFormatException e) {
        	 System.out.println("itemID, basePrice and numberOfMinutes must be numeric arguments");
        }
        catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println("Usage: <itemID> <basePrice> <numberOfMinutes>");
        }
	}

}