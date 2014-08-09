package test.bridge.core.bidding.rules;

import static bridge.bidding.Bid.*;
import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.Pass;
import bridge.bidding.rules.Response;
import bridge.core.Hand;
import bridge.core.West;
import bridge.core.deck.Clubs;
import bridge.core.deck.Diamonds;
import bridge.core.deck.NoTrump;
import junit.framework.TestCase;

public class ResponseTest extends TestCase {
	public void testOpeningIsNotAResponses() {
		Auctioneer a = new Auctioneer(West.i());
		Response rule = new Response(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a response situation");
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testPartnersPassIsNotAResponses() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Pass());
		a.bid(new Pass());
		Response rule = new Response(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a response situation");
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testPartnersDoubleIsNotAResponses() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(ONE_CLUBS);
		a.bid(DOUBLE);
		a.bid(PASS);
		Response rule = new Response(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a response situation");
			}

		};
		assertEquals(null, rule.getBid());
	}

	public void testMakeAResponse() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());

		Response rule = new Response(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				return new Bid(7, NoTrump.i());
			}

		};
		assertEquals(new Bid(7, NoTrump.i()), rule.getBid());
	}

	public void testRebidNotAResponses() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Response rule = new Response(a, new Hand("3,2", "K,Q,J,2", "9,8", "A,K,5,4,3")) {
			@Override
			protected Bid prepareBid() {
				throw new RuntimeException("should not try to prepare bid when it's not a response situation");
			}

		};
		assertEquals(null, rule.getBid());
	}

}
