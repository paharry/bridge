package bridge.core.deck;

import bridge.core.Card;

public class Five {
	public static Card of(Suit denomination) {
		return new Card("5", denomination);
	}
}
