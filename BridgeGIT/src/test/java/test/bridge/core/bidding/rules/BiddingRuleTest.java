package test.bridge.core.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.Pass;
import bridge.bidding.rules.BiddingRule;
import bridge.core.West;
import bridge.core.deck.NoTrump;
import junit.framework.TestCase;

public class BiddingRuleTest extends TestCase {
	public void testDoNotBidIfSubclassRecommendsBidBelowCurrentHigh() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(2, NoTrump.i()));
		BiddingRule always1NT = new BiddingRule(a, null) {
			protected Bid prepareBid() {
				return new Bid(1, NoTrump.i());
			}

			@Override
			protected boolean applies() {
				return true;
			}
		};
		assertNull(always1NT.getBid());
	}

	public void testDoNotBidIfSubclassRecommendsBidEqualCurrentHigh() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		BiddingRule always1NT = new BiddingRule(a, null) {
			protected Bid prepareBid() {
				return new Bid(1, NoTrump.i());
			}

			@Override
			protected boolean applies() {
				return true;
			}
		};
		assertNull(always1NT.getBid());
	}

	public void testAllowSubclassToRecommendPass() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		BiddingRule alwaysPass = new BiddingRule(a, null) {
			protected Bid prepareBid() {
				return new Pass();
			}

			@Override
			protected boolean applies() {
				return true;
			}
		};
		assertEquals(new Pass(), alwaysPass.getBid());
	}
}
