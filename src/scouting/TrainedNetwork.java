package scouting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TrainedNetwork {

	private static Writer w;
	private static Writer x;
	private static DataManager dm = new DataManager();
    private static int[][] testingInputs = {

    	};
    
    private static int[] testingOutputs = {};
    private static double[] hiddenValues = new double[Calibration.HIDDEN_NODES];
    
    private static double[][] IH_weights;
    private static double[] HO_weights;
    
    private static double experimentalOut = 0.0;
    private static double tempError = 0.0;
    private static double accuracy = Calibration.ACCURACY;
    
    private static NeuralNetwork_AllianceScore nnas = new NeuralNetwork_AllianceScore();
    
    public static void main(String[] args) throws InterruptedException {
    	w = new Writer("_trained.txt");
    	w.clear();
    	x = new Writer("_outliersTest.txt");
    	x.clear();
    	train();
    	initData();
        displayResults();
    }
    
    private static void train() {
    	nnas.process();
    	IH_weights = nnas.getIHWeights();
    	HO_weights = nnas.getHOWeights();
    }
    
    private static void initData() {
    	ArrayList<String> events = new ArrayList<String>();
    	/*
    	events.add("casj");
		events.add("cair");
		events.add("hiho");
		events.add("idbo");
		events.add("ilch");
		events.add("mxto");
		events.add("nyli");
		events.add("ohcl");
		events.add("melew");
		events.add("mihow");
		events.add("miken");
		events.add("milsu");
		events.add("mitry");
		events.add("onwin");
		events.add("orlak");
		*/
		// events.add("2017hop");
		NeuralMatrix nm = dm.process(events);
		testingInputs = nm.getInputs();
		testingOutputs = nm.getOutputs();
    }
    private static void calcNet(final int patNum) {
        for(int f = 0; f<hiddenValues.length; f++) {
            hiddenValues[f] = 0.0;
            for(int g = 0; g<testingInputs[patNum].length; g++) {
                hiddenValues[f] += (testingInputs[patNum][g] * IH_weights[g][f]);
            }
            hiddenValues[f] = activate(hiddenValues[f]);
        }
        experimentalOut = 0.0;
        for(int f = 0; f<hiddenValues.length; f++) {
            experimentalOut += hiddenValues[f] * HO_weights[f];
        }
        tempError = (experimentalOut / testingOutputs[patNum]) - 1;
    }
    private static double activate(final double data) {
    	//return 1.0 / (1.0 + Math.pow(Math.E, -data));
    	return Math.tanh(data);
    }
    private static void displayResults() {
    	ArrayList<Integer> outliers = new ArrayList<Integer>();
    	ArrayList<Double> experimentalOutliers = new ArrayList<Double>();
    	double totalError = 0;
        for(int f = 0; f<testingInputs.length; f++) {
            calcNet(f);
            double error = ((testingOutputs[f] / experimentalOut)-1) * 100;
            totalError += Math.abs(error);
            if( error > accuracy ) {
            	outliers.add(f);
            	experimentalOutliers.add(experimentalOut);
            }
            w.write("pat = " + (f + 1) + "  \t\tactual = " + testingOutputs[f] + "\tneural = " + experimentalOut + "\terror = " +  error);
        }
        double averageError = totalError / testingInputs.length;
        int count = 0;
        for( int match : outliers ) {
        	String line = "{";
        	for(int f=0; f<testingInputs[match].length; f++ ) {
        		line += testingInputs[match][f] + ", ";
        	}
        	x.write(line + "}");
        	x.write("Match Number: " + match + "\tExpected: " + testingOutputs[match] + "\tActual: " + experimentalOutliers.get(count) + "\tError: " + ((testingOutputs[match] / experimentalOutliers.get(count)-1)*100));
        	count++;
        }
        System.out.println("Average Error: " + averageError);
        System.out.println("Outliers (" + outliers.size() + "/" + testingInputs.length + " | " + (((double) outliers.size()/(double)testingInputs.length) * 100.0) + "%): ");
        //sitRep();
    }
}
