package bridge.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.PointCalculator;
import bridge.core.Hand;
import bridge.core.deck.Clubs;
import bridge.core.deck.Diamonds;
import bridge.core.deck.Suit;
import bridge.core.deck.Trump;

public class Open1Color extends BiddingRule {

	private PointCalculator pc;

	public Open1Color(Auctioneer a, Hand h) {
		super(a, h);
		pc = new PointCalculator(hand);
	}

	@Override
	protected boolean applies() {
		return auction.isOpeningBid() && pc.getCombinedPoints() >= 13;
	}

	@Override
	protected Bid prepareBid() {
		Bid result = null;

		Suit highest = null;
		for (Suit color : Suit.list) {
			if (hand.getSuitLength(color) >= 5) {
				if (highest == null) {
					highest = color;
				} else if (hand.getSuitLength(color) > hand
						.getSuitLength(highest)) {
					highest = color;
				}
			}
		}
		if (highest != null) {
			result = new Bid(1, highest);
		} else {
			result = new Bid(1, getStrongerMinor());
		}
		return result;
	}

	private Trump getStrongerMinor() {
		Trump result = null;
		if (hand.getSuitLength(Clubs.i()) > hand.getSuitLength(Diamonds.i())) {
			result = Clubs.i();
		} else if (hand.getSuitLength(Clubs.i()) == 3
				&& hand.getSuitLength(Diamonds.i()) == 3) {
			if (pc.getHighCardPoints(hand.getSuitHi2Low(Clubs.i())) > pc
					.getHighCardPoints(hand.getSuitHi2Low(Diamonds.i()))) {
				result = Clubs.i();
			} else {
				result = Diamonds.i();
			}
		} else {
			result = Diamonds.i();
		}
		return result;
	}
}
