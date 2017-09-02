package scouting;

import java.util.ArrayList;

public class SimpleMatch implements Match {
	private String winner;
	private int blueScore;
	private int redScore;
	private int redClimb;
	private int blueClimb;
	private boolean blueRotorRank;
	private boolean redRotorRank;
	private boolean blueKPaRank;
	private boolean redKPaRank;
	private ArrayList<String> blueTeams;
	private ArrayList<String> redTeams;
	
	public SimpleMatch() {
		winner = "";
		blueTeams = new ArrayList<String>();
		redTeams = new ArrayList<String>();
		redClimb = 0;
		blueClimb = 0;
		blueRotorRank = false;
		redRotorRank = false;
		blueKPaRank = false;
		redKPaRank = false;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	public int getRedScore() {
		return redScore;
	}
	
	public void setRedScore(int score) {
		redScore = score;	
	}
	
	public int getBlueScore() {
		return blueScore;
	}
	
	public void setBlueScore(int score) {
		blueScore = score;
	}
	
	public int getRedClimb() {
		return redClimb;
	}
	
	public void addRedClimb() {
		redClimb++;
	}
	
	public int getBlueClimb() {
		return blueClimb;
	}
	
	public void addBlueClimb() {
		blueClimb++;
	}
	
	public boolean getRedKPaRank() {
		return redKPaRank;
	}
	
	public void setRedKPaRank(boolean value) {
		redKPaRank = value;
		
	}
	
	public boolean getBlueKPaRank() {
		return blueKPaRank;
	}
	
	public void setBlueKPaRank(boolean value) {
		blueKPaRank = value;
	}
	
	public boolean getRedRotorRank() {
		return redRotorRank;
	}
	
	public void setRedRotorRank(boolean value) {
		redRotorRank = value;
		
	}
	
	public boolean getBlueRotorRank() {
		return blueRotorRank;
	}
	
	public void setBlueRotorRank(boolean value) {
		blueRotorRank = value;
	}

	@Override
	public ArrayList<String> getRedTeams() {
		return redTeams;
	}

	@Override
	public ArrayList<String> getBlueTeams() {
		return blueTeams;
	}

	@Override
	public void addRedTeam(String team) {
		redTeams.add(team);
	}

	@Override
	public void addBlueTeam(String team) {
		blueTeams.add(team);		
	}
	public String toString() {
		//String ret = "Match Data:";
		String ret = "";
		String bg = "No";
		String bp = "No";
		String rg = "No";
		String rp = "No";
		if( blueRotorRank ) {
			bg = "Yes";
		}
		if( blueKPaRank ) {
			bp = "Yes";
		}
		if( redRotorRank ) {
			rg = "Yes";
		}
		if( redKPaRank ) {
			rp = "Yes";
		}
		/*
		ret += "\n\tBlue Alliance:" + "\t| " + blueTeams.get(0) + "\t" + blueTeams.get(1) + "\t" + blueTeams.get(2);
		ret += "\n\tRed Alliance: " + "\t| " + redTeams.get(0) + "\t" + redTeams.get(1) + "\t" + redTeams.get(2);
		ret += "\n\tResults:      " + "\t| " + getBlueScore() + ":" + getRedScore();
		ret += "\n\tRank Points:  " + "\t| " + "Gears:" + rg + " : " + bg + "\tkPa:" + rp + " : " + bp + "Winner: " + winner;
		*/
		
		ret += "\nMatch:" + "\t__Blue__" + "\t" + "__Red__";
		ret += "\n\t" + blueTeams.get(0) + "\t\t" + redTeams.get(0);
		ret += "\n\t" + blueTeams.get(1) + "\t\t" + redTeams.get(1);
		ret += "\n\t" + blueTeams.get(2) + "\t\t" + redTeams.get(2);
		ret += "\nScore:" + "\t" + getBlueScore() + "\t\t" + getRedScore();
		ret += "\nClimb:" + "\t" + getBlueClimb() + "\t\t" + getRedClimb();
		ret += "\nRotor:" + "\t" + bg + "\t\t" + rg;
		ret += "\nkPa:" + "\t" + bp + "\t\t" + rp;
		ret += "\nWinner:\t\t" + winner;
		return ret;
	}
}
