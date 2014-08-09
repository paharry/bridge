package bridge.presentation.gui;

import java.awt.Point;

import bridge.core.Card;
import bridge.core.Deal;
import bridge.core.Direction;
import bridge.core.East;
import bridge.core.Hand;
import bridge.core.North;
import bridge.core.South;
import bridge.core.West;
import bridge.core.deck.Suit;

public class OneColumnPerColor extends HandDisplay {

	public OneColumnPerColor(Direction human, Direction player, Deal game, CardPanelHost owner) {
		super(human, player, game, owner);
	}

	final static int CARD_OFFSET = 30;

	@Override
	public void display() {
		dispose(cards);
		Hand hand = new Hand(game.getPlayer(player).getHand());
		Point upperLeft = calculateUpperLeft(human, player);
		for (Suit color : Suit.list) {
			int j = 0;
			for (Card card : hand.getSuitHi2Low(color)) {
				CardPanel cardPanel = new CardPanel(card);
				cards.add(cardPanel);
				if (human.equals(South.i())) {
					cardPanel.setPlayable(true);
				}
				owner.addCard(cardPanel);
				cardPanel.setLocation((int) upperLeft.getX(), (int) upperLeft.getY() + CARD_OFFSET * j);
				j++;
			}
			upperLeft.setLocation(upperLeft.getX() + CardPanel.IMAGE_WIDTH + 2, upperLeft.getY());
		}

	}

	private Point calculateUpperLeft(Direction human, Direction player) {
		Direction slot = new HumanAlwaysOnBottom(human).mapRelativeTo(player);
		if (North.i().equals(slot)) {
			return new Point(235, 5);
		} else if (West.i().equals(slot)) {
			return new Point(3, owner.getTotalHeight() - 500);
		} else if (East.i().equals(slot)) {
			return new Point(512, owner.getTotalHeight() - 500);
		} else if (South.i().equals(slot)) {
			return new Point(235, owner.getTableBottom() + 1);

		}
		throw new RuntimeException("unknown direction");
	}

}
