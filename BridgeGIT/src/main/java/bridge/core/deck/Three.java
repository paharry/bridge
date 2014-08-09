package bridge.core.deck;

import bridge.core.Card;

public class Three {
	public static Card of(Suit denomination) {
		return new Card("3", denomination);
	}
}
