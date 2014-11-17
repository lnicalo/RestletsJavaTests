package a1.rmi.auction;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Hashtable;

import a1.rmi.item.NoItemException;
import a1.rmi.auction.Bid;


public class AuctionManager implements IAuctionManager {
	private Hashtable<Integer, AuctionStatus> AuctionTable;
	private static final String host = "L9008";
	private static final int port = 2000;
	
	public AuctionManager (){
		AuctionTable = new Hashtable<Integer, AuctionStatus>();
		
	}
		
	public AuctionStatus checkAuctionStatus(int itemId) throws RemoteException,
			NoItemException {
		AuctionStatus as = AuctionTable.get(itemId);
		if (as == null ) {
			throw new NoItemException();
		}
		
		Date rightNow = new Date();
		if(rightNow.after(as.getAuctionEnds())) {
			// Actualizamos la auction
			as.setClosed();
		}
		
		return as;
	}

	public void createAuction(int itemId, int basePrice, int numberOfMinutes)
			throws RemoteException, BadAuctionException {
		
		// Comprobar parametros de entrada
		AuctionStatus as = AuctionTable.get(itemId);
		if (as!= null || basePrice < 0 ||  numberOfMinutes < 0) {
			throw new BadAuctionException();
		}
		
		Date rightNow = new Date();
		
		// Pasamos los minutos de entrada a milisegundos
		Date endDate = new Date(rightNow.getTime() + numberOfMinutes*60000);
		
		// Creamos subasta
		as = new AuctionStatus(rightNow, endDate, basePrice);
		AuctionTable.put(itemId, as);
	}

	public void makeBid(int itemId, int offer, String buyer)
			throws RemoteException, NoItemException, BidTooLowException,
			ClosedAuctionException {
		
		// Comprobamos parametros
		AuctionStatus as = checkAuctionStatus(itemId);
		if (as == null) {
			throw new NoItemException();
		}
		
		Bid bestOffer = as.getBestOffer();
		if (bestOffer != null) {
			if (as.getBasePrice() > offer) {
				throw new BidTooLowException();
			}
			else if (bestOffer.getOffer() >= offer) {
				throw new BidTooLowException();
			}
			else if(as.isClosed()) {
				throw new ClosedAuctionException();
			}
		}	
		else {
			if (as.getBasePrice() > offer) {
				throw new BidTooLowException();
			}
			else if(as.isClosed()) {
				throw new ClosedAuctionException();
			}
		}
		as.setBestOffer(new Bid(offer, buyer));
		
	}
	
	public static void main(String args[]) {
		try {
            AuctionManager obj = new AuctionManager();
            IAuctionManager stub = (IAuctionManager) UnicastRemoteObject.exportObject(obj, 0);
            	
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(host,port);
            registry.rebind("IAuctionManager", stub);

            System.err.println("IAuctionManager ready");
        } catch (Exception e) {
            System.err.println("IAuctionManager exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
