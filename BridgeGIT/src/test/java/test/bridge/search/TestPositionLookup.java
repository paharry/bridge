package test.bridge.search;

import bridge.core.Deal;
import bridge.core.East;
import bridge.core.Hand;
import bridge.core.North;
import bridge.core.South;
import bridge.core.West;
import bridge.core.deck.Ace;
import bridge.core.deck.Clubs;
import bridge.core.deck.Diamonds;
import bridge.core.deck.Eight;
import bridge.core.deck.Five;
import bridge.core.deck.Four;
import bridge.core.deck.Hearts;
import bridge.core.deck.Nine;
import bridge.core.deck.NoTrump;
import bridge.core.deck.Seven;
import bridge.core.deck.Spades;
import bridge.core.deck.Ten;
import bridge.core.deck.Three;
import bridge.core.deck.Two;
import bridge.presentation.GameUtils;
import bridge.search.Node;
import bridge.search.PositionLookup;
import junit.framework.TestCase;

public class TestPositionLookup extends TestCase {
	public void testSameObjectShownTwice() {
		Deal g = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();
		Node node = new Node(null);
		assertFalse(pl.positionEncountered(g, node.getTricksTaken()));
		assertTrue(pl.positionEncountered(g, node.getTricksTaken()));
		assertEquals(node.getTricksTaken(), pl.getNode(g));
	}

	@SuppressWarnings("unused")
	public void testOnlyReturnFirstNodeEncountetredForThePosition() {
		Deal g = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();
		Node node = new Node(null);
		boolean justPresentThePosition = pl.positionEncountered(g, node.getTricksTaken());

		Deal identicalTwin = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(identicalTwin);
		identicalTwin.playOneTrick();

		Node identicalTwinNode = new Node(null);
		assertTrue(pl.positionEncountered(identicalTwin, identicalTwinNode.getTricksTaken()));
		assertEquals(node.getTricksTaken(), pl.getNode(identicalTwin));

	}

	@SuppressWarnings("unused")
	public void testDistinguishDifferentPlays() {
		Deal g = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g, null);

