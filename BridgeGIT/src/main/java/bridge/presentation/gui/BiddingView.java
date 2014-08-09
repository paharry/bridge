package bridge.presentation.gui;

import bridge.bidding.Auctioneer;
import bridge.core.Hand;

public interface BiddingView {

	public abstract void setCards(Hand hand);

	public abstract void setAuction(Auctioneer auction);

	public abstract void auctionStateChanged();

	public abstract void display(String msg);

	public abstract void hide();

	public abstract void show();

	public abstract void setController(BiddingController c);

	public abstract void displayScore(ScoringTracker scoringTracker);
}
