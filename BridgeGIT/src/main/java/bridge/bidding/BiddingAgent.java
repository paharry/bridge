package bridge.bidding;

import java.util.ArrayList;
import java.util.List;

import bridge.bidding.rules.AlwaysPass;
import bridge.bidding.rules.BiddingRule;
import bridge.bidding.rules.Open1Color;
import bridge.bidding.rules.Open1NT;
import bridge.bidding.rules.Overcall1NT;
import bridge.bidding.rules.OvercallSuit;
import bridge.bidding.rules.Rebid1ColorOriginalSuit;
import bridge.bidding.rules.Rebid1ColorRaisePartner;
import bridge.bidding.rules.Rebid1ColorWithNT;
import bridge.bidding.rules.Rebid1ColorWithNewSuit;
import bridge.bidding.rules.Rebid1NT;
import bridge.bidding.rules.Respond1ColorRaiseMajorSuit;
import bridge.bidding.rules.Respond1ColorRaiseMinorSuit;
import bridge.bidding.rules.Respond1ColorWithNT;
import bridge.bidding.rules.Respond1ColorWithNewSuit;
import bridge.bidding.rules.Respond1NT;
import bridge.bidding.rules.RespondOvercallSuit;
import bridge.core.Hand;


public class BiddingAgent {

	private final List<BiddingRule> rules;

	/**
	 * @param
	 * @return
	 */	
	public BiddingAgent(Auctioneer a, Hand h) {
		rules = new ArrayList<>();
		rules.add(new Open1NT(a, h));
		rules.add(new Open1Color(a, h));
		rules.add(new Respond1NT(a, h));
		rules.add(new Respond1ColorRaiseMajorSuit(a, h));
		rules.add(new Respond1ColorWithNewSuit(a, h));
		rules.add(new Respond1ColorRaiseMinorSuit(a, h));
		rules.add(new Respond1ColorWithNT(a, h));
		rules.add(new Rebid1NT(a, h));
		rules.add(new Rebid1ColorRaisePartner(a, h));
		rules.add(new Rebid1ColorWithNewSuit(a, h));
		rules.add(new Rebid1ColorOriginalSuit(a, h));
		rules.add(new Rebid1ColorWithNT(a, h));
		rules.add(new OvercallSuit(a, h));
		rules.add(new RespondOvercallSuit(a, h));
		rules.add(new Overcall1NT(a, h));
		rules.add(new AlwaysPass());
	}

	/**
	 * @param
	 * @return
	 */
	public Bid getBid() {
		Bid result = null;
		for (BiddingRule rule : rules) {
			result = rule.getBid();
			if (result != null) {
				//System.out.println("rule: " + rule.getClass() + " recommends: " + result);
				break;
			}
		}
		return result;
	}

}