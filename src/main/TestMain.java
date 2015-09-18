package main;

import java.io.IOException;

public class TestMain {

	public static void main(String[] args) throws IOException {
		Hub h = new Hub();
		TestInterface ti = new TestInterface(h);
		ti.makeConsoleInterface();
	}
}
