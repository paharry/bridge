package test.bridge.core;

import java.util.List;

import bridge.core.Card;
import bridge.core.Hand;
import bridge.core.deck.Ace;
import bridge.core.deck.Clubs;
import bridge.core.deck.Diamonds;
import bridge.core.deck.Hearts;
import bridge.core.deck.King;
import bridge.core.deck.Three;
import bridge.core.deck.Two;
import junit.framework.TestCase;

public class HandTest extends TestCase {
  
  public void testGetColorLength() {
	Hand h = new Hand("", "", "4,3,2", "A,K");
	assertEquals(0, h.getSuitLength(Hearts.i()));
	assertEquals(3, h.getSuitLength(Diamonds.i()));
  }
  
  public void testGetColor() {
	  Hand h = new Hand(King.of(Hearts.i()), Two.of(Diamonds.i()), Ace.of(Diamonds.i()), Three.of(Clubs.i()), Three.of(Diamonds.i()));
	  List<Card> actual = h.getSuitHi2Low(Diamonds.i());
	  assertEquals(3, actual.size());
	  assertEquals(Ace.of(Diamonds.i()), actual.get(0));
	  assertEquals(Three.of(Diamonds.i()), actual.get(1));
	  assertEquals(Two.of(Diamonds.i()), actual.get(2));
  }
}
