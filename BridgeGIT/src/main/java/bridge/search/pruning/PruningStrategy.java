package bridge.search.pruning;

import bridge.search.Node;

public interface PruningStrategy {
	public void prune(Node node);
}
