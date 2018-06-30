package com.tiaa.atc;

import java.util.Queue;
import java.util.concurrent.Callable;

public class Worker implements Callable<Integer> {

	private final Queue<String> conveyorBelt;

	private final int assemblyTime;

	private boolean bolt1;

	private boolean bolt2;

	private boolean machine;

	private int totalAssembled;

	public Worker(final Queue<String> conveyorBelt, final int assemblyTime) {
		bolt1 = false;
		bolt2 = false;
		machine = false;
		totalAssembled = 0;
		this.assemblyTime = assemblyTime;
		this.conveyorBelt = conveyorBelt;
	}

	@Override
	public Integer call() {
		while (!conveyorBelt.isEmpty()) {
			pickupRawMaterial();

			if (machine && bolt1 && bolt2) {
				assemble();
			}
		}
		return totalAssembled;
	}

	private void pickupRawMaterial() {
		if ((totalAssembled > 0) && (conveyorBelt.size() < 3) && !(machine || bolt1 || bolt2)) {
			return;
		}

		final String rawMaterial = conveyorBelt.poll();
		if (!machine && "M".equals(rawMaterial)) {
			machine = true;
		} else if (!bolt1 && "B".equals(rawMaterial)) {
			bolt1 = true;
		} else if (!bolt2 && "B".equals(rawMaterial)) {
			bolt2 = true;
		} else {
			conveyorBelt.add(rawMaterial);
		}
	}

	private void assemble() {
		try {
			bolt1 = false;
			bolt2 = false;
			machine = false;
			Thread.sleep((long) assemblyTime * 1000);
			totalAssembled++;
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
