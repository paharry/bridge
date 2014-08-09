package test.bridge.core.bidding;

import static bridge.core.Direction.*;
import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.BiddingAgent;
import bridge.core.Hand;
import junit.framework.TestCase;

public abstract class BiddingAgentTestCase extends TestCase {
	Auctioneer auctioneer;
	BiddingAgent agent;

	protected void expectPlayerToBid(Bid bid) {
		assertEquals(bid, agent.getBid());

	}

	protected void andPlayersCards(String... cardsBySuits) {
		agent = new BiddingAgent(auctioneer, new Hand(cardsBySuits));

	}

	protected void givenPlayersCards(String... cardsBySuits) {
		givenNoPriorBids();
		agent = new BiddingAgent(auctioneer, new Hand(cardsBySuits));

	}

	protected void givenNoPriorBids() {
		auctioneer = new Auctioneer(WEST);

	}

	protected void givenBidding(Bid... bids) {
		givenNoPriorBids();
		for (Bid bid : bids) {
			auctioneer.bid(bid);
		}

	}
}
