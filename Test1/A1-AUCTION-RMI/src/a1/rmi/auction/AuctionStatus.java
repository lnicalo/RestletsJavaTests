package a1.rmi.auction;

import java.io.Serializable;
import java.util.Date;

import a1.rmi.auction.Bid;

public class AuctionStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean closed;
	private Date createdOn;
	private Date auctionEnds;
	private int basePrice;
	private Bid bestOffer;
	
	public AuctionStatus(Date c, Date e, int bp) {
		closed = false;
		createdOn = c;
		auctionEnds = e;
		basePrice = bp;
		setBestOffer(null);
	}

	public void setClosed() {
		this.closed = true;
	}
	public boolean isClosed() {
		return closed;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public Date getAuctionEnds() {
		return auctionEnds;
	}
	public int getBasePrice() {
		return basePrice;
	}
	public void setBestOffer(Bid bestOffer) {
		this.bestOffer = bestOffer;
	}
	public Bid getBestOffer() {
		return bestOffer;
	}	
	
}
