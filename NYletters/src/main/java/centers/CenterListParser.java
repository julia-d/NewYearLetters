package centers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import utils.FileProducer;
import utils.Utils;

public class CenterListParser {
	private String sourceFileName;
	private String outputFileName;

	public static void main(String[] args) {
		CenterListParser pc = new CenterListParser();
		List<String> lines = Utils.readSource(pc
				.getSourceFileName());
		List<String[]> countryAndCenter = pc.defineCountryAndCenter(lines);
		TreeMap<String, List<String>> map = pc
				.mapCentersToCountries(countryAndCenter);
		String output = pc.produceOutput(map);
		Utils.writeToFile(output, pc.getOutputFileName());
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	// 1 get files
	public CenterListParser() {
		System.out.println("Source and output file paths set as class fields.");
		String[] files = FileProducer.prepareFiles();
		sourceFileName = files[0];
		outputFileName = files[2];
	}

	// for testing
	public CenterListParser(String source, String output) {
		System.out.println("Source and output file paths set as class fields.");
		sourceFileName = source;
		outputFileName = output;
	}

	// 3) Define country and city in the received strings
	public List<String[]> defineCountryAndCenter(List<String> inputList) {
		System.out.println("Defining countries and centers from the list of input lines.");
		List<String[]> resultList = new LinkedList<String[]>();
		for (String line : inputList) {
			char[] allLine = line.toCharArray();
			resultList.add(this.countryAndCenter(allLine));
		}
		return resultList;
	}

	public String[] countryAndCenter(char[] allLine) {
		String[] countryAndCenter = new String[2];
		int index = 0;
		countryAndCenter[0] = Utils.makeAddressItem(allLine, index);
		index += countryAndCenter[0].length() + 1;

		// country index - unused
		String countryIndex = Utils.makeAddressItem(allLine, index);
		index += countryIndex.length() + 1;
		// region - unused
		String region = Utils.makeAddressItem(allLine, index);
		index += region.length() + 1;

		countryAndCenter[1] = Utils.makeAddressItem(allLine, index);
		return countryAndCenter;

	}

	// 4) Map 1 country - many centers

	public TreeMap<String, List<String>> mapCentersToCountries(
			List<String[]> countryAndCenter) {
		System.out.println("Mapping centers to countries.");
		TreeMap<String, List<String>> resultMap = new TreeMap<String, List<String>>();
		Set<String> keys = new HashSet<String>();
		for (String[] countryCenter : countryAndCenter) {
			keys.add(countryCenter[0]);
			// resultMap.put(countryCenter[0], new LinkedList<String>());
		}
		for (String[] countryCenter : countryAndCenter) {
			if (resultMap.containsKey(countryCenter[0])) {
				resultMap.get(countryCenter[0]).add(countryCenter[1]);
			} else {
				resultMap.put(countryCenter[0], new LinkedList<String>());
				resultMap.get(countryCenter[0]).add(countryCenter[1]);
			}
		}
		return resultMap;
	}

	// 5) Form the output
	public String produceOutput(Map<String, List<String>> countriesAndCenters) {
		System.out.println("Producing a String output with list of countries"
				+ " and its centers.");
		StringBuilder sb = new StringBuilder();
		Set<String> Countries = countriesAndCenters.keySet();
		List<String> centersInCountry;
		for (String country : Countries) {
			sb.append(country);
			sb.append("\n");
			centersInCountry = countriesAndCenters.get(country);
			for (String center : centersInCountry) {
				sb.append(center + ", ");
			}
			sb.append("\n");
			sb.append("\n");
			sb.append("-------------------------------------------");
			sb.append("\n");
			sb.append("\n");
		}
		return sb.toString();
	}

}
