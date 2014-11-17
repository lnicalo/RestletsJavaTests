package a1.rmiiiop.item;

import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import a1.rmiiiop.item.Item;
import a1.rmiiiop.item.NoItemException;
import a1.rmiiiop.auction.AuctionStatus;
import a1.rmiiiop.auction.BadAuctionException;
import a1.rmiiiop.auction.IAuctionManager;

public class ItemManager extends PortableRemoteObject implements IItemManager {
	private static int Id=0;
	private Hashtable<Integer, Item> itemTable;
	private IAuctionManager asStub;
	
	public ItemManager() throws RemoteException {
		super();
		itemTable = new Hashtable<Integer, Item>();
		
		Context ic;
        Object objref;
		try {
			ic = new InitialContext();
			objref = ic.lookup("IAuctionManager");
			
			// Obtenemos la referencia del objeto del Name Service
			asStub = (IAuctionManager) PortableRemoteObject.narrow(objref, IAuctionManager.class);
		} catch (NamingException e) {
			System.err.println("IItemManager exception: " + e.toString());
	        e.printStackTrace();
		}
		
		
		
		
	}
	
	public void auctionItem(int itemId, int basePrice, int numberOfMinutes)
			throws RemoteException, NoItemException, BadAuctionException {
        
        if (itemTable.get(itemId) == null) {
        	throw new NoItemException();
        }

		asStub.createAuction(itemId, basePrice, numberOfMinutes);
    	
		
	}

	public String listItems() throws RemoteException {
		String output = "";
		
        Item item;
        AuctionStatus as;
        
		for(int i=1;i <= Id;i++) {
			item = itemTable.get(i);
			try{
				as = asStub.checkAuctionStatus(i);
			}
			catch (NoItemException e) {
				as = null;
			}
			output += item.getDescription(as);
		}  		  		
		
        return output;
	}

	public int postItem(String name, String seller) throws RemoteException {
		
		Item newItem = new Item(name, seller);
		// Asignamos un Id
		Id++;
		newItem.setItemId(Id);
		itemTable.put(Id, newItem);
		return Id;
	}

	public static void main(String args[]) {
		try {
			ItemManager obj = new ItemManager();
            Context initialNamingContext = new InitialContext();
            initialNamingContext.rebind("IItemManager", obj);

            System.out.println("ItemManager ready");
        } 
		catch (NamingException e) {
	        System.err.println("IItemManager exception: " + e.toString());
	        e.printStackTrace();
		} catch (RemoteException e) {
			System.err.println("IItemManager exception: " + e.toString());
	        e.printStackTrace();
		}
    }
}
