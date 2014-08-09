package bridge.bidding.rules;

import static bridge.bidding.Bid.ONE_NOTRUMP;
import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.PointCalculator;
import bridge.core.Hand;


public class Overcall1NT extends BiddingRule {

	private final PointCalculator pc;

	public Overcall1NT(Auctioneer a, Hand h) {
		super(a, h);
		pc = new PointCalculator(hand);
	}

	@Override
	protected boolean applies() {
		return auction.mayOvercall() && pc.getHighCardPoints() >= 16 && pc.getHighCardPoints() <= 18 && pc.isBalanced()
				&& haveStopperInEnemySuit();
	}

	@Override
	protected Bid prepareBid() {
		return ONE_NOTRUMP;
	}
}