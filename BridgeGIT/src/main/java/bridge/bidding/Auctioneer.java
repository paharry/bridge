package bridge.bidding;

import static bridge.bidding.Bid.*;
import static bridge.core.Direction.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bridge.core.Direction;
import bridge.core.deck.Trump;

public class Auctioneer {
	private Direction nextToBid;
	private int passCount;
	private Bid highBid;
	private int bidCount;
	private Call last;
	private Call beforeLast;
	private final List<Call> calls;

	/**
	 * Constructor
	 * 
	 * @param firstToBid
	 * @return none it is an constructor
	 */
	public Auctioneer(Direction firstToBid) {
		this.nextToBid = firstToBid;
		bidCount = 0;
		last = null;
		beforeLast = null;
		calls = new ArrayList<>();
	}


	/**
	 * @param The bid b
	 * @return none
	 * 
	 * With newBidupdate theAutioneer records
	 */
	public void bid(Bid newBid) {
		Bid bid = Bid.cloneBid(newBid);
		beforeLast = last;
		last = new Call(bid, nextToBid);
		calls.add(last);
		bidCount++;
		if (bid.isPass()) {
			passCount++;
		} else {
			passCount = 0;
			if (DOUBLE.equals(bid)) {
				getHighBid().makeDoubled();
			} else {
				highBid = bid;
			}
		}
		nextToBid = nextToBid.clockwise();
	}

	/**
	 * Tests when the bidding process has completed
	 * 
	 * @param
	 * @return True if finished
	 */
	public boolean biddingFinished() {
		return (passCount == 3 && highBid != null) || passCount == 4;
	}

	/**
	 * Do we have an opening bid?
	 * 
	 * @param True if we have opening bid
	 * @return
	 */
	public boolean isOpeningBid() {
		for (Call call : calls) {
			if (!call.isPass()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * What was partners last call?
	 * 
	 * @param Your call
	 * @return partners call
	 */
	public Call getPartnersCall(Call playerCall) {
		int current = calls.indexOf(playerCall);
		if (current >= 2) {
			return calls.get(current - 2);
		} else {
			return null;
		}
	}

	/**
	 * is the bid valid?
	 * 
	 * @param  candidate bid to be validated 
	 * @return true for valid bid
	 */
	public boolean isValid(Bid candidate) {
		boolean result = false;
		if (candidate != null) {
			if (candidate.equals(DOUBLE)) {
				if (getHighCall() != null
						&& !getHighCall().pairMatches(nextToBid)
						&& !getHighBid().isDoubled()) {
					return true;
				}
			} else if (candidate.isPass()
					|| candidate.greaterThan(getHighBid())) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * @param
	 * @return
	 */
	public Direction getDummy() {
		Direction result = null;
		if (biddingFinished() && getHighCall() != null) {
			for (Call call : calls) {
				if (call.getBid().hasTrump()
						&& call.getTrump().equals(getHighCall().getTrump())
						&& call.pairMatches(getHighCall().getDirection())) {
					result = call.getDirection().opposite();
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Obtain the highest bid
	 * 
	 * @param none
	 * @return Call the highest bid
	 */
	public Call getHighCall() {
		Bid localHighBid = this.highBid;
		for (Call call : calls) {
			if (call.getBid().equals(localHighBid)) {
				return call;
			}
		}
		return null;
	}

	/**
	 * The parties in bidding are referred to by directions of the world, but
	 * these are not the same directions as the ones during play. This method
	 * provides a way to find the offset from what this class considers a
	 * direction and what direction ends up being when the contract is played.
	 * 
	 * ie: if auction's West becomes the dummy (South during play), the offset
	 * is 1 move clockwise, and when given South as parameter, this method
	 * returns West.
	 * 
	 * @param original
	 *            the original direcction
	 * @return offset = 1 means move clockwise
	 */
	public Direction getDummyOffsetDirection(Direction original) {
		Direction d = getDummy();
		Direction offset = original;
		for (int i = 0; i < 4; i++) {
			if (d.equals(NORTH)) {
				break;
			} else {
				d = d.clockwise();
				offset = offset.clockwise();
			}
		}
		return offset;
	}

	/**
	 * @param
	 * @return
	 */
	public boolean mayOvercall() {
		if (bidCount == 1) {
			if (firstBid().is1Suit()) {
				return true;
			}
		} else if (bidCount == 2) {
			if (firstBid().isPass() && secondBid().is1Suit()) {
				return true;
			}
		} else if (bidCount == 3) {
			if (firstBid().isPass() && secondBid().isPass()
					&& thirdBid().is1Suit()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param
	 * @return
	 */
	private Bid thirdBid() {
		return calls.get(2).getBid();
	}

	/**
	 * @param
	 * @return
	 */
	private Bid secondBid() {
		return calls.get(1).getBid();
	}

	/**
	 * @param
	 * @return
	 */
	private Bid firstBid() {
		return calls.get(0).getBid();
	}

	/**
	 * @param
	 * @return
	 */
	private int getCallOrderZeroBased(Bid bid) {
		int result = -1;
		for (Call call : calls) {
			result++;
			if (bid.equals(call.getBid())) {
				return result;
			}
		}
		return -1;
	}

	/**
	 * @param
	 * @return
	 */
	public boolean isOvercall(Bid bid) {
		if (PASS.equals(bid)) {
			return false;
		}
		boolean result = false;
		int callOrder = getCallOrderZeroBased(bid);
		if (callOrder == 1 && !PASS.equals(calls.get(0).getBid())) {
			return true;
		}
		return result;
	}

	/**
	 * Identify what are the opposition (enemy?) trumps 
	 * @param
	 * @return
	 */
	public Set<Trump> getEnemyTrumps() {
		Set<Trump> result = new HashSet<>();
		List<Call> reversedCalls = getCalls();
		Collections.reverse(reversedCalls);
		boolean enemyBid = true;
		for (Call call : reversedCalls) {
			if (call.getBid().hasTrump() && enemyBid) {
				result.add(call.getTrump());
			}
			enemyBid = !enemyBid;
		}

		return result;
	}

	/**
	 * @param
	 * @return
	 */
	public boolean isOpening(Call callWithTrump) {
		int index = getCallOrderZeroBased(callWithTrump.getBid());
		if (index == 0) {
			return true;
		}
		if (index == 1 && calls.get(0).isPass()) {
			return true;
		}
		if (index == 2 && calls.get(0).isPass() && calls.get(1).isPass()) {
			return true;
		}
		if (index == 3 && calls.get(0).isPass() && calls.get(1).isPass()
				&& calls.get(2).isPass()) {
			return true;
		}
		return false;
	}

/*
 * getters and setters	
 */

	public Bid getHighBid() {
		return highBid;
	}
	
	public Direction getNextToBid() {
		return nextToBid;
	}


	public Call getLastCall() {
		return last;
	}	

	public Call getPartnersLastCall() {
		return beforeLast;
	}
	
	public List<Call> getCalls() {
		ArrayList<Call> result = new ArrayList<>();
		result.addAll(calls);
		return result;
	}
}