		Deal g2 = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g2);
		playOneTrickWithSlightTwist(g2);

		assertFalse(pl.positionEncountered(g2, null));
	}

	private void playOneTrickWithSlightTwist(Deal g2) {
		g2.play(g2.getNextToPlay().getHand().get(1));
		for (int i = 0; i < 3; i++) {
			g2.play(g2.getNextToPlay().getHand().get(0));
		}

	}

	@SuppressWarnings("unused")
	public void testOneCardPlayedDifferentObjectsSamePosition() {
		Deal g = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();
		Node node = new Node(null);
		boolean justPresentThePosition = pl.positionEncountered(g, node.getTricksTaken());

		Deal g2 = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g2);
		g2.playOneTrick();
		assertTrue(pl.positionEncountered(g2, null));
		assertEquals(node.getTricksTaken(), pl.getNode(g2));
	}

	public void testOneCardPlayedDifferentCards() {
		Deal g = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.play(g.getNextToPlay().getHand().get(0));

		PositionLookup pl = new PositionLookup();
		@SuppressWarnings("unused")
		boolean justPresentThePosition = pl.positionEncountered(g, null);

		Deal gameWithDifferentCardPlayed = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(gameWithDifferentCardPlayed);
		gameWithDifferentCardPlayed.play(gameWithDifferentCardPlayed.getNextToPlay().getHand().get(1));
		assertFalse(pl.positionEncountered(gameWithDifferentCardPlayed, null));
	}

	@SuppressWarnings("unused")
	public void testCanRememberMoreThanOnePosition() {
		Deal g = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();

		Deal gameWithDifferentCardPlayed = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(gameWithDifferentCardPlayed);
		playOneTrickWithSlightTwist(gameWithDifferentCardPlayed);

		Node node = new Node(null);
		Node nodeWithDifferentCardPlayed = new Node(null);
		boolean justPresentThePosition = pl.positionEncountered(gameWithDifferentCardPlayed,
				nodeWithDifferentCardPlayed.getTricksTaken());
		justPresentThePosition = pl.positionEncountered(g, node.getTricksTaken());

		assertTrue(pl.positionEncountered(g, null));
		assertEquals(node.getTricksTaken(), pl.getNode(g));
		assertTrue(pl.positionEncountered(gameWithDifferentCardPlayed, null));
		assertEquals(nodeWithDifferentCardPlayed.getTricksTaken(), pl.getNode(gameWithDifferentCardPlayed));
	}

	@SuppressWarnings("unused")
	public void testTwoTricksPlayedSameFirstTrick() {
		Deal g = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();
		g.playOneTrick();

		Deal sameFirstTrick = new Deal(NoTrump.i());
		GameUtils.initializeSingleColorSuits(sameFirstTrick);
		sameFirstTrick.playOneTrick();
		playOneTrickWithSlightTwist(sameFirstTrick);

		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g, null);

		assertFalse(pl.positionEncountered(sameFirstTrick, null));

	}

	public void testDistinguishNumberOfTricks() {
		Deal g = new Deal(Spades.i());
		g.getPlayer(West.i()).init(new Hand("", "3,2", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(new Hand("7", "", "8", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(new Hand("", "", "", "4,5").getCardsHighToLow());
		g.getPlayer(South.i()).init(new Hand("", "", "", "10,9").getCardsHighToLow());

		Deal differentOrder = g.duplicate();

		g.play(Three.of(Hearts.i()));
		g.play(Eight.of(Diamonds.i()));
		g.play(Four.of(Clubs.i()));
		g.play(Ten.of(Clubs.i()));

		g.play(Two.of(Hearts.i()));
		g.play(Seven.of(Spades.i()));
		g.play(Five.of(Clubs.i()));
		g.play(Nine.of(Clubs.i()));

		PositionLookup pl = new PositionLookup();
		Node node = new Node(null);
		@SuppressWarnings("unused")
		boolean justPresentThePosition = pl.positionEncountered(g, node.getTricksTaken());
		assertTrue(pl.positionEncountered(g, null));
		assertEquals(node.getTricksTaken(), pl.getNode(g));

		differentOrder.play(Three.of(Hearts.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Four.of(Clubs.i()));
		differentOrder.play(Ten.of(Clubs.i()));

		differentOrder.play(Eight.of(Diamonds.i()));
		differentOrder.play(Five.of(Clubs.i()));
		differentOrder.play(Nine.of(Clubs.i()));
		differentOrder.play(Two.of(Hearts.i()));
		Node differentOrderNode = new Node(null);
		assertFalse(pl.positionEncountered(differentOrder, differentOrderNode.getTricksTaken()));
		assertEquals(differentOrderNode.getTricksTaken(), pl.getNode(differentOrder));
	}

	public void testDistinguishPlayerTurn() {
		Deal g = new Deal(Spades.i());
		g.getPlayer(West.i()).init(new Hand("10", "3", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(new Hand("7", "8", "", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(new Hand("", "", "", "4,5").getCardsHighToLow());
		g.getPlayer(South.i()).init(new Hand("", "", "", "10,9").getCardsHighToLow());

		Deal differentOrder = g.duplicate();

		g.play(Three.of(Hearts.i()));
		g.play(Eight.of(Hearts.i()));
		g.play(Four.of(Clubs.i()));
		g.play(Ten.of(Clubs.i()));

		g.play(Seven.of(Spades.i()));
		g.play(Five.of(Clubs.i()));
		g.play(Nine.of(Clubs.i()));
		g.play(Ten.of(Spades.i()));

		Node node = new Node(null);
		PositionLookup pl = new PositionLookup();

		@SuppressWarnings("unused")
		boolean justPresentThePosition = pl.positionEncountered(g, node.getTricksTaken());
		assertTrue(pl.positionEncountered(g, null));

		differentOrder.play(Ten.of(Spades.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Four.of(Clubs.i()));
		differentOrder.play(Ten.of(Clubs.i()));

		differentOrder.play(Three.of(Hearts.i()));
		differentOrder.play(Eight.of(Hearts.i()));
		differentOrder.play(Five.of(Clubs.i()));
		differentOrder.play(Nine.of(Hearts.i()));
		assertFalse(pl.positionEncountered(differentOrder, null));
	}

	public void testOnlyApplyToCompletedTricks() {
		Deal g = new Deal(Spades.i());
		g.getPlayer(West.i()).init(new Hand("A,3", "", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(new Hand("7,2", "", "", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(new Hand("", "", "5,4", "").getCardsHighToLow());
		g.getPlayer(South.i()).init(new Hand("", "", "", "7,6").getCardsHighToLow());

		Deal differentOrder = g.duplicate();

		g.play(Ace.of(Spades.i()));
		g.play(Two.of(Spades.i()));
		g.play(Five.of(Diamonds.i()));
		g.play(Seven.of(Clubs.i()));

		g.play(Three.of(Spades.i()));
		g.play(Seven.of(Spades.i()));

		PositionLookup pl = new PositionLookup();
		@SuppressWarnings("unused")
		boolean justPresentThePosition = pl.positionEncountered(g, null);

		differentOrder.play(Ace.of(Spades.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Five.of(Diamonds.i()));
		differentOrder.play(Seven.of(Clubs.i()));

		differentOrder.play(Three.of(Spades.i()));
		differentOrder.play(Two.of(Spades.i()));
		assertFalse(pl.positionEncountered(differentOrder, null));
	}

	// public void testLengthOfUniqueString() {
	// Game g = new Game(NoTrump.i());
	// GameUtils.initializeSingleColorSuits(g, 13);
	//		
	// for (int i=0; i< 13; i++) {
	// g.playOneTrick();
	// }
	//		
	// System.out.println(g.getUniqueString());
	// System.out.println(g.getUniqueString().length());
	// }

	// public void testPrimes() {
	// int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
	// 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
	// 73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
	// 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
	// 179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
	// 233, 239, 241, 251, 257, 263, 269, 271, 277, 281};
	//		
	// int i = 1;
	// BigInteger total = new BigInteger("1");
	// for (int prime : primes) {
	// total = total.multiply(new BigInteger(""+ prime));
	// System.out.println(i+"("+prime+"): "+total.toString(Character.MAX_RADIX));
	// i++;
	// }
	// }

}
