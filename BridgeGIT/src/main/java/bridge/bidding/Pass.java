package bridge.bidding;

public class Pass extends Bid {
	public Pass() {
		super(-1, null);
	}

	@Override
	public String toString() {
		return stringValue();
	}

	public static String stringValue() {
		return "PASS";
	}
}
