package bridge.core.deck;

import bridge.core.Card;

public class Six {
	public static Card of(Suit denomination) {
		return new Card("6", denomination);
	}
}
