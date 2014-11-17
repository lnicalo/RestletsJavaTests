package a2.rest.auction;

import java.util.Date;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class AuctionResource extends ServerResource {
	
	private Integer itemId;
	private Auction auction;
	
	@Override  
	protected void doInit() throws ResourceException {  
		// Get the "itemId" attribute value taken from the URI template
		try {
			itemId = Integer.parseInt(((String)getRequest().getAttributes().get("itemId")));
		}
		catch (NumberFormatException e) {
			Status s = new Status(409, "Incorrect Arguments", "", "");
			setStatus(s);
		}
		// Get the auction information
		auction = ((AuctionApp) getApplication()).getAuctionList().get(itemId);
		
	}
	
	@Post("form")
	public Representation makeBid(Representation entity) {
		
		Form aForm = new Form(entity);
		Date now = new Date();
		String buyer = aForm.getFirstValue("buyer");
		Integer offer;
		
		try  {
			offer = Integer.parseInt(aForm.getFirstValue("offer"));
		}
		catch (NumberFormatException e) {
			Status s = new Status(409, "Incorrect Arguments", "", "");
			setStatus(s);
			return null;				
		}
		
		if(auction == null) {
			Status s = new Status(404, "The item is not at auction", "", "");
			setStatus(s);
			return null;
		}
		
		if (now.after(auction.getAuctionEnds()))
			auction.setClosed();
		
		Bid bestOffer = auction.getBestOffer();
		if (bestOffer != null) {
			if (auction.getBasePrice() > offer) {
				Status s = new Status(400, "The bid is too low", "", "");
				setStatus(s);
				return null;
			}
			else if (bestOffer.getOffer() >= offer) {
				Status s = new Status(400, "The bid is too low", "", "");
				setStatus(s);
				return null;
			}
			else if(auction.isClosed()) {
				Status s = new Status(400, "The auction is closed", "", "");
				setStatus(s);
				return null;
			}
		}	
		else {
			if (auction.getBasePrice() > offer) {
				Status s = new Status(400, "The bid is too low", "", "");
				setStatus(s);
				return null;
			}
			else if(auction.isClosed()) {
				Status s = new Status(400, "The auction is closed", "", "");
				setStatus(s);
				return null;
			}
		}
		
		auction.setBestOffer(new Bid(offer, buyer));
		setStatus(Status.SUCCESS_OK);
		Representation res = new StringRepresentation("Bid succesfully made", MediaType.TEXT_PLAIN);
		res.setLocationRef(getRequest().getResourceRef().getIdentifier());
		return res;

	}
	
	@Put("form")
	public Representation acceptAuction(Representation entity) {	
		
		if(auction != null) {
			Status s = new Status(409, "The item is already at auction", "", "");
			setStatus(s);
			return null;
		}
		else {
			try {
				
				Form auctionForm = new Form(entity);
						
				Integer basePrice = Integer.parseInt(auctionForm.getFirstValue("basePrice"));
				Integer numberOfMinutes = Integer.parseInt(auctionForm.getFirstValue("numberOfMinutes"));
				
				if(basePrice < 0 || numberOfMinutes < 0) {
					Status s = new Status(409, "Incorrect Arguments", "", "");
					setStatus(s);
					return null;
				}
				
				Date rightNow = new Date();
				Date endDate = new Date(rightNow.getTime() + numberOfMinutes*60000);
				
				Auction auction = new Auction(itemId,rightNow,endDate,basePrice);
				((AuctionApp) getApplication()).getAuctionList().put(itemId, auction);
				
				Representation res = new StringRepresentation("Auction created. Id:" + itemId, MediaType.TEXT_PLAIN);
				res.setLocationRef(getRequest().getResourceRef().getIdentifier());
				return res;
			}
			
			catch (NumberFormatException e) {
				Status s = new Status(409, "Incorrect Arguments", "", "");
				setStatus(s);
				return null;				
			}
		}
	}  		
		
	
	@Get("html")
	public Representation toHTML() {
		
		Representation r = null;
		
		if (auction!=null) {
			setStatus(Status.SUCCESS_OK);
			Date now = new Date();
			
			if (now.after(auction.getAuctionEnds())) {
				auction.setClosed();
			}
			
			r = auction.getHTML();

		}
		else {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);			
		}
		
		return r;
	}	
		
}
