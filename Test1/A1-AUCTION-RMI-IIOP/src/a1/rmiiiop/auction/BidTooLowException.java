package a1.rmiiiop.auction;

public class BidTooLowException extends Exception {

	private static final long serialVersionUID = 1L;
	public BidTooLowException() {
		super("Your offer  is too low for this auction");
	}
}
