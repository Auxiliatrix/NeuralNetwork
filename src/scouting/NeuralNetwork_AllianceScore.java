package scouting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// TODO: maybe whole matches?

public class NeuralNetwork_AllianceScore {

	/*	Data Input Format
	 * 	G = Gears
	 * 	P = Pressure
	 * 	# = # of bots
	 * 
	 * 	#G>0.0	#G>0.5	#G>1.0	#G>1.5	#G>2.0	#G>2.5	#G>3.0	#G>3.5	#G>4.0	#G>4.5	#G>5.0	#G>5.5	#G>6.0
	 * 	#P>0	#P>10	#P>20	#P>30	#P>40	#P>50	#P>60
	 * 
	 * Hidden Nodes:
	 * 	10
	 * Output Data
	 * 	Alliance Score
	 */

	private Writer w;
	private Writer x;
	private Writer y;
	private DataManager dm = new DataManager();
	
    private int[][] trainingInputs = {

    	};
    
    private int[] trainingOutputs = {};
    
    private int[][] problemInputs = {
    		
    	};
    
    private double[] hiddenValues = new double[Calibration.HIDDEN_NODES];
    
    private double IH_learningRate = Calibration.LEARNING_RATE;
    private double HO_learningRate = Calibration.LEARNING_RATE;
    
    private double[][] IH_weights;
    private double[] HO_weights;
    private double[][] iIH_weights;
    private double[] iHO_weights;
    
    private double tempError = 0.0;
    private double experimentalOut = 0.0;
    private double RMSerror = 0.0;
    private double accuracy = Calibration.ACCURACY;
    
    private int epoch = 0;
    
    public NeuralNetwork_AllianceScore() {
    	
    }
    
    public double[][] getIHWeights() {
    	return IH_weights;
    }
    
    public double[] getHOWeights() {
    	return HO_weights;
    }
    
    public void process() {
    	w = new Writer("_results.txt");
    	x = new Writer("_predictions.txt");
    	y = new Writer("_outliersTrain.txt");
    	w.clear();
    	x.clear();
    	y.clear();
    	initData();
        initWeights();
        double last = 0;
        double orig = 0;
        while( true ) {
        	if( Calibration.ENABLE_MOMENTUM ) {
        		for( int f=0; f<Calibration.MOMENTUM_TRIGGERS.length; f++ ) {
        			if( RMSerror <= Calibration.MOMENTUM_TRIGGERS[f] ) {
        				IH_learningRate = Calibration.MOMENTUM_VALUES[f];
        				HO_learningRate = Calibration.MOMENTUM_VALUES[f];
        			}
        		}
        	}
        	double rate = 0;
        	for(int f = 0; f<trainingInputs.length; f++) {
                int patNum = new Random().nextInt(trainingInputs.length);
                calcNet(patNum);
                WeightChangesIH(patNum);
            }
            RMSerror = calcOverallError()*100;
            if( epoch == 0 ) {
            	orig = RMSerror;
            }
            rate = (last - RMSerror);
            System.out.println("epoch = " + epoch + "\tRMS Error = " + RMSerror + "\timprovement: " + (orig-RMSerror) + "\trate: " + rate);
            if( RMSerror<accuracy ) {
            	break;
            }
            last = RMSerror;
            epoch++;
        }
        displayResults();
        for( int f=0; f<problemInputs.length; f++ ) {
        	System.out.println(finalCalc(problemInputs[f]));
        }
    }
    
    private void initData() {
    	ArrayList<String> events = new ArrayList<String>();
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
		/*
		events.add("txwa");
		events.add("txwo");
		events.add("cthar");
		events.add("nccmp");
		events.add("njski");
		events.add("onlon");
		events.add("waahs");
		*/
		NeuralMatrix nm = dm.process(events);
		trainingInputs = nm.getInputs();
		trainingOutputs = nm.getOutputs();
		IH_weights =  new double[trainingInputs[0].length][hiddenValues.length];
	    HO_weights = new double[hiddenValues.length];
    }
    
