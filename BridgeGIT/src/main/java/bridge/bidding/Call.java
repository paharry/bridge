package bridge.bidding;

import bridge.core.Direction;
import bridge.core.deck.Trump;

/**
 *  pah Bid and call seem be confused 
 *  when is call not bid and vise versa?
 */

public class Call {
	private final Bid bid;
	private final Direction direction;

	public Call(Bid bid, Direction direction) {
		this.bid = bid;
		this.direction = direction;

	}
	
	/**
	 * @param
	 * @return
	 */
	public Trump getTrump() {
		return bid.getTrump();
	}

	/**
	 * @param
	 * @return
	 */
	public boolean isPass() {
		return new Pass().equals(bid);
	}

	/**
	 * @param
	 * @return
	 */
	public boolean pairMatches(Direction candidate) {
		if (direction.equals(candidate) || direction.opposite().equals(candidate)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 *  getters and setters 
	 */
	
	public Bid getBid() {
		return bid;
	}

	public Direction getDirection() {
		return direction;
	}

/**
 * toString
 */
	@Override
	public String toString() {
		return direction.toString() + ": " + bid;
	}

}
