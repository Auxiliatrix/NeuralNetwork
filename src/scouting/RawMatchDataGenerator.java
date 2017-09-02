package scouting;

import java.util.ArrayList;

public class RawMatchDataGenerator {
	private SimpleTeamDataMap stdm;
	
	public RawMatchDataGenerator(SimpleTeamDataMap stdm) {
		this.stdm = stdm;
	}
	public ArrayList<SimpleDataSet> generate(SimpleMatchMap smm, int mode) {
		ArrayList<SimpleDataSet> sdsm = new ArrayList<SimpleDataSet>();
		for( SimpleMatch sm : smm.asArrayList() ) {
			sdsm.add(generate(sm, mode));
		}
		return sdsm;
	}
	public SimpleDataSet generate(SimpleMatch sm, int mode) {
		//if( mode == 0 ) {
			return quantitative(sm);
		//}
		// TODO: modes 1 and 2
	}

	private SimpleDataSet quantitative(SimpleMatch sm) {
		String bluePlacement = "";
		int[] blueGears = new int[11*Calibration.RESOLUTION];
		int[] bluePressure = new int[11*Calibration.RESOLUTION];
		for( int f=0; f<11*Calibration.RESOLUTION; f++ ) {
			blueGears[f] = 0;
			bluePressure[f] = 0;
		}
		for( String team : sm.getBlueTeams() ) {
			System.out.println(team);
			SimpleTeamData std = stdm.get(team);
			int gearPlacement = (int) (std.getMeanGears() * 2 * Calibration.RESOLUTION);
			int pressurePlacement = (int) (std.getMeanPressure() / (5.0 / (double) Calibration.RESOLUTION)) - 1;
			if( Calibration.DIFFERENTIATE_GEARS ) {
				for( int f=0; f<=Math.max(gearPlacement,0); f++ ) {
					blueGears[f]++;
				}
			} else {
				blueGears[Math.max(gearPlacement, 0)]++;
			}
			if( Calibration.DIFFERENTIATE_PRESSURE ) {
				for( int f=0; f<=Math.max(pressurePlacement,0); f++) {
					bluePressure[f]++;
				}
			} else if( pressurePlacement != -1 ) {
				bluePressure[Math.max(pressurePlacement, 0)]++;
			}
		}
		bluePlacement += "{";
		for( int f=0; f<11*Calibration.RESOLUTION; f++ ) {
			bluePlacement += blueGears[f]+ ", ";
		}
		for( int f=0; f<11*Calibration.RESOLUTION; f++ ) {
			bluePlacement += bluePressure[f] + ", ";
		}
		bluePlacement += "}";
		int adjustedBlueScore = sm.getBlueScore() - sm.getBlueClimb() * 50;
		String redPlacement = "";
		int[] redGears = new int[11*Calibration.RESOLUTION];
		int[] redPressure = new int[11*Calibration.RESOLUTION];
		for( int f=0; f<11*Calibration.RESOLUTION; f++ ) {
			redGears[f] = 0;
			redPressure[f] = 0;
		}
		for( String team : sm.getRedTeams() ) {
			SimpleTeamData std = stdm.get(team);
			int gearPlacement = (int) (std.getMeanGears() * 2 * Calibration.RESOLUTION);
			int pressurePlacement = (int) (std.getMeanPressure() / (5.0 / (double) Calibration.RESOLUTION)) - 1;
			if( Calibration.DIFFERENTIATE_GEARS ) {
				for( int f=0; f<=Math.max(gearPlacement, 0); f++ ) {
					redGears[f]++;
				}
			} else {
				redGears[Math.max(gearPlacement, 0)]++;
			}
			if( Calibration.DIFFERENTIATE_PRESSURE ) {
				for( int f=0; f<=Math.max(pressurePlacement, 0); f++ ) {
					redPressure[f]++;
				}
			} else if( pressurePlacement != -1 ) {
				redPressure[Math.max(pressurePlacement, 0)]++;
			}
		}
		redPlacement += "{";
		for( int f=0; f<Calibration.RESOLUTION; f++ ) {
			redPlacement += redGears[f]+ ", ";
		}
		for( int f=0; f<Calibration.RESOLUTION; f++ ) {
			redPlacement += redPressure[f] + ", ";
		}
		redPlacement += "}";
		int adjustedRedScore = sm.getRedScore() - sm.getRedClimb() * 50;
		/* string style */
		//return new SimpleDataSet(bluePlacement, adjustedBlueScore, redPlacement, adjustedRedScore);
		/* raw style */
		//return new SimpleDataSet(blueGears, adjustedBlueScore, redGears, adjustedRedScore);
		/* complete style */
		return new SimpleDataSet(blueGears, bluePlacement, adjustedBlueScore, redGears, redPlacement, adjustedRedScore);
	}
}
