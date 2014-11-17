package a1.rmiiiop.auction;

import java.rmi.Remote;
import java.rmi.RemoteException;

import a1.rmiiiop.item.NoItemException;

public interface IAuctionManager extends Remote {
	
	public void createAuction(int itemId, int basePrice, int numberOfMinutes) 
			throws RemoteException, BadAuctionException;
	public void makeBid(int itemId, int offer, String buyer) throws RemoteException, 
			NoItemException, BidTooLowException, ClosedAuctionException;
	public AuctionStatus checkAuctionStatus(int itemId)  throws RemoteException,
			NoItemException;	
}
