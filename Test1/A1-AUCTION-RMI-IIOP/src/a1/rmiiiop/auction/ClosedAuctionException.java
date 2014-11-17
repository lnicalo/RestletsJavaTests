package a1.rmiiiop.auction;

public class ClosedAuctionException extends Exception {

	private static final long serialVersionUID = 1L;
	public ClosedAuctionException() {
		super("This auction is closed. You can not make any bid.");
	}

}
