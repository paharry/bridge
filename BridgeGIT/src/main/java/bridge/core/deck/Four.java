package bridge.core.deck;

import bridge.core.Card;

public class Four {
	public static Card of(Suit denomination) {
		return new Card("4", denomination);
	}
}
