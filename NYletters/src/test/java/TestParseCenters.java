import static org.junit.Assert.*;
import general.GeneralMethods;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import centers.CenterListParser;

public class TestParseCenters {
	private static CenterListParser pc;
	private static TreeMap<String, List<String>> map;
	private static List<String> sourceLines;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String sourceFile = "./src/test/resources/All Buddhist centers addresses - November 2014 .csv";
		String outputFile = "./src/test/resources/TestCentersOutput.txt";
		pc = new CenterListParser(sourceFile, outputFile);
		sourceLines = GeneralMethods.readSourceRemoveUnused(pc
				.getSourceFileName());

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		pc = null;
		sourceLines = null;

	}

	@Before
	public void setUp() throws Exception {
		map = pc.mapCentersToCountries(pc.defineCountryAndCenter(sourceLines));
	}

	@After
	public void tearDown() throws Exception {
		map = null;
	}

	@Test
	public void testCities() {
		List<String> ukrCentersForCompare = new LinkedList<String>(
				Arrays.asList("KIEV", "CHERNIHIV", "DNEPROPETROVSK", "DONETSK",
						"KHARKOV", "LUGANSK", "LVIV", "MELITOPOL", "MINKOVKA",
						"MUKACHEVO", "ODESSA", "POLTAVA", "SEVASTOPOL",
						"STAKHANOV", "UZHGOROD", "ZAPOROZHYE", "ZNAMIN"));

		List<String> ukrCenters = null;
		Set<String> countries = map.keySet();
		for (String country : countries) {
			if (country.matches("UKRAINE")) {
				ukrCenters = map.get(country);
			}
		}

		assertNotNull(ukrCenters);

		int countMatches = 0;
		for (String center : ukrCenters) {
			for (String cen : ukrCentersForCompare) {
				if (cen.matches(center)) {
					countMatches++;
				}
			}
		}
		assertEquals(countMatches, ukrCentersForCompare.size());
	}

	@Test
	public void testQuantity() {
		int countUkrCentersInline = 0;
		for (String line : sourceLines) {
			if (line.matches("(.*)UKR(.*)")) {
				countUkrCentersInline++;
			}

		}

		int countUkrCentersOutput = 0;
		Set<String> countries = map.keySet();
		for (String country : countries) {
			if (country.matches("UKRAINE")) {
				countUkrCentersOutput = map.get(country).size();
			}
		}

		assertEquals(countUkrCentersInline, countUkrCentersOutput);
		assertNotSame(countUkrCentersInline, 0);
		assertNotSame(countUkrCentersOutput, 0);
	}
	
	@Test
	public void testOutputManually() {
		String output = pc.produceOutput(map);
		GeneralMethods.writeToFile(output, pc.getOutputFileName());
	}

}
