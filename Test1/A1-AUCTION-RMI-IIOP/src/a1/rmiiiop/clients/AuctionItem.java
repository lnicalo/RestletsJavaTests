package a1.rmiiiop.clients;


import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import a1.rmiiiop.auction.BadAuctionException;
import a1.rmiiiop.item.IItemManager;
import a1.rmiiiop.item.NoItemException;

public class AuctionItem {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Context ic;
        Object objref;
        IItemManager stub;

		try {
			ic = new InitialContext();
			
			// Obtenemos la referencia del objeto del Name Service
			objref = ic.lookup("IItemManager");
			stub = (IItemManager) PortableRemoteObject.narrow(objref, IItemManager.class);
			
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
		} catch (NamingException e) {
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