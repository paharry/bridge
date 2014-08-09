/**
 * Calculates the losing trick count of hand
 */
package bridge.bidding;

import java.util.List;

import bridge.core.Card;
import bridge.core.Hand;
import bridge.core.deck.Ace;
import bridge.core.deck.Jack;
import bridge.core.deck.King;
import bridge.core.deck.Queen;
import bridge.core.deck.Suit;

/**
 * @author pah
 *
 */
public class LosingTrickCalculator {

	protected Hand hand;

	// private int ltc =12; // max value of losing trick count per hand

	public LosingTrickCalculator(Hand hand) {
		this.hand = hand;
	}

	public int LosingTrickCount() {

		int ltc = 0;
		for (Suit suit : Suit.list) {
			ltc += getLTCperSuit(hand.getSuitHi2Low(suit));
			System.out.print("\tLTC is now =\t" + ltc);
		}
		
		return ltc;
	}

	private int getLTCperSuit(List<Card> cardList) {

		System.out.print("\nSuit is =\t" + cardList);

		int suitLTC = cardList.size();
		switch (suitLTC) {
		case 0: // void no losers!
			return 0;
		case 1: // singleton 1 lose unless its an Ace
			if (Ace.isValueOf(cardList.get(0)))
				return 0;
			else
				return 1;
		default: // 2 or more cards in the suit
			if (suitLTC > 3)
				suitLTC = 3; // max per suit

			for (Card card : cardList) {
				if (Ace.isValueOf(card)) {
					suitLTC--;
				} else if (King.isValueOf(card)) {
					suitLTC--;
				} else if (Queen.isValueOf(card)) {
					suitLTC--;
				}
			}
			// @TODO we have q** always 2 , 
			// should be 3 except for queen Q** =3 unless trump suit or  QJ* or QT* 2 tricks
			return suitLTC;
		}

	}

}
