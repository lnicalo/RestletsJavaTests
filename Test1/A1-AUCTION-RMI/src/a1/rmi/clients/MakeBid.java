package a1.rmi.clients;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import a1.rmi.auction.BidTooLowException;
import a1.rmi.auction.ClosedAuctionException;
import a1.rmi.auction.IAuctionManager;
import a1.rmi.item.NoItemException;


public class MakeBid {
	final static String host = "L9008";
	final static int port = 2000;

	public static void main(String[] args) {
		try {
            Registry registry = LocateRegistry.getRegistry(host,port);
            IAuctionManager stub = (IAuctionManager) registry.lookup("IAuctionManager");
            stub.makeBid(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
            System.out.println("Bid made successfully");
        } 
		catch (NoItemException e) {
			System.out.println(e.getMessage());
		}
		catch (BidTooLowException e) {
			System.out.println(e.getMessage());
		}
		catch (ClosedAuctionException e) {
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
        	 System.out.println("ID and offer must be numeric arguments");
        }
        catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println("Usage: <itemID> <offer> <buyer>");
        }

	}

}
