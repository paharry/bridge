package bridge.core.deck;

import bridge.core.Card;

public class Ten {
	   public static Card of(Suit denomination) {
		   return new Card("10", denomination);
	   }
}
