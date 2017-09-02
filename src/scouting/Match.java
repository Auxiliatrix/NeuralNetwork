package scouting;

import java.util.ArrayList;

public interface Match {
	public String getWinner();
	public void setWinner(String winner);
	public int getRedScore();
	public void setRedScore(int score);
	public int getBlueScore();
	public void setBlueScore(int score);
	public int getRedClimb();
	public void addRedClimb();
	public int getBlueClimb();
	public void addBlueClimb();
	public boolean getRedKPaRank();
	public void setRedKPaRank(boolean value);
	public boolean getBlueKPaRank();
	public void setBlueKPaRank(boolean value);
	public boolean getRedRotorRank();
	public void setRedRotorRank(boolean value);
	public boolean getBlueRotorRank();
	public void setBlueRotorRank(boolean value);
	public ArrayList<String> getRedTeams();
	public ArrayList<String> getBlueTeams();
	public void addRedTeam(String team);
	public void addBlueTeam(String team);
	public String toString();
}
