package bridge.search.pruning;

import bridge.search.Node;

public class AlphaBeta implements PruningStrategy {

	@Override
	public void prune(Node node) {
		if (node.shouldBeAlphaPruned()) {
			node.alphaPrune();
		}

		if (node.shouldBeBetaPruned()) {
			node.betaPrune();
		}

		if (node.getParent() != null) {
			prune(node.getParent());
		}

	}

}
