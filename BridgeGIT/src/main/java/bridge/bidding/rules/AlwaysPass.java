package bridge.bidding.rules;

import bridge.bidding.Bid;
import bridge.bidding.Pass;

public class AlwaysPass extends BiddingRule {

	public AlwaysPass() {
		super(null, null);
	}

	@Override
	protected Bid prepareBid() {
		return new Pass();
	}

	@Override
	protected boolean applies() {
		return true;
	}

}
