package bridge.bidding;

public class Double extends Bid {

	public Double() {
		super(-2, null);
	}

	@Override
	public String toString() {
		return stringValue();
	}

	public static String stringValue() {
		return "DOUBLE";
	}

	public boolean equals(Object a) {
		if (a instanceof Double) {
			return true;
		}
		return false;
	}
}
