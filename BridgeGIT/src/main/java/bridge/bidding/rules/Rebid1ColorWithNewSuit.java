package bridge.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.PointCalculator;
import bridge.core.Hand;
import bridge.core.deck.Suit;

public class Rebid1ColorWithNewSuit extends RebidToLevel1Response {

	private Suit unbidSuit;

	public Rebid1ColorWithNewSuit(Auctioneer a, Hand h) {
		super(a, h);

	}

	@Override
	protected boolean applies() {
		if (super.applies()) {
			unbidSuit = getUnbidSuitWithAtLeast4Cards();
			if (unbidSuit != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Bid prepareBid() {
		PointCalculator calculator = new PointCalculator(hand);
		int minimumBid = getMinimumBidInSuit(unbidSuit);
		if (calculator.getCombinedPoints() >= 19) {
			Bid bid = new Bid(minimumBid + 1, unbidSuit);
			bid.makeGameForcing();
			return bid;
		}
		if ((minimumBid == 2 && !calculator.isBalanced())) {
			if (calculator.getCombinedPoints() >= 16 || unbidSuit.isLowerRankThan(opening.getTrump())) {
				return new Bid(minimumBid, unbidSuit);
			}
		}
		if (minimumBid == 1) {
			return new Bid(minimumBid, unbidSuit);
		}

		return null;
	}

	private int getMinimumBidInSuit(Suit suit) {
		if (auction.isValid(new Bid(1, suit))) {
			return 1;
		} else {
			return 2;
		}
	}

	private Suit getUnbidSuitWithAtLeast4Cards() {
		for (Suit color : Suit.list) {
			if (hand.getSuitLength(color) >= 4 && hasNotBeenBid(color)) {
				return color;
			}
		}
		return null;
	}

	private boolean hasNotBeenBid(Suit suit) {
		return !suit.equals(response.getTrump()) && !suit.equals(opening.getTrump());
	}

}
