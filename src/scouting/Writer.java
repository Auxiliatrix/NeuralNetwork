package scouting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Writer {
	private String fileName;
	
	public Writer(String fileName) {
		this.fileName = fileName;
		try {
			FileReader fileReader = new FileReader(this.fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			fileReader.close();
			bufferedReader.close();
		} catch (Exception e) {
			System.out.println("File creation failed: " + fileName);
		}
	}
	public void write(String line) {
		try {
			FileWriter fstream = new FileWriter(fileName, true);
			BufferedWriter fbw = new BufferedWriter(fstream);
			if (line != null) {
				fbw.write(line);
				fbw.newLine();
				fbw.close();
			}
		} catch (Exception e) {
			System.out.println("Couldn't print to the file.");
		}
	}
	public void clear() {
		try {
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter fbw = new BufferedWriter(fstream);
			fbw.write("");
		} catch (Exception e) {
			System.out.println("Couldn't clear the file.");
		}
	}
}
