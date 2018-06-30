package com.tiaa.atc;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AssemblyTimeCalculator {

	public void calculateTime(final int machines, final int bolts, final int assemblyTime) {
		if (assemblyTime < 1) {
			throw new IllegalArgumentException("Assembly time must be greater than 0.");
		}

		if ((machines < 1) || (bolts < 1)) {
			throw new IllegalArgumentException("Number of machines and bolts must be greater than 0.");
		}

		if ((machines > 0) && (bolts > 0) && ((machines * 2) != bolts)) {
			throw new IllegalArgumentException("Number of bolts must be double of number of machines.");
		}

		final Queue<String> conveyorBelt = initializeConveyorBelt(machines, bolts);

		final ExecutorService executor = Executors.newFixedThreadPool(3);
		final List<Future<Integer>> workerList = new ArrayList<>();
		for (int w = 0; w < 3; w++) {
			final Future<Integer> future = executor.submit(new Worker(conveyorBelt, assemblyTime));
			workerList.add(future);
		}

		int totalProducts = 0;
		for (final Future<Integer> w : workerList) {
			try {
				totalProducts += w.get();
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
			}
		}
		executor.shutdown();

		System.out.println("Total Products = " + totalProducts);

		int timeTaken = ((totalProducts / 3) * assemblyTime);
		if((totalProducts % 3) > 0) {
			timeTaken += assemblyTime;
		}
		System.out.println("Total Time Taken = " + timeTaken);
	}

	private Queue<String> initializeConveyorBelt(final int machines, final int bolts) {
		final Queue<String> conveyorBelt = new ConcurrentLinkedQueue<>();
		int temp = machines;
		for (int i = 0; i < bolts; i++) {
			conveyorBelt.add("B");
			if (temp-- > 0) {
				conveyorBelt.add("M");
			}
		}
		return conveyorBelt;
	}

}
