package a2.rest.clients;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import a2.rest.configuracion.Configuracion;

public class MakeBid {

	public static void main(String[] args) throws IOException {
		try {
			
			Form form = new Form();
			form.add("offer",args[1]);
			form.add("buyer", args[2]);
						
			ClientResource cr = new ClientResource(Configuracion.auctionIp + "/auctions/" + args[0]);
						
			Representation r = cr.post(form.getWebRepresentation());
			
			System.out.println(r.getText());
	        Reference uri = r.getLocationRef();
	        System.out.println("The uri for checking the auction is: " + uri.toString());
			
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Incorrect arguments");
		}
		catch (ResourceException e) {
				System.out.println(e.getStatus().getDescription());
		}

	}

}
