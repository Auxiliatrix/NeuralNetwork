package scouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SimpleMatchMap implements MatchMap {
	private ArrayList<SimpleMatch> sm;
	
	public SimpleMatchMap() {
		sm = new ArrayList<SimpleMatch>();
	}
	public SimpleMatchMap(ArrayList<SimpleMatch> preMade) {
		sm = preMade;
	}
	public void add(SimpleMatch value) {
		sm.add(value);
	}
	public SimpleMatch get(int index) {
		return sm.get(index);
	}
	public ArrayList<SimpleMatch> asArrayList() {
		return sm;
	}
	public String toString() {
		String ret = "DataSet:";
		for( SimpleMatch smi : sm ) {
			ret += "\n" + smi.toString();
		}
		return ret;
	}
}