    private void initWeights() {
        for(int f = 0; f<hiddenValues.length; f++) {
            HO_weights[f] = (new Random().nextDouble() - 0.5) / 2.0;
            for(int g = 0; g<trainingInputs[0].length; g++) {
                IH_weights[g][f] = (new Random().nextDouble() - 0.5) / 5.0;
            }
            iHO_weights = HO_weights;
            iIH_weights = IH_weights;
        }
    }
    private double finalCalc(int[] values) {
        for(int f = 0; f<hiddenValues.length; f++) {
            hiddenValues[f] = 0.0;
            for(int g = 0; g<values.length; g++) {
                hiddenValues[f] += (values[g] * IH_weights[g][f]);
            }
            hiddenValues[f] = activate(hiddenValues[f]);
        }
        experimentalOut = 0.0;
        for(int f = 0; f<hiddenValues.length; f++) {
            experimentalOut += hiddenValues[f] * HO_weights[f];
        }
        return experimentalOut;
    }
    private void calcNet(final int patNum) {
        for(int f = 0; f<hiddenValues.length; f++) {
            hiddenValues[f] = 0.0;
            for(int g = 0; g<trainingInputs[patNum].length; g++) {
                hiddenValues[f] += (trainingInputs[patNum][g] * IH_weights[g][f]);
            }
            hiddenValues[f] = activate(hiddenValues[f]);
        }
        experimentalOut = 0.0;
        for(int f = 0; f<hiddenValues.length; f++) {
            experimentalOut += hiddenValues[f] * HO_weights[f];
        }
        tempError = (experimentalOut / trainingOutputs[patNum]) - 1;
    }
    private double activate(final double data) {
    	//return 1.0 / (1.0 + Math.pow(Math.E, -data));
    	return Math.tanh(data);
    }
    private void WeightChangesIH(final int patNum) {
    	for(int f = 0; f<hiddenValues.length; f++) {
            double weightChange = HO_learningRate * tempError * hiddenValues[f];
            HO_weights[f] -= weightChange;
            /*
            if(HO_weights[f]<-15.0){
                HO_weights[f] = -15.0;
            }else if(HO_weights[f] > 15.0){
                HO_weights[f] = 15.0;
            }
            */
        }
        for(int f = 0; f<hiddenValues.length; f++) {
            for(int g = 0; g<trainingInputs[patNum].length; g++) {
                double x = 1 - Math.pow(hiddenValues[f], 2);
                x = x * HO_weights[f] * tempError * IH_learningRate;
                x = x * trainingInputs[patNum][g];
                double weightChange = x;
                IH_weights[g][f] -= weightChange;
            }
        }
    }
    private double calcOverallError() {
        double errorValue = 0.0;
        for(int f = 0; f<trainingInputs.length; f++) {
             calcNet(f);
             errorValue += Math.pow(tempError, 2);
        }
        errorValue /= trainingInputs.length;  
        return Math.sqrt(errorValue);
    }
    private void displayResults() {
    	ArrayList<Integer> outliers = new ArrayList<Integer>();
    	ArrayList<Double> experimentalOutliers = new ArrayList<Double>();
    	double totalError = 0;
        for(int f = 0; f<trainingInputs.length; f++) {
            calcNet(f);
            double error = ((trainingOutputs[f] / experimentalOut)-1) * 100;
            totalError += Math.abs(error);
            if( error > accuracy ) {
            	outliers.add(f);
            	experimentalOutliers.add(experimentalOut);
            }
            x.write("pat = " + (f + 1) + "   \t\tactual = " + trainingOutputs[f] + "\tneural = " + experimentalOut + "\terror = " +  error);
        }
        double averageError = totalError / trainingInputs.length;
        ArrayList<String> toWrite = new ArrayList<String>();
        toWrite.add("Initial Weights: Input to Hidden");
        for( int f=0; f<iIH_weights.length; f++ ) {
        	String next = "{";
        	for( int g=0; g<iIH_weights[0].length; g++ ) {
        		next += iIH_weights[f][g] + ", ";
        	}
        	toWrite.add(next + "},");
        }
        toWrite.add("Initial Weights: Hidden to Output");
        String next = "{";
        for( int f=0; f<iHO_weights.length; f++ ) {
        	next += (iHO_weights[f] + ", ");
        }
        toWrite.add(next + "}");
        int count = 0;
        for( String line : toWrite ) {
        	w.write(line);
        }
        for( int match : outliers ) {
        	String line = "{";
        	for(int f=0; f<trainingInputs[match].length; f++ ) {
        		line += trainingInputs[match][f] + ", ";
        	}
        	y.write(line + "}");
        	y.write("Match Number: " + match + "\tExpected: " + trainingOutputs[match] + "\tActual: " + experimentalOutliers.get(count) + "\tError: " + ((trainingOutputs[match] / experimentalOutliers.get(count)-1)*100));
        	count++;
        }
        System.out.println("Epochs: " + epoch);
        System.out.println("Average Error: " + averageError);
        System.out.println("Outliers (" + outliers.size() + "/" + trainingInputs.length + " | " + (((double) outliers.size()/(double)trainingInputs.length) * 100.0) + "%): ");
        //sitRep();
    }
}