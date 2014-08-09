package bridge.core.deck;

import bridge.core.Card;

public class Queen {
	public static Card of(Suit denomination) {
		return new Card("Q", denomination);
	}

	public static boolean isValueOf(Card card) {
		return card.getValue() == Card.strToIntValue("Q");
	}
}
