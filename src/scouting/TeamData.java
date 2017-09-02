package scouting;

public interface TeamData {
	
	public String getName();
	public double getMeanGears();
	public double getMeanPressure();
	public void setName(String newName);
	public void setMeanGears(double newGears);
	public void setMeanPressure(double newPressure);
	public void addMeanGears(double newGears);
	public void addMeanPressure(double newPressure);
	public String toString();
	
}
