package a1.rmiiiop.auction;

import java.io.Serializable;

public class Bid implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int offer;
	private String buyer;
	
	public Bid(int o, String b) {
		offer = o;
		buyer = b;
	}	
	
	public int getOffer() {
		return offer;
	}	
	public String getBuyer() {
		return buyer;
	}
	
}
