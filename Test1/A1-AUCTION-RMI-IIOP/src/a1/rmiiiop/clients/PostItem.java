package a1.rmiiiop.clients;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import a1.rmiiiop.item.IItemManager;

public class PostItem {
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
            int id_item = stub.postItem(args[0], args[1]);
            System.out.println("Item posted successfully " + id_item);
            System.out.println("Item ID: " + id_item);
        }
		catch( NamingException e) {
			System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
		} 
		catch (RemoteException e) {
			System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
		}
		
		// Excepciones debidas argumentos incorrectos
        catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println("Usage: <name> <seller>");
        }
	}

}
