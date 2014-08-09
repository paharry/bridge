package test.bridge.core.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.Pass;
import bridge.bidding.rules.RebidToLevel1Response;
import bridge.core.Hand;
import bridge.core.West;
import bridge.core.deck.Diamonds;
import bridge.core.deck.Hearts;
import junit.framework.TestCase;

public class RebidTo1ResponseTest extends TestCase {
	public void testOnlyApplyToRebidsFrom1LevelResponseAsPerLesson5() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Diamonds.i()));
		a.bid(new Pass());
		RebidToLevel1Response rule = new RebidToLevel1Response(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when partner responsed above level 1");
			}

		};
		assertEquals(null, rule.getBid());
	}
}
