package scouting;

public class SimpleTeamData implements TeamData {
	private String name;
	private double mGears;
	private double mPressure;
	private int gearDataPoints;
	private int pressureDataPoints;
	
	public SimpleTeamData() {
		this.name = "";
		gearDataPoints = 0;
		pressureDataPoints = 0;
		mGears = 0;
		mPressure = 0;
	}
	public SimpleTeamData(String name) {
		this.name = name;
		gearDataPoints = 0;
		pressureDataPoints = 0;
		mGears = 0;
		mPressure = 0;
	}
	public SimpleTeamData(String name, double mGears, double mPressure) {
		this.name = name;
		gearDataPoints = 1;
		pressureDataPoints = 1;
		this.mGears = mGears;
		this.mPressure = mPressure;
	}
	public String getName() {
		return name;
	}
	public void setName(String newName) {
		name = newName;
	}
	public double getMeanGears() {
		return mGears/gearDataPoints;
	}
	public void setMeanGears(double newGears) {
		mGears = newGears;
		gearDataPoints = 1;
	}
	public void addMeanGears(double newGears) {
		mGears += newGears;
		gearDataPoints++;
	}
	public double getMeanPressure() {
		return mPressure/pressureDataPoints;
	}
	public void setMeanPressure(double newPressure) {
		mPressure = newPressure;
		pressureDataPoints = 1;
	}
	public void addMeanPressure(double newPressure) {
		mPressure += newPressure;
		pressureDataPoints++;
	}
	public String toString(){
		String ret = "";
		ret += "Team: " + name;
		ret += "\t| mGears: " + this.getMeanGears();
		ret += "\t| mPress: " + this.getMeanPressure();
		return ret;
	}
}
