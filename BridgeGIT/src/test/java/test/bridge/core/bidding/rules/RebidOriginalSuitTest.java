package test.bridge.core.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.Pass;
import bridge.bidding.rules.Rebid1ColorOriginalSuit;
import bridge.core.Hand;
import bridge.core.West;
import bridge.core.deck.Clubs;
import bridge.core.deck.Diamonds;
import bridge.core.deck.Hearts;
import bridge.core.deck.NoTrump;
import bridge.core.deck.Spades;
import junit.framework.TestCase;

public class RebidOriginalSuitTest extends TestCase {
	public void testRebidAt2Level() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorOriginalSuit rule = new Rebid1ColorOriginalSuit(a, new Hand("3,2", "K,Q,J", "9,8", "A,K,5,4,3,2"));
		assertEquals(new Bid(2, Clubs.i()), rule.getBid());
	}

	public void testRebidAt3Level() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Rebid1ColorOriginalSuit rule = new Rebid1ColorOriginalSuit(a, new Hand("3,2", "A,K,5,4,3,2", "K,Q,J", "K,8"));
		assertEquals(new Bid(3, Hearts.i()), rule.getBid());
	}

	public void testRebidAt4Level() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Hearts.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		Rebid1ColorOriginalSuit rule = new Rebid1ColorOriginalSuit(a, new Hand("K,2", "A,K,J,5,4,3,2", "K,Q", "K,8"));
		assertEquals(new Bid(4, Hearts.i()), rule.getBid());
	}

	public void testDoNotRebidIfLessThan6Cards() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Clubs.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorOriginalSuit rule = new Rebid1ColorOriginalSuit(a, new Hand("3,2", "K,Q,J", "9,8,2", "A,K,5,4,3"));
		assertEquals(null, rule.getBid());
	}

	public void testDoNotRebidNT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(1, Diamonds.i()));
		a.bid(new Pass());
		Rebid1ColorOriginalSuit rule = new Rebid1ColorOriginalSuit(a, new Hand("3,2", "K,Q,J", "9,8", "A,K,5,4,3,2"));
		assertEquals(null, rule.getBid());
	}
}
