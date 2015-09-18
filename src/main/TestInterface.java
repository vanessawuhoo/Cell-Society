package main;

import java.io.IOException;
import java.util.Map;

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
			Map<Integer, Map<String, Double>> states = test_hub.testStep();
			printStates(states);
		}
	}
	
	private void printStates(Map<Integer, Map<String, Double>> states) {
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int index = m*(i - 1) + j;
				double s = states.get(index).get("state");
				System.out.print(s + "  ");
			}
			System.out.println();
		}
	}
}
