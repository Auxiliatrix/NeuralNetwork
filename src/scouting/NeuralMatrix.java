package scouting;

public class NeuralMatrix {
	
	private int[][] inputs;
	private int[] outputs;
	public NeuralMatrix( int[][] inputs, int[] outputs ) {
		this.inputs = inputs;
		this.outputs = outputs;
	}
	public int[][] getInputs() {
		return inputs;
	}
	public int[] getOutputs() {
		return outputs;
	}
}
