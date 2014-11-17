package a2.rest.item;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import a2.rest.configuracion.Configuracion;

public class ItemListResource extends ServerResource {
	
	private static int Id = 0;
	
	@Post("form")
	public Representation acceptItem(Representation entity) {	
		try {
		
			Form itemForm = new Form(entity);
					
			// The item and seller name is obtained
			String name = itemForm.getFirstValue("name");
			String seller = itemForm.getFirstValue("seller");
			
			// A new Item is created and it is assigned a new Id
			Item newItem = new Item(name, seller);
			Id++;
			newItem.setItemId(Id);
			
			//The new Item is attached to the HashTable
			((ItemApp) getApplication()).getItemList().put(Id, newItem);
		
			//Status is updated and the return representation is created  
			setStatus(Status.SUCCESS_OK);
			Representation res = new StringRepresentation("Item created. Id:" + Id, MediaType.TEXT_PLAIN);
			res.setLocationRef(getRequest().getResourceRef().getIdentifier() + "/" + Id);
			return res;
		}
		catch (Exception e) {
			setStatus(Status.SERVER_ERROR_NOT_IMPLEMENTED);
			return null;
		}
			
	}
	
	@Get
	public Representation listItemsHTML() {

		int fin = ((ItemApp) getApplication()).getItemList().size();
		
		String htmldoc= "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n";
		htmldoc+= "<html>\n";
		htmldoc+= "<head>\n";
		htmldoc+= "<title>List Items</title>\n";
		htmldoc+= "</head>\n";
		htmldoc+= "<body>\n";
		htmldoc+= "<CENTER><H1>ITEM LIST</H1></CENTER>";
		
		if (fin==0) {			
			htmldoc+= "<p> The item list is empty</p>\n";
		}
		else {
			for (int i=1; i<=fin; i++){
				htmldoc += "<a href=" + Configuracion.itemIp + "/items/" + i + ">Item " + i + "</a><br>";
			}
		}
		
		htmldoc+= "</body>\n";		
		htmldoc+= "</html>\n";		
		return new StringRepresentation(htmldoc,MediaType.TEXT_HTML);
	}
}
