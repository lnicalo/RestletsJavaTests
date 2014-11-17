package a2.rest.item;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import a2.rest.configuracion.Configuracion;


public class ItemResource extends ServerResource {
	
	private Integer itemId;
	private Item item;
	
	@Override  
	protected void doInit() throws ResourceException { 
		
		try {
			itemId = Integer.parseInt(((String)getRequest().getAttributes().get("itemId")));
		}
		catch (NumberFormatException e) {
			Status s = new Status(409, "Incorrect Arguments", "", "");
			setStatus(s);
		}
		
		item = ((ItemApp) getApplication()).getItemList().get(itemId);
	}
	
	@Post("form")
	public Representation auctionItem(Representation entity) {	
					
		Form itemForm = new Form(entity);
		//Comprobar que el item existe
		if ( (((ItemApp) getApplication()).getItemList().get(itemId)) == null  ) {
			Status s = new Status(404, "The item doesn´t exit", "", "");
			setStatus(s);
			return null;
		}
		
		try {
			
			ClientResource cr = new ClientResource(Configuracion.auctionIp + "/auctions/" + itemId);
			Representation r = cr.put(itemForm.getWebRepresentation());
			setStatus(Status.SUCCESS_OK);
				
			return r;
			
		}
		catch (ResourceException e) {
			Status s = new Status(409, e.getStatus().getDescription(), "", "");
			setStatus(s);
			return null;
		}
	}	
	
	@Get
	public Representation checkItem() {
		
		Representation r = null;
		
		if (item == null) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		} else {
			setStatus(Status.SUCCESS_OK);
			r = item.getDocumentItemHTML();			
		}
			
		return r;
				
	}
			 	
}
