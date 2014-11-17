package a2.rest.item;

import java.util.HashMap;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import a2.rest.configuracion.Configuracion;

public class ItemApp extends Application {
private HashMap<Integer, Item> itemMap = new HashMap<Integer, Item>();
	
	 /** 
     * Creates a root Restlet that will receive all incoming calls. 
     */  
    @Override  
    public synchronized Restlet createInboundRoot() {  
        // Create a router Restlet that routes each call to a  
        // new instance.  
        Router router = new Router(getContext());  
  
        // Defines several routes
        router.attach("/items", ItemListResource.class);  
        router.attach("/items/{itemId}", ItemResource.class);  
        
        return router;  
    }
    
    public static void main(String[] args) throws Exception {  
        // Create a new Component.  
        Component component = new Component();  
      
        // Add a new HTTP server listening on a port that can be changed in the Configuracion
        // class.  
        component.getServers().add(Protocol.HTTP, Configuracion.itemPort);
        //Add a new HTTP client listening
        component.getClients().add(Protocol.HTTP);
        // Attach the application.  
        component.getDefaultHost().attach("",  
                new ItemApp());
      
        // Start the component.  
        component.start();  
    }

	public HashMap<Integer, Item> getItemList() {
		return itemMap;
	}
}
