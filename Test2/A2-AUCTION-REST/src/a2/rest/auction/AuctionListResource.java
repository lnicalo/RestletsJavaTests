package a2.rest.auction;

import java.util.Iterator;
import java.util.Set;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import a2.rest.configuracion.Configuracion;

public class AuctionListResource extends ServerResource {
	
	@Get("html")
	public Representation get(){
		
		Integer numAuction = ((AuctionApp) getApplication()).getAuctionList().size();
				
		String htmldoc= "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n";
		htmldoc+= "<html>\n";
		htmldoc+= "<head>\n";
		htmldoc+= "<title>Auction list</title>\n";
		htmldoc+= "</head>\n";
		htmldoc+= "<body>\n";
		htmldoc+= "<CENTER><H1>AUCTION LIST</H1></CENTER>";
		
		if (numAuction==0) {			
			htmldoc+= "<p> The auction list is empty</p>\n";
		}else {
			Set<Integer> set = ((AuctionApp) getApplication()).getAuctionList().keySet();
			Iterator<Integer> iter = set.iterator();
			
			while(iter.hasNext()){
				Integer i = iter.next();
				htmldoc += "<a href= " + Configuracion.auctionIp + "/auctions/" + i + ">Auction of Item" + i + "</a><br>";
			}
		}
		htmldoc+= "</body>\n";		
		htmldoc+= "</html>\n";		
		return new StringRepresentation(htmldoc,MediaType.TEXT_HTML);
	}
	
	
}
