package main;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;

public class TestInterface {

	private Hub test_hub;
	private final int m = 2;
	private final int n = 2;
	
	public TestInterface(Hub h) {
		test_hub = h;
	}
	
	public void makeConsoleInterface() throws IOException {
		SimVars sv = test_hub.loadTestSim();
		printStates(sv.states);
		while(true) {
			System.in.read();
			Queue<Double> states = test_hub.testStep();
			printStates(states);
		}
	}
	
	private void printStates(Queue<Double> states) {
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				double s = states.poll();
				System.out.print(s + "  ");
			}
			System.out.println();
		}
	}
}
