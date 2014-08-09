package bridge.presentation.gui;

import bridge.core.Card;

public interface CardPlayedListener {
	public void playCard(Card c);

	public void forceMove();

	public void displayPreviousTrick();

	public void newGame();
}
