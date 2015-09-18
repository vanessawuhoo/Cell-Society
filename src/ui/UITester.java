package ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
//tester class to make sure UI elements work 
public class UITester {
	
	//tester grid parameters
	public int[] getParameters(){
		int[] p = {5,5};
		return p;
	}
	
	//map implementation of passing states
	public Map<Integer, Map<String,Double>> getState(){
		Map<Integer, Map<String,Double>> mm = new HashMap<Integer,Map<String,Double>>();
		int[] hold = getParameters();
		int max = hold[0] * hold[1];
		for (int i = 0; i < max; i++) {
			Map<String, Double> m = new HashMap<String, Double>();
			if (i % 2 == 0) {
				m.put("state", 1.0);
			} else {
				m.put("state", 2.0);
			}
			mm.put(i, m);
		}
		return mm;
	}
	
	
}
