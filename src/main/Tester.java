package main;

import java.io.IOException;

public class Tester {

	public static void main(String[] args) throws IOException {
		Hub hub = new Hub();
		TestInterface ti = new TestInterface(hub);
		ti.makeConsoleInterface();
	}
}
