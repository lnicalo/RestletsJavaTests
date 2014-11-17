package a1.rmi.item;

public class NoItemException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoItemException() {
		super("The item identifier is not valid.");
		
	}

}
