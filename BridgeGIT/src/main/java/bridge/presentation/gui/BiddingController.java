package bridge.presentation.gui;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.BiddingAgent;
import bridge.core.Deal;
import bridge.core.Direction;
import bridge.core.Hand;
import bridge.core.North;
import bridge.core.Player;
import bridge.core.South;
import bridge.core.West;

public class BiddingController {

	private final BiddingView view;
	private final Deal cardHolder;
	private final Auctioneer auction;
	private final Player human;
	private final MainController parent;

	public BiddingController(BiddingView v, MainController p, ScoringTracker scoringTracker) {
		view = v;
		view.setController(this);
		view.show();
		parent = p;
		cardHolder = Deal.construct();
		auction = new Auctioneer(West.i());
		view.setAuction(auction);
		human = cardHolder.selectHumanPlayer();
		view.setCards(new Hand(human.getHand()));
		doAutomatedBidding();
		view.displayScore(scoringTracker);
	}

	public Deal getCardHolder() {
		return cardHolder;
	}

	public Auctioneer getAuction() {
		return auction;
	}

	public Player getHuman() {
		return human;
	}

	private void doAutomatedBidding() {
		while (!auction.biddingFinished() && !auction.getNextToBid().equals(human.getDirection2())) {
			Hand hand = new Hand(cardHolder.getPlayer(auction.getNextToBid().getValue()).getHand());
			BiddingAgent ba = new BiddingAgent(auction, hand);
			auction.bid(ba.getBid());
			view.auctionStateChanged();
		}

	}

	public void placeBid(int bidSize, String trump) {
		if (!auction.biddingFinished()) {
			if (!auction.getNextToBid().equals(human.getDirection2())) {
				view.display("Not your turn to bid");
				return;
			}
			Bid candidate = null;
			candidate = Bid.makeBid(bidSize, trump);
			if (!auction.isValid(candidate)) {
				view.display("Invalid bid: " + candidate);
				return;
			}
			auction.bid(candidate);
			view.display("Bid placed:" + candidate);
			view.auctionStateChanged();
			doAutomatedBidding();
		}
	}

	public void playGame() {
		view.hide();
		parent.playGame();

	}

	public Direction allowHumanToPlayIfDummy() {
		Direction newHuman = auction.getDummyOffsetDirection(getHuman().getDirection2());
		if (North.i().equals(newHuman)) {
			newHuman = South.i();
		}
		return newHuman;
	}

	public void newGame() {
		view.hide();
		parent.newGame();

	}
}
