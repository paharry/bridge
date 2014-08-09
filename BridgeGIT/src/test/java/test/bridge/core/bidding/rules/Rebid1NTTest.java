package test.bridge.core.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.Pass;
import bridge.bidding.rules.Rebid1NT;
import bridge.core.Hand;
import bridge.core.West;
import bridge.core.deck.Diamonds;
import bridge.core.deck.Hearts;
import bridge.core.deck.NoTrump;
import bridge.core.deck.Spades;
import junit.framework.TestCase;

public class Rebid1NTTest extends TestCase {
	public void testRaiseTo4IfAtLeast3CardsInColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Hearts.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,2", "A,3,2", "A,Q,8,6", "K,J,5,3"));
		assertEquals(new Bid(4, Hearts.i()), rule.getBid());

		Auctioneer a2 = new Auctioneer(West.i());
		a2.bid(new Bid(1, NoTrump.i()));
		a2.bid(new Pass());
		a2.bid(new Bid(3, Spades.i()));
		a2.bid(new Pass());
		Rebid1NT triangulate = new Rebid1NT(
				a2, new Hand("A,Q,3", "K,2", "A,8,6,3", "K,J,5,3"));
		assertEquals(new Bid(4, Spades.i()), triangulate.getBid());
	}

	public void testRaiseTo3NTIfDoubletonInColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Hearts.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertEquals(new Bid(3, NoTrump.i()), rule.getBid());
	}
	public void testDoNotRespondIfPartnerDidNotCallAMajorColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Diamonds.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertNull(rule.getBid());
	}
	public void testPassIfPartnerCalled2InAMajorColor() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, Spades.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertEquals(new Pass(), rule.getBid());
	}
	public void test2NTInvitational16HCP() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, NoTrump.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,3,2", "A,3", "A,J,8,6", "K,J,5,3"));
		assertEquals(new Pass(), rule.getBid());
	}
	public void test2NTInvitational18HCP() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(2, NoTrump.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,3,2", "A,3", "A,K,8,6", "K,J,5,3"));
		assertEquals(new Bid(3, NoTrump.i()), rule.getBid());
	}
	public void test3NTSignoff() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, NoTrump.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,3,2", "A,3", "A,K,8,6", "K,J,5,3"));
		assertEquals(new Pass(), rule.getBid());
	}
	public void testDoNotRespondIfPartnersBidWasAnOpening() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(3, Spades.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertNull(rule.getBid());
	}
	public void testDoNotRespondIfPartnersBidWasNotRespondingTo1NT() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, Spades.i()));
		a.bid(new Pass());
		a.bid(new Bid(3, Spades.i()));
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("K,3,2", "A,3", "A,Q,8,6", "K,J,5,3"));
		assertEquals(null, rule.getBid());
	}
	
	public void testBugIdentifyingPartnersBidThrowsNPE() {
		Auctioneer a = new Auctioneer(West.i());
		a.bid(new Bid(1, NoTrump.i()));
		a.bid(new Pass());
		a.bid(new Pass());
		a.bid(new Pass());
		Rebid1NT rule = new Rebid1NT(
				a, new Hand("A,K,5,2", "8,6,5,2", "A,Q,9", "A,7"));	
		assertEquals(null, rule.getBid());
	}

}
