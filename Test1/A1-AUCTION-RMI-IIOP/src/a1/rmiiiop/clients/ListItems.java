package a1.rmiiiop.clients;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import a1.rmiiiop.item.IItemManager;

public class ListItems {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Context ic;
        Object objref;
        IItemManager stub;

		try {
			ic = new InitialContext();
			
			// Obtenemos la referencia del objeto del Name Service
			objref = ic.lookup("IItemManager");
			stub = (IItemManager) PortableRemoteObject.narrow(objref, IItemManager.class);
            String lista = stub.listItems();
            System.out.println(lista);
        } catch (Exception e) {
        	System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
	}

}
