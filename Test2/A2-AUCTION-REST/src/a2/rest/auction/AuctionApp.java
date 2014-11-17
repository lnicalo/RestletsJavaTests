package a2.rest.auction;
  

import java.util.HashMap;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import a2.rest.configuracion.Configuracion;

public class AuctionApp extends Application {  
	private HashMap<Integer, Auction> auctionMap = new HashMap<Integer, Auction>();
	
    /** 
     * Creates a root Restlet that will receive all incoming calls. 
     */  
    @Override  
    public synchronized Restlet createInboundRoot() {  
        // Create a router Restlet that routes each call to a  
        // new instance 
        Router router = new Router(getContext());  
  
        // Defines several routes
        router.attach("/auctions", AuctionListResource.class);  
        router.attach("/auctions/{itemId}", AuctionResource.class);  
        
        return router;  
    }
    
    public static void main(String[] args) throws Exception {  
        // Create a new Component.  
        Component component = new Component();  
      
        // Add a new HTTP server listening on a port that can be changed in Configuracion
        // class
        component.getServers().add(Protocol.HTTP, Configuracion.auctionPort);
        // Attach the application.  
        component.getDefaultHost().attach("",  
                new AuctionApp());
     
        component.start();  
    }

	public HashMap<Integer, Auction> getAuctionList() {
		return auctionMap;
	}
}