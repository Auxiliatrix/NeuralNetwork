package scouting;

import java.util.ArrayList;

public class SimpleMatchParser implements MatchParser {
	private FileConverter fc;
	
	public SimpleMatchParser() {
		fc = new FileConverter();
	}
	/**
	 * Constructs a SimpleDataParser with an Override option.
	 * @param override determines whether or not to overwrite data with the newest available data.
	 *					otherwise, it will average the data.
	 */
	public SimpleMatchMap process(String fileName)
	{
		ArrayList<SimpleMatch> smm = new ArrayList<SimpleMatch>();
		ArrayList<String> lines = fc.convert(fileName);
		int elementCount = -1;
		boolean blue = false;
		boolean red = false;
		for( String line : lines ) {
			if( line.contains("actual_time") ) {
				SimpleMatch next = new SimpleMatch();
				smm.add(next);
				elementCount++;
			}
			if( line.contains("blue") ) {
				blue = true;
				red = false;
			}
			if( line.contains("red") ) {
				red = true;
				blue = false;
			}
			if( line.contains("score\"") ) {
				int colon = line.indexOf(":");
				int comma = line.indexOf(",");
				String scoreRough = line.substring(colon+2,comma);
				int score = Integer.parseInt(scoreRough);
				if( blue ) {
					smm.get(elementCount).setBlueScore(score);
				} else if( red ) {
					smm.get(elementCount).setRedScore(score);
				}
			}
			if( line.contains("frc") ) {
				int firstQuote = line.indexOf("\"");
				int secondQuote = line.lastIndexOf("\"");
				String teamName = line.substring(firstQuote+1,secondQuote);
				if( blue ) {
					smm.get(elementCount).addBlueTeam(teamName);
				} else if( red ) {
					smm.get(elementCount).addRedTeam(teamName);
				}
			}
			if( line.contains("rotorRankingPointAchieved") ) {
				if( line.contains("true") ) {
					if( blue ) {
						smm.get(elementCount).setBlueRotorRank(true);
					} else if( red ) {
						smm.get(elementCount).setRedRotorRank(true);
					}
				}
			}
			if( line.contains("kPaRankingPointAchieved") ) {
				if( line.contains("true") ) {
					if( blue ) {
						smm.get(elementCount).setBlueKPaRank(true);
					} else if( red ) {
						smm.get(elementCount).setRedKPaRank(true);
					}
				}
			}
			if( line.contains("winning_alliance") ) {
				if( line.contains("blue") ) {
					smm.get(elementCount).setWinner("blue");
				} else if( line.contains("red") ) {
					smm.get(elementCount).setWinner("red");
				}
			}
			if( line.contains("ReadyForTakeoff") ) {
				if( blue ) {
					smm.get(elementCount).addBlueClimb();
				} else if( red ) {
					smm.get(elementCount).addRedClimb();
				}
			}
		}
		return new SimpleMatchMap(smm);
	}
	public SimpleMatchMap process(ArrayList<String> files)
	{
		ArrayList<SimpleMatch> smm = new ArrayList<SimpleMatch>();
		for( String fileName : files ) {
			ArrayList<String> lines = fc.convert(fileName);
			int elementCount = -1;
			boolean blue = false;
			boolean red = false;
			for( String line : lines ) {
				if( line.contains("actual_time") ) {
					SimpleMatch next = new SimpleMatch();
					smm.add(next);
					elementCount++;
				}
				if( line.contains("blue") ) {
					blue = true;
					red = false;
				}
				if( line.contains("red") ) {
					red = true;
					blue = false;
				}
				if( line.contains("score") ) {
					int colon = line.indexOf(":");
					int comma = line.indexOf(",");
					String scoreRough = line.substring(colon+2,comma);
					int score = Integer.parseInt(scoreRough);
					if( blue ) {
						smm.get(elementCount).setBlueScore(score);
					} else if( red ) {
						smm.get(elementCount).setRedScore(score);
					}
				}
				if( line.contains("frc") ) {
					int firstQuote = line.indexOf("\"");
					int secondQuote = line.lastIndexOf("\"");
					String teamName = line.substring(firstQuote+1,secondQuote);
					if( blue ) {
						smm.get(elementCount).addBlueTeam(teamName);
					} else if( red ) {
						smm.get(elementCount).addRedTeam(teamName);
					}
				}
				if( line.contains("rotorRankingPointAchieved") ) {
					if( line.contains("true") ) {
						if( blue ) {
							smm.get(elementCount).setBlueRotorRank(true);
						} else if( red ) {
							smm.get(elementCount).setRedRotorRank(true);
						}
					}
				}
				if( line.contains("kPaRankingPointAchieved") ) {
					if( line.contains("true") ) {
						if( blue ) {
							smm.get(elementCount).setBlueKPaRank(true);
						} else if( red ) {
							smm.get(elementCount).setRedKPaRank(true);
						}
					}
				}
				if( line.contains("winning_alliance") ) {
					if( line.contains("blue") ) {
						smm.get(elementCount).setWinner("blue");
					} else if( line.contains("red") ) {
						smm.get(elementCount).setWinner("red");
					}
				}
				if( line.contains("ReadyForTakeoff") ) {
					if( blue ) {
						smm.get(elementCount).addBlueClimb();
					} else if( red ) {
						smm.get(elementCount).addRedClimb();
					}
				}
			}
		}
		return new SimpleMatchMap(smm);
	}

}
