package bridge.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.core.Hand;

public class TakeoutDouble extends BiddingRule {

	public TakeoutDouble(Auctioneer a, Hand h) {
		super(a, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean applies() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Bid prepareBid() {
		// TODO Auto-generated method stub
		return null;
	}

}
