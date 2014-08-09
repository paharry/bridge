package bridge.core.deck;

import bridge.core.Card;

public class Nine {
	public static Card of(Suit denomination) {
		return new Card("9", denomination);
	}
}
