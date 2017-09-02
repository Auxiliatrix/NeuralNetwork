package scouting;

public class SimpleDataSet implements DataSet {
	private String bluePlacementInput;
	private int blueScore;
	private String redPlacementInput;
	private int redScore;
	private int[] bluePlacementInputRaw;
	private int[] redPlacementInputRaw;
	
	public SimpleDataSet() {
		bluePlacementInput = "";
		blueScore = 0;
	}
	public SimpleDataSet(String bluePlacementInput, int blueScore, String redPlacementInput, int redScore) {
		this.bluePlacementInput = bluePlacementInput;
		this.blueScore = blueScore;
		this.redPlacementInput = redPlacementInput;
		this.redScore = redScore;
	}
	public SimpleDataSet(int[] bluePlacementInputRaw, int blueScore, int[] redPlacementInputRaw, int redScore) {
		this.bluePlacementInputRaw = bluePlacementInputRaw;
		this.redPlacementInputRaw = redPlacementInputRaw;
	}
	public SimpleDataSet(int[] bluePlacementInputRaw, String bluePlacementInput, int blueScore, int[] redPlacementInputRaw, String redPlacementInput, int redScore) {
		this.bluePlacementInputRaw = bluePlacementInputRaw;
		this.bluePlacementInput = bluePlacementInput;
		this.blueScore = blueScore;
		this.redPlacementInputRaw = redPlacementInputRaw;
		this.redPlacementInput = redPlacementInput;
		this.redScore = redScore;
	}
	public String getBluePlacementInput() {
		return bluePlacementInput;
	}
	public int[] getBluePlacementInputRaw() {
		return bluePlacementInputRaw;
	}
	public int getBlueScore() {
		return blueScore;
	}
	public String getRedPlacementInput() {
		return redPlacementInput;
	}
	public int[] getRedPlacementInputRaw() {
		return redPlacementInputRaw;
	}
	public int getRedScore() {
		return redScore;
	}
}
