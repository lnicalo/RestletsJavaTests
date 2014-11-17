package a2.rest.item;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import a2.rest.configuracion.Configuracion;

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
	
	public Representation getDocumentItemHTML() {
		String htmldoc= "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n";
		htmldoc+= "<html>\n";
		htmldoc+= "<head>\n";
		htmldoc+= "<title>Description of item "+itemId+"</title>\n";
		htmldoc+= "</head>\n";
		htmldoc+= "<body>\n";
		
		htmldoc += getItemHTML();
		htmldoc+= "</body>\n";		
		htmldoc+= "</html>\n";		
		return new StringRepresentation(htmldoc,MediaType.TEXT_HTML);
	}
	
	public String getItemHTML() {
		
		String htmlitem = "<p>Item "+itemId+"</p>\n";
		htmlitem+= "<p> Name: "+name+"</p>\n";
		htmlitem+= "<p> Seller: "+seller+"</p>\n";
		
		try {
			// In order to know if the item is auctioned, it is necessary to connect with
			// the AuctionManager
			ClientResource cr = new ClientResource(Configuracion.auctionIp + "/auctions/" + itemId);
			cr.get();
			htmlitem+= "<p> Status: Auctioned</p>\n";
		}
		catch (ResourceException e) {
			// If the resource (auction) does not exist, the item is not at auction 
			htmlitem+= "<p> Status: Not Auctioned</p>\n";
		}
					
		return htmlitem;
	}
}
