package ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public class UITester {
	public int[] getParameters(){
		int[] p = {8,5};
		return p;
	}
	
	public Queue<Double> getQueueState(){
		Queue<Double> q = new LinkedList<Double>();
		int[] hold = getParameters();
		int max = hold[0] * hold[1];
		for (int i = 0; i < max; i++) {
			q.add((double) i);
		}
		return q;
	}
	
	public Map<String, Double> getState(){
		Map<String, Double> m = new HashMap<String, Double>();
		for (int i = 0; i < 25; i++) {
			if (i % 2 == 0) {
				m.put("Hi"+i, 1.0);
			} else {
				m.put("Hi"+i, 2.0);
			}
		}
		return m;
	}
	
	
}
