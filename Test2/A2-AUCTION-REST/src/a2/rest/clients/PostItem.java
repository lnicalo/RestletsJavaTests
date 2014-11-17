package a2.rest.clients;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import a2.rest.configuracion.Configuracion;

public class PostItem {
	public static void main(String[] args) throws IOException {
		try {
					
			Form form = new Form();
			form.add("name",args[0]);
			form.add("seller",args[1]);
						
			ClientResource cr = new ClientResource(Configuracion.itemIp + "/items");
						
			Representation r = cr.post(form.getWebRepresentation());
	        System.out.println(r.getText());
	        Reference uri = r.getLocationRef();
	        System.out.println("The uri for the posted Item is: " + uri.toString());
			
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Incorrect arguments");
		}
		catch (ResourceException e) {
				System.out.println(e.getStatus().getDescription());
		}
	}
}
