package addresses;

import general.FileProducer;
import general.GeneralMethods;

import java.util.LinkedList;
import java.util.List;

public class AddressParser {
	private String sourceFileName;
	private String outputFileName;

	public static void main(String[] args) {
		AddressParser pa = new AddressParser();
		List<String> lines = GeneralMethods.readSourceRemoveUnused(pa
				.getSourceFileName());
		List<String[]> addressDetails = pa.extractAddressDetails(lines);
		String ouput = pa.produceOutput(addressDetails);
		GeneralMethods.writeToFile(ouput, pa.getOutputFileName());
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	// 1. Find file paths
	public AddressParser() {
		System.out.println("Source and output file paths set as class fields.");
		String[] files = FileProducer.prepareFiles();
		sourceFileName = files[0];
		outputFileName = files[1];
	}

	// for test
	public AddressParser(String source, String output) {
		System.out.println("Source and output file paths set as class fields.");
		sourceFileName = source;
		outputFileName = output;
	}

	// 2. Extract address details.
	public List<String[]> extractAddressDetails(List<String> inputList) {
		System.out
				.println("Extracting address details from input list with lines.");
		List<String[]> resultList = new LinkedList<String[]>();
		System.out.println("Preparing addresses in an output format.");
		for (String line : inputList) {
			resultList.add(this.makeAddress(line.toCharArray()));

		}
		return resultList;

	}

	public String[] makeAddress(char[] allLine) {
		int index = 0;
		String[] address = new String[5];

		// country
		String country = GeneralMethods.makeAddressItem(allLine, index);
		index += country.length() + 1;

		// country index - unused
		String countryIndex = GeneralMethods.makeAddressItem(allLine, index);
		index += countryIndex.length() + 1;

		// region - unused
		String region = GeneralMethods.makeAddressItem(allLine, index);
		index += region.length() + 1;

		// city
		String city = GeneralMethods.makeAddressItem(allLine, index);
		index += city.length() + 1;

		// host
		String host = GeneralMethods.makeAddressItem(allLine, index);
		index += host.length() + 1;
		if (host.isEmpty()) {
			host = "DIAMOND WAY BUDDHIST CENTER";
		}

		// street
		String street = GeneralMethods.makeAddressItem(allLine, index);
		index += street.length() + 1;

		// zip
		String zip = GeneralMethods.makeAddressItem(allLine, index);
		index += zip.length() + 1;

		// city location
		String location = GeneralMethods.makeAddressItem(allLine, index);

		// group the address
		address[0] = host;
		address[1] = street;
		address[2] = city;
		address[3] = location;
		address[4] = zip + " " + country;

		return address;
	}

	// 3. produce output string
	public String produceOutput(List<String[]> list) {
		System.out.println("Producing a String output with addresses.");
		StringBuilder sb = new StringBuilder();
		for (String[] address : list) {
			sb.append("\n");
			sb.append("------------------------------");
			sb.append("\n");
			sb.append("\n");
			for (String item : address) {
				sb.append("| ");
				sb.append(item);
				sb.append("\n");
			}
		}

		return sb.toString();
	}

}
