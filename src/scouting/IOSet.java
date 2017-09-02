package scouting;

import java.util.ArrayList;

public class IOSet {
	private String placementInput;
	private int[] placementInputRaw;
	private ArrayList<Integer> scores;
	private int overlapCount;
	
	public IOSet() {
		placementInput = "";
		scores = new ArrayList<Integer>();
		overlapCount = 0;
	}
	public IOSet(String placementInput, int[] placementInputRaw) {
		this.placementInput = placementInput;
		this.placementInputRaw = placementInputRaw;
		scores = new ArrayList<Integer>();
		overlapCount = 0;
	}
	public IOSet(String placementInput, int[] placementInputRaw, int score) {
		this.placementInput = placementInput;
		this.placementInputRaw = placementInputRaw;
		scores = new ArrayList<Integer>();
		scores.add(score);
		overlapCount = 1;
	}
	public void setPlacementInput(String pi, int[] pir) {
		placementInput = pi;
		placementInputRaw = pir;
	}
	public String getPlacementInput() {
		return placementInput;
	}
	public int[] getPlacementInputRaw() {
		return placementInputRaw;
	}
	public void addScore(int score) {
		scores.add(score);
		overlapCount++;
	}
	public ArrayList<Integer> getScores() {
		return scores;
	}
	public int getAverageScore() {
		int total = 0;
		for( int score : scores )
			total += score;
		return total/overlapCount;
	}
}
