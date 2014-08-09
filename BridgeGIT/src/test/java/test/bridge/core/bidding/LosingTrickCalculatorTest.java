package test.bridge.core.bidding;

import static org.junit.Assert.*;

import org.junit.Test;

import bridge.bidding.LosingTrickCalculator;
import bridge.core.Hand;

public class LosingTrickCalculatorTest {

	@Test
	public void test3orMoreCardsInSuitMissing1Winner() {
		LosingTrickCalculator ltc = new LosingTrickCalculator(new Hand("A,K,Q,3", "A,K,9", "A,Q,9","K,Q,9"));
		assertEquals(3,ltc.LosingTrickCount());
	}
	
	@Test
	public void test3orMoreCardsInSuitMissing2Winners() {
		LosingTrickCalculator ltc = new LosingTrickCalculator(new Hand("A,10,5", "K,9,8", "Q,9,7","9,8,3,2"));
		assertEquals(9,ltc.LosingTrickCount());
	}
	
	@Test
	public void testDoubletons() {
		LosingTrickCalculator ltc = new LosingTrickCalculator(new Hand("A,K", "K,9", "A,9","Q,9,8,7,6,5,2"));
		assertEquals(4,ltc.LosingTrickCount());
	}
	
	@Test
	public void testSingletons() {
		LosingTrickCalculator ltc = new LosingTrickCalculator(new Hand("A", "K", "Q","A,K,Q,8,7,6,5,4,3,2"));
		assertEquals(2,ltc.LosingTrickCount());
	}
	
}
