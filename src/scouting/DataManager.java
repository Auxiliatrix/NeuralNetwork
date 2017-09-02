package scouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DataManager {

	private SimpleDataParser sdp;
	private SimpleMatchParser smp;
	private final boolean VERBOSE = false;
	private RawMatchDataGenerator rmdg;
	
	public DataManager() {
		sdp  = new SimpleDataParser(true);
		smp = new SimpleMatchParser();
	}
	
	public NeuralMatrix process(ArrayList<String> events) {
		ArrayList<String> atdms = new ArrayList<String>();
		ArrayList<String> asmms = new ArrayList<String>();
		for( String event : events ) {
			atdms.add(event+"D.txt");
			asmms.add(event+"M.txt");
		}
		ArrayList<String> inputs = new ArrayList<String>();
		ArrayList<Integer> outputs = new ArrayList<Integer>();
		
		HashMap<String, IOSet> averagedData = new HashMap<String, IOSet>();
		
		ArrayList<int[]> inputsRaw = new ArrayList<int[]>();
		for( int f=0; f<atdms.size(); f++ ) {
			SimpleTeamDataMap tdm = sdp.process(atdms.get(f));
			SimpleMatchMap smm = smp.process(asmms.get(f));
			if( VERBOSE ) {
				System.out.println("-=-={ Team Data }=-=-");
				System.out.println(tdm);
				System.out.println();
				System.out.println("-=-={ Match Data }=-=-");
				System.out.println(smm);
			}
			rmdg = new RawMatchDataGenerator(tdm);
			ArrayList<SimpleDataSet> sdsm = rmdg.generate(smm, 0);
			for( SimpleDataSet sds : sdsm ) {
				if( Calibration.AVERAGE_OVERLAP ) {
					if( averagedData.containsKey(sds.getBluePlacementInput()) ) {
						averagedData.get(sds.getBluePlacementInput()).addScore(sds.getBlueScore());
					} else {
						averagedData.put(sds.getBluePlacementInput(), new IOSet(sds.getBluePlacementInput(), sds.getBluePlacementInputRaw(), sds.getBlueScore()));
					}
				} else if( inputs.contains(sds.getBluePlacementInput()) && Calibration.REMOVE_OVERLAP ) {
					// do nothing 
				} else {
					inputs.add(sds.getBluePlacementInput());
					outputs.add(sds.getBlueScore());
					inputsRaw.add(sds.getBluePlacementInputRaw());
				}
				
				if( Calibration.AVERAGE_OVERLAP ) {
					if( averagedData.containsKey(sds.getRedPlacementInput()) ) {
						averagedData.get(sds.getRedPlacementInput()).addScore(sds.getRedScore());
					} else {
						averagedData.put(sds.getRedPlacementInput(), new IOSet(sds.getRedPlacementInput(), sds.getRedPlacementInputRaw(), sds.getRedScore()));
					}				
				} else if( inputs.contains(sds.getRedPlacementInput()) && Calibration.REMOVE_OVERLAP ) {
					// do nothing
				} else {
					inputs.add(sds.getRedPlacementInput());
					outputs.add(sds.getRedScore());
					inputsRaw.add(sds.getRedPlacementInputRaw());
				}
			}
		}
		String line = "";
		if( VERBOSE ) {
			line = "-=-={Input Data}=-=-";
			System.out.println(line);
			for( String inputLine : inputs ) {
				System.out.println(inputLine + ",");
			}
			line = "-=-{Output Data}=-=-";
			System.out.println(line);
			line = "";
			line += "{";
			for( int outputLine : outputs ) {
				line += outputLine + ", ";
			}
			line += "}";
		}
		System.out.println(line);
		if( Calibration.AVERAGE_OVERLAP ) {
			Object[] keys = averagedData.keySet().toArray();
			line = "Input Matrix Size: " + averagedData.size() + " x " + averagedData.get(keys[0]).getPlacementInput().length()/3 ;
			System.out.println(line);
			line = "Output Matrix Size: " + averagedData.size() + " x " + "1";
			System.out.println(line);
			line = "Recommended Hidden Layer Size: " + ((averagedData.get(keys[0]).getPlacementInput().length()/3 + 1) / 2) ;
			System.out.println(line);
			
			IOSet example = averagedData.get(keys[0]);
			int size = example.getPlacementInputRaw().length;
			int[][] inputMatrix = new int[averagedData.size()][size];
			int[] outputMatrix = new int[averagedData.size()];
			for( int f=0; f<keys.length; f++ ) {
				String key = (String) keys[f];
				IOSet ios = averagedData.get(key);
				int[] raw = ios.getPlacementInputRaw();
				for( int g=0; g<raw.length; g++ ) {
					inputMatrix[f][g] = raw[g];
				}
				outputMatrix[f] = ios.getAverageScore();
			}
			return new NeuralMatrix(inputMatrix, outputMatrix);
		} else {
			line = "Input Matrix Size: " + inputs.size() + " x " + inputs.get(0).length()/3 ;
			System.out.println(line);
			line = "Output Matrix Size: " + outputs.size() + " x " + "1";
			System.out.println(line);
			line = "Recommended Hidden Layer Size: " + ((inputs.get(0).length() / 3 + 1) / 2) ;
			System.out.println(line);
			
			int[][] inputMatrix = new int[inputsRaw.size()][inputsRaw.get(0).length];
			int[] outputMatrix = new int[outputs.size()];
			for( int f=0; f<inputsRaw.size(); f++ ) {
				for( int g=0; g<inputsRaw.get(0).length; g++ ) {
					inputMatrix[f][g] = inputsRaw.get(f)[g];
				}
				outputMatrix[f] = outputs.get(f);
			}
			return new NeuralMatrix(inputMatrix, outputMatrix);
		}
	}
}
