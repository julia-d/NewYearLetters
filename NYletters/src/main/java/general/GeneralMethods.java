package general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GeneralMethods {

	public static String removeExtra(String line, String pattern1,
			String pattern2, String pattern3) {
		String temp;
		temp = line.replaceAll(pattern1, "");
		temp = temp.replaceAll(pattern2, "");
		temp = temp.replaceAll(pattern3, "");
		return temp;

	}
	
	public static List<String> readSourceRemoveUnused(String sourceFileName) {
		System.out.println("Reading " + sourceFileName + ". Removing unused parts.");
		List<String> resultList = new LinkedList<String>();
		String patternRemove1 = "CENTERS;\\d+\\;";
		String patternRemove2 = "\\+\\d+(.*)";
		String patternRemove3 = ";\\w+(@(.*))";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(sourceFileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Can't find " + sourceFileName, e);
		}
		String line = "";
		try {
		while((line = br.readLine()) != null) {
			resultList.add(GeneralMethods.removeExtra(line, patternRemove1, patternRemove2, patternRemove3));
		}
		} catch(IOException e) {
			GeneralMethods.closeQuietly(br);
			throw new RuntimeException("Can't read " + sourceFileName, e);
		}
		
		GeneralMethods.closeQuietly(br);
		return resultList;
	}
	
	public static void writeToFile(String resultString, String outputFileName) {
		System.out.println("Writing the output String to the output file - " + outputFileName);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(outputFileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Input file not found", e);
		} catch (IOException e) {
			throw new RuntimeException("Can't create BufferedWriter, e");
		}
		try {
			bw.write(resultString);
		} catch (IOException e) {
			GeneralMethods.flushAndCloseQuietly(bw);
			throw new RuntimeException("Can't write output file", e);
		}
		GeneralMethods.flushAndCloseQuietly(bw);
		
	}
	
	public static void flushAndCloseQuietly(BufferedWriter bw) {
		try {
			bw.flush();
		} catch (IOException e) {
			// do nothing
		}
		try {
			bw.close();
		} catch (IOException e) {
			// do nothing
		}
	}
	public static void closeQuietly(BufferedReader br) {
		try {
			br.close();
		} catch(IOException e) {
			// do nothing;
		}
	}
	
	public static String makeAddressItem(char[] arr, int index) {
		String addressItem = "";
		while ((index < arr.length) && (arr[index] != ';')) {
			addressItem += arr[index];
			index++;
		}
		addressItem = addressItem.toUpperCase();
		return addressItem;
	}

}
