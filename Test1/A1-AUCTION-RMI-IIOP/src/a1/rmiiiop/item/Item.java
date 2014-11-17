package a1.rmiiiop.item;

import a1.rmiiiop.auction.AuctionStatus;

public class Item {
	
	private int itemId;
	private String name;
	private String seller;
	
	public Item(String n, String s) {
		name = n;
		seller = s;
	}	
	
	public void setItemId(int id) {
		itemId = id;
	}
	public int getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public String getSeller() {
		return seller;
	}
	
	public String getDescription(AuctionStatus as) {
		String desc = " - Item id: "+itemId+", Name: "+name+ ", Seller: "+seller+"\n";
		if (as == null) {
			desc += "   Status: no auctioned\n";
		}
		else if (!as.isClosed()) {
			desc += "   Status: at auction, Auction ends: "+as.getAuctionEnds().toString()+"\n";			
			if (as.getBestOffer() == null) {
				desc += "   Base price: "+as.getBasePrice()+" euros, No offers yet\n";				
			}
			else {
				desc += "   Base price: "+as.getBasePrice()+" euros, Best offer: "+as.getBestOffer().getOffer()+"\n";	
			}
		}
		else {
			desc += "   Status: auctioned, Auction ended on: "+as.getAuctionEnds().toString()+"\n";			
			if (as.getBestOffer() == null) {
				desc += "   This item was not sold\n";				
			}
			else {
				desc += "   Sold to "+as.getBestOffer().getBuyer()+" for "+as.getBestOffer().getOffer()+" euros\n";	
			}						
		}			
		return desc;
	}
}
