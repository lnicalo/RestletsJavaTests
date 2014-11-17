package a1.rmiiiop.clients;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import a1.rmiiiop.auction.BidTooLowException;
import a1.rmiiiop.auction.ClosedAuctionException;
import a1.rmiiiop.auction.IAuctionManager;
import a1.rmiiiop.item.NoItemException;


public class MakeBid {

	public static void main(String[] args) {
		Context ic;
        Object objref;
        IAuctionManager stub;

		try {
			ic = new InitialContext();
			
			// Obtenemos la referencia del objeto del Name Service
			objref = ic.lookup("IAuctionManager");
			stub = (IAuctionManager) PortableRemoteObject.narrow(objref, IAuctionManager.class);
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
		catch (NamingException e) {
			System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
		} 
		catch (RemoteException e) {
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
