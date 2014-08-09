package bridge.bidding.rules;

import bridge.bidding.Auctioneer;
import bridge.core.Hand;

public abstract class RebidToLevel1Response extends Rebid {

	public RebidToLevel1Response(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		return super.applies() && response.getValue() == 1;
	}

}
