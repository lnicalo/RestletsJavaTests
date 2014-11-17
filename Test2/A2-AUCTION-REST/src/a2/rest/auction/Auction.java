package a2.rest.auction;

import java.util.Date;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class Auction {

	private int itemId;
	private boolean closed;
	private Date createdOn;
	private Date auctionEnds;
	private int basePrice;
	private Bid bestOffer;

	public Auction(int i, Date c, Date e, int bp) {
		itemId = i;
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

	public Representation getWebForm() {  
		// Gathering informations into a Web form.  
		Form form = new Form();  
		form.add("itemId", String.valueOf(itemId));  
		form.add("closed", String.valueOf(closed));  
		form.add("createdOn", String.valueOf(createdOn)); 
		form.add("auctionEnds", String.valueOf(auctionEnds)); 
		form.add("basePrice", String.valueOf(basePrice));
		if (bestOffer == null) {
			form.add("bestOffer", String.valueOf(false));
			form.add("buyer", String.valueOf(false));	
		}
		else {
			form.add("bestOffer", String.valueOf(bestOffer.getOffer()));
			form.add("buyer", bestOffer.getBuyer());			
		}
		return form.getWebRepresentation();  
	}
	
	public Representation getHTML() {
		String htmldoc= "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n";
		htmldoc+= "<html>\n";
		htmldoc+= "<head>\n";
		htmldoc+= "<title>Auction of item "+itemId+"</title>\n";
		htmldoc+= "</head>\n";
		htmldoc+= "<body>\n";
		htmldoc+= "<p>Auction of item "+itemId+"</p>\n";
		if (!this.isClosed()) {
			htmldoc+= "<p> Status: at auction, Auction ends: "+auctionEnds.toString()+"</p>\n";			
			if (bestOffer == null) {
				htmldoc+= "<p> Base price: "+basePrice+" euros, No offers yet</p>\n";				
			}
			else {
				htmldoc+= "<p> Base price: "+basePrice+" euros, Best offer: "+bestOffer.getOffer()+"</p>\n";	
			}
		}
		else {
			htmldoc+= "<p> Status: auctioned, Auction ended on: "+auctionEnds.toString()+"</p>\n";			
			if (bestOffer == null) {
				htmldoc+= "<p> This item was not sold</p>\n";			
			}
			else {
				htmldoc+= "<p> Sold to "+bestOffer.getBuyer()+" for "+bestOffer.getOffer()+" euros</p>\n";
			}						
		}		
		htmldoc+= "</body>\n";		
		htmldoc+= "</html>\n";		
		return new StringRepresentation(htmldoc,MediaType.TEXT_HTML);
	}

}
