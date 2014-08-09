package bridge.core.deck;

import bridge.core.Card;

public class Seven {
	public static Card of(Suit denomination) {
		return new Card("7", denomination);
	}
}
