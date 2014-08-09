package bridge.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.PointCalculator;
import bridge.core.Hand;

public class Rebid1ColorOriginalSuit extends RebidToLevel1Response {

	public Rebid1ColorOriginalSuit(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		return super.applies() && opening.getTrump().isSuit();
	}

	@Override
	protected Bid prepareBid() {
		if (hand.getSuitLength(opening.getTrump().asSuit()) >= 6) {
			int combinedPoints = new PointCalculator(hand).getCombinedPoints();
			if (combinedPoints <= 15) {
				return new Bid(2, opening.getTrump());
			}
			if (combinedPoints >= 16 && combinedPoints <= 18) {
				return new Bid(3, opening.getTrump());

			}
			if (combinedPoints >= 19 && hand.getSuitLength(opening.getTrump().asSuit()) >= 7) {
				return new Bid(4, opening.getTrump());
			}
		}
		return null;
	}

}
