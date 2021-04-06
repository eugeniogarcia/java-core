package loom;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

public class demo {
	public static void f1() {
		final Thread thread = Thread.ofVirtual().start(() -> System.out.println("Hola"));
		try {
			thread.join();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void f2() {
		final var queue = new SynchronousQueue<String>();
		System.out.println("Lanza el virtual thread");
		Thread.ofVirtual().start(() -> {
			try {
				Thread.sleep(Duration.ofSeconds(2));
				queue.put("done");
			} catch (final InterruptedException e) { }
		});

		try {
			final String msg = queue.take();
			System.out.println("Se recibio el mensaje");
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void f3() {
		final ThreadFactory factory = Thread.ofVirtual().name("worker", 0).factory();

		factory.newThread(() -> {
			try {
				Thread.sleep(Duration.ofSeconds(2));
				System.out.println("Factoria 1");
			} catch (final InterruptedException e) { }}).start();

		factory.newThread(() -> {
			try {
				Thread.sleep(Duration.ofSeconds(2));
				System.out.println("Factoria 2");
			} catch (final InterruptedException e) { }}).start();
	}

	public static void f4() throws InterruptedException, ExecutionException {
		try (ExecutorService executor = Executors.newVirtualThreadExecutor()) {

			// Submits a value-returning task and waits for the result
			final Future<String> future = executor.submit(() -> "Pupa");
			final String result = future.join();

			// Submits two value-returning tasks to get a Stream that is lazily populated
			// with completed Future objects as the tasks complete
			final Stream<Future<String>> stream = executor.submit(List.of(() -> "Mausi", () -> "Nani"));
			stream.filter(Future::isCompletedNormally)
			.map(Future::join)
			.forEach(System.out::println);

			// Executes two value-returning tasks, waiting for both to complete
			final List<Future<String>> results1 = executor.invokeAll(List.of(() -> "Nico", () -> "Vera"));
			results1.stream().filter(Future::isCompletedNormally)
			.map(Future::join).forEach(x->{System.out.println(x);});

			// Executes two value-returning tasks, waiting for both to complete. If one of the
			// tasks completes with an exception, the other is cancelled.
			final List<Future<String>> results2 = executor.invokeAll(List.of(() -> "foo", () -> "bar"), /*cancelOnException*/ true);
			results2.stream().filter(Future::isCompletedNormally)
			.map(Future::join).forEach(x->{System.out.println(x);});

			// Executes two value-returning tasks, returning the result of the first to
			// complete, cancelling the other.
			final String first = executor.invokeAny(List.of(() -> "Uno", () -> "Dos"));
			System.out.println(first);

		}
	}
}
