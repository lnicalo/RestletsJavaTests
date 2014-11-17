package a1.rmiiiop.auction;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import a1.rmiiiop.item.NoItemException;


public class AuctionManager extends PortableRemoteObject implements IAuctionManager {
	private Hashtable<Integer, AuctionStatus> AuctionTable;
	
	public AuctionManager () throws RemoteException {
		super();
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
            Context initialNamingContext = new InitialContext();
            initialNamingContext.rebind("IAuctionManager", obj);

            System.err.println("IAuctionManager ready");
        } catch (Exception e) {
            System.err.println("IAuctionManager exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
