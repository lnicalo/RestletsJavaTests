package a1.rmiiiop.auction;

public class BadAuctionException extends Exception {

	private static final long serialVersionUID = 1L;
	public BadAuctionException() {
		super("Action parameters are not valid or there is another auction associated to this item");
	}
}
