package test.bridge.core.bidding.rules;

import static bridge.bidding.Bid.*;
import static bridge.core.Direction.*;
import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.rules.TakeoutDouble;
import bridge.core.Hand;
import junit.framework.TestCase;

public class TakeoutDoubleTest extends TestCase {
	private Auctioneer auctioneer;
	private TakeoutDouble rule;

	private void givenNoPriorBids() {
		auctioneer = new Auctioneer(WEST);

	}

	private void givenBidding(Bid... bids) {
		givenNoPriorBids();
		for (Bid bid : bids) {
			auctioneer.bid(bid);
		}

	}

	private void ruleShouldBid(Bid bid) {
		assertEquals(bid, rule.getBid());

	}

	private void andPlayersCards(String... cardsBySuits) {
		rule = new TakeoutDouble(auctioneer, new Hand(cardsBySuits));

	}

	//TODO: work here next
	public void testTakeout() {
		givenBidding(ONE_CLUBS);
		andPlayersCards("A,K,2", "A,Q,3", "8,6,5,3", "K,J,3");
		ruleShouldBid(null);
	}

}
