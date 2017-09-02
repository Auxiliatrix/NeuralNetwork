package scouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SimpleTeamDataMap implements TeamDataMap {
	private HashMap<String, SimpleTeamData> hm;
	
	public SimpleTeamDataMap() {
		hm = new HashMap<String, SimpleTeamData>();
	}
	public SimpleTeamDataMap(HashMap<String, SimpleTeamData> preMade) {
		hm = preMade;
	}
	public void add(String key, SimpleTeamData value) {
		hm.put(key, value);
	}
	public SimpleTeamData get(String key) {
		return hm.get(key);
	}
	public String toString() {
		String ret = "DataSet:";
		Set<String> keys = hm.keySet();
		for( String key : keys ) {
			ret += "\n" + hm.get(key).toString();
		}
		return ret;
	}
}
