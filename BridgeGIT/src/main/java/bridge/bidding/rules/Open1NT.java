package bridge.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.PointCalculator;
import bridge.core.Hand;
import bridge.core.deck.NoTrump;

public class Open1NT extends BiddingRule {

	private PointCalculator pc;

	public Open1NT(Auctioneer a, Hand h) {
		super(a, h);
		pc = new PointCalculator(hand);
	}

	@Override
	protected Bid prepareBid() {
		return new Bid(1, NoTrump.i());
	}

	@Override
	protected boolean applies() {
		return auction.isOpeningBid() && pc.getHighCardPoints() >= 16
				&& pc.getHighCardPoints() <= 18 && pc.isBalanced();
	}

}
