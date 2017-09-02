package scouting;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleDataParser implements DataParser {
	private FileConverter fc;
	private boolean override;
	
	public SimpleDataParser() {
		override = false;
		fc = new FileConverter();
	}
	/**
	 * Constructs a SimpleDataParser with an Override option.
	 * @param override determines whether or not to overwrite data with the newest available data.
	 *					otherwise, it will average the data.
	 */
	public SimpleDataParser(boolean override) {
		this.override = override;
		fc = new FileConverter();
	}
	public SimpleTeamDataMap process(String fileName)
	{
		HashMap<String, SimpleTeamData> tdm = new HashMap<String, SimpleTeamData>();
		ArrayList<String> lines = fc.convert(fileName);
		int opened = 0;
		int closed = 0;
		boolean startA = false;
		boolean startB = false;
		for( String line : lines ) {
			if( line.contains(  "\"stat_mean_vars\": {" ) ) {
				startA = true;
			} else if( startA && line.contains("\"qual\": {") ) {
				startB = true;
				opened++;
			} else if( startA && startB ) {
				boolean meanGears = false;
				boolean meanPressure = false;
				if( line.contains("{") )
					opened++;
				else if( line.contains("}") )
					closed++;
				else {
					if( opened == 3 && closed == 0 ) {
						meanGears = true;
					}
					if( opened == 6 && closed == 3 ) {
						meanPressure = true;
					}
				}
				if( meanGears ) {
					if( !line.endsWith(",") ) {
						line += ",";
					}
					int firstQuote = line.indexOf("\"");
					int secondQuote = line.lastIndexOf("\"");
					int colon = line.indexOf(":");
					int comma = line.indexOf(",");
					String numberString = line.substring(colon+2,comma);
					String name = line.substring(firstQuote+1, secondQuote);
					double num = Double.parseDouble(numberString);
					if( !tdm.containsKey(name) ) {
						tdm.put(name, new SimpleTeamData(name));
					}
					
					SimpleTeamData temp = tdm.get(name);
					if(override)
						temp.setMeanGears(num);
					else
						temp.addMeanGears(num);
					tdm.put(name, temp);
				}
				if( meanPressure ) {
					if( !line.endsWith(",") ) {
						line += ",";
					}
					int firstQuote = line.indexOf("\"");
					int secondQuote = line.lastIndexOf("\"");
					int colon = line.indexOf(":");
					int comma = line.indexOf(",");
					String numberString = line.substring(colon+2,comma);
					String name = line.substring(firstQuote+1, secondQuote);
					double num = Double.parseDouble(numberString);
					if( !tdm.containsKey(name) ) {
						tdm.put(name, new SimpleTeamData(name));
					}
					
					SimpleTeamData temp = tdm.get(name);
					if(override)
						temp.setMeanPressure(num);
					else
						temp.addMeanPressure(num);
					tdm.put(name, temp);
				}
			}
		}
		return new SimpleTeamDataMap(tdm);
	}
	public SimpleTeamDataMap process(ArrayList<String> files)
	{
		HashMap<String, SimpleTeamData> tdm = new HashMap<String, SimpleTeamData>();
		for( String fileName : files ) {
			ArrayList<String> lines = fc.convert(fileName);
			int opened = 0;
			int closed = 0;
			boolean startA = false;
			boolean startB = false;
			for( String line : lines ) {
				if( line.contains(  "\"stat_mean_vars\": {" ) ) {
					startA = true;
				} else if( startA && line.contains("\"qual\": {") ) {
					startB = true;
					opened++;
				} else if( startA && startB ) {
					boolean meanGears = false;
					boolean meanPressure = false;
					if( line.contains("{") )
						opened++;
					else if( line.contains("}") )
						closed++;
					else {
						if( opened == 3 && closed == 0 ) {
							meanGears = true;
						}
						if( opened == 6 && closed == 3 ) {
							meanPressure = true;
						}
					}
					
					if( meanGears ) {
						if( !line.endsWith(",") ) {
							line += ",";
						}
						int firstQuote = line.indexOf("\"");
						int secondQuote = line.lastIndexOf("\"");
						int colon = line.indexOf(":");
						int comma = line.indexOf(",");
						String numberString = line.substring(colon+2,comma);
						String name = line.substring(firstQuote+1, secondQuote);
						double num = Double.parseDouble(numberString);
						if( !tdm.containsKey(name) ) {
							tdm.put(name, new SimpleTeamData(name));
						}
						
						SimpleTeamData temp = tdm.get(name);
						if(override)
							temp.setMeanGears(num);
						else
							temp.addMeanGears(num);
						tdm.put(name, temp);
					}
					if( meanPressure ) {
						if( !line.endsWith(",") ) {
							line += ",";
						}
						int firstQuote = line.indexOf("\"");
						int secondQuote = line.lastIndexOf("\"");
						int colon = line.indexOf(":");
						int comma = line.indexOf(",");
						String numberString = line.substring(colon+2,comma);
						String name = line.substring(firstQuote+1, secondQuote);
						double num = Double.parseDouble(numberString);
						if( !tdm.containsKey(name) ) {
							tdm.put(name, new SimpleTeamData(name));
						}
						
						SimpleTeamData temp = tdm.get(name);
						if(override)
							temp.setMeanPressure(num);
						else
							temp.addMeanPressure(num);
						tdm.put(name, temp);
					}
				}
			}
		}
		return new SimpleTeamDataMap(tdm);
	}
}
