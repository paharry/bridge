package bridge.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.bidding.Bid;
import bridge.bidding.Call;
import bridge.core.Hand;

public abstract class Rebid extends BiddingRule {
	protected Bid response;
	protected Bid opening;

	public Rebid(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		if (validOpeningAndResponse()) {
			response = auction.getPartnersLastCall().getBid();
			opening = auction.getPartnersCall(auction.getPartnersLastCall()).getBid();
		}
		return validOpeningAndResponse();
	}

	private boolean validOpeningAndResponse() {
		Call responderCall = auction.getPartnersLastCall();
		if (responderCall != null && responderCall.getBid().hasTrump()) {
			Call myOpeningBid = auction.getPartnersCall(responderCall);
			if (myOpeningBid != null && myOpeningBid.getBid().hasTrump() && auction.isOpening(myOpeningBid)) {
				return true;
			}
		}
		return false;
	}

	@Override
	abstract protected Bid prepareBid();

}
