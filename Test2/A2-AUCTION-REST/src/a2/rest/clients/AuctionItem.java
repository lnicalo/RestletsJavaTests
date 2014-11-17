package a2.rest.clients;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import a2.rest.configuracion.Configuracion;


public class AuctionItem {

	public static void main(String[] args) throws IOException{
		
		try {
			
			Form form = new Form();			// Form to send data
			form.add("itemId",args[0]);
			form.add("basePrice",args[1]);
			form.add("numberOfMinutes",args[2]);
									
			ClientResource c = new ClientResource(Configuracion.itemIp + "/items/" + args[0]);
			Representation r = c.post(form.getWebRepresentation());
					
			System.out.println(r.getText());
	        Reference uri = r.getLocationRef();
	        System.out.println("The uri for checking the auction is: " + uri.toString());
	 	   	       			
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Incorrect arguments");
		
		}
		catch (ResourceException e) {
			Status s = e.getStatus();
			System.out.println(s.getDescription());
			
		}
	}

}
