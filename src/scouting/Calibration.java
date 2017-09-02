package scouting;

public class Calibration {
	public static final int RESOLUTION = 1;
	public static final double ACCURACY = 15;
	public static final int HIDDEN_NODES = 110;
	public static final double LEARNING_RATE = 0.001;
	public static final boolean DIFFERENTIATE_GEARS = true;
	public static final boolean DIFFERENTIATE_PRESSURE = true;
	public static final boolean REMOVE_CONFLICTIONS = false;
	public static final boolean REMOVE_OVERLAP = false;
	public static final boolean AVERAGE_OVERLAP = true;
	public static final boolean ENABLE_MOMENTUM = false;
	public static final int[] MOMENTUM_TRIGGERS = {100, 23, 21, 15, 13};
	public static final double[] MOMENTUM_VALUES = {0.0001, 0.001, 0.0001, 0.001, 0.0001};
	/*
	 * RESOLUTION:
		 * Inversely proportional to the generalization of data.
		 * Increasing it will:
		 * 		+ Generalize data less
		 * 		+ Give you more accurate results with your testing data
		 * 		- Overfit data more
		 * 		- Introduce extra patterns into the equation
	 * ACCURACY:
		 * End goal for the program. Determines when the network should stop and check its work.
		 * Increasing it will:
		 * 		+ Make the program end faster
		 * 		- Result in less accurate data
		 * 		- Make it more likely to take averages instead of patterns
	 * HIDDEN NODES:
		 * Increase the number of hidden nodes during its processing.
		 * Increasing it will:
		 * 		+ Increase the number of patterns it can solve for
		 * 		- Drastically increase the iteration time
	 * LEARNING RATE:
		 * Increases the rate of change the program learns at
		 * Increasing it will:
		 * 		+ Help it learn patterns faster
		 * 		+ Help it isolate main factors quicker
		 * 		- Increase risk of oscillation and/or learning regression
	 */
}
