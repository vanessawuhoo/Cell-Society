package ui;

import java.util.HashMap;
import java.util.Map;

public class UITester {
	public int[] getParameters(){
		int[] p = {3, 3};
		return p;
	}
	
	public Map<String, Double> getState(){
		Map<String, Double> m = new HashMap<String, Double>();
		for (int i = 0; i < 9; i++) {
			if (i % 2 == 0) {
				m.put("Hi"+i, 1.0);
			} else {
				m.put("NOOOO"+i, 2.0);
			}
		}
		return m;
	}
}
