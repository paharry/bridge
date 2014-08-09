package bridge.core.deck;

import bridge.core.Card;

public class Jack {
	public static Card of(Suit denomination) {
		return new Card("J", denomination);
	}

	public static boolean isValueOf(Card card) {
		return card.getValue() == Card.strToIntValue("J");
	}
}
