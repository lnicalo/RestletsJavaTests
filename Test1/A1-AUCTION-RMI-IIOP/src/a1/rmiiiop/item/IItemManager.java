package a1.rmiiiop.item;

import java.rmi.Remote;
import java.rmi.RemoteException;

import a1.rmiiiop.auction.BadAuctionException;

public interface IItemManager extends Remote {
	
	public int postItem(String name, String seller) throws RemoteException;
	public void auctionItem(int itemId, int basePrice, int numberOfMinutes) throws RemoteException,
			NoItemException, BadAuctionException; 
	public String listItems() throws RemoteException;

}
