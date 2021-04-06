package loom;

import java.util.concurrent.ExecutionException;

public class workbench {

	public static void main(String[] args) {
		System.out.println("Empezamos");

		demo.f1();

		demo.f2();

		demo.f3();

		try {
			demo.f4();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
