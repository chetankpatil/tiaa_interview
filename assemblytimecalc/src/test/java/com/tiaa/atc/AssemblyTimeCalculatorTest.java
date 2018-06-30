package com.tiaa.atc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssemblyTimeCalculatorTest {

	private PrintStream sysOut;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private final AssemblyTimeCalculator atc = new AssemblyTimeCalculator();

	@Before
	public void setUpStreams() {
		sysOut = System.out;
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void revertStreams() {
		System.setOut(sysOut);
	}

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void calculateWithInacceptableInputs() {
		final AssemblyTimeCalculator atc = new AssemblyTimeCalculator();

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Assembly time must be greater than 0.");
		atc.calculateTime(0, 0, 0);

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Number of machines and bolts must be greater than 0.");
		atc.calculateTime(0, 0, 1);

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Number of machines and bolts must be greater than 0.");
		atc.calculateTime(0, 1, 1);

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Number of bolts must be double of number of machines.");
		atc.calculateTime(1, 1, 1);

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Number of bolts must be double of number of machines.");
		atc.calculateTime(1, 3, 1);
	}

	@Test
	public void calculateWithAcceptableInput1() {

		atc.calculateTime(1, 2, 40);
		Assert.assertEquals("Total Products = 1\nTotal Time Taken = 40\n", outContent.toString());
	}

	@Test
	public void calculateWithAcceptableInput2() {
		final AssemblyTimeCalculator atc = new AssemblyTimeCalculator();
		atc.calculateTime(2, 4, 45);
		Assert.assertEquals("Total Products = 2\nTotal Time Taken = 45\n", outContent.toString());

	}

	@Test
	public void calculateWithAcceptableInput3() {
		final AssemblyTimeCalculator atc = new AssemblyTimeCalculator();

		atc.calculateTime(3, 6, 49);
		Assert.assertEquals("Total Products = 3\nTotal Time Taken = 49\n", outContent.toString());

	}

	@Test
	public void calculateWithAcceptableInput4() {
		final AssemblyTimeCalculator atc = new AssemblyTimeCalculator();

		atc.calculateTime(4, 8, 57);
		Assert.assertEquals("Total Products = 4\nTotal Time Taken = 114\n", outContent.toString());

	}

	@Test
	public void calculateWithAcceptableInput5() {
		final AssemblyTimeCalculator atc = new AssemblyTimeCalculator();

		atc.calculateTime(5, 10, 15);
		Assert.assertEquals("Total Products = 5\nTotal Time Taken = 30\n", outContent.toString());
	}

}
