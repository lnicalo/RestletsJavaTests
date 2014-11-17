package a1.rmi.item;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

import a1.rmi.auction.AuctionStatus;
import a1.rmi.auction.BadAuctionException;
import a1.rmi.auction.IAuctionManager;

public class ItemManager implements IItemManager {
	private static int Id=0;
	private Hashtable<Integer, Item> itemTable;
	private static final String host = "L9008";
	private static final int port = 2000;
	private IAuctionManager asStub;
	
	public ItemManager() {
		itemTable = new Hashtable<Integer, Item>();
		
        try {
        	Registry registry = LocateRegistry.getRegistry(host,port);
			asStub = (IAuctionManager) registry.lookup("IAuctionManager");
		} 
        catch (RemoteException e) {
        	System.err.println("ItemManager exception: " + e.toString());
			e.printStackTrace();
        } 
        catch (NotBoundException e) {
        	System.err.println("ItemManager exception: " + e.toString());
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
            IItemManager stub = (IItemManager) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(host,port);
            registry.rebind("IItemManager", stub);

            System.err.println("ItemManager ready");
        }
		catch (RemoteException e) {
            System.err.println("IItemManager exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
