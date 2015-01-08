import static org.junit.Assert.*;

import java.util.List;

import general.GeneralMethods;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import addresses.AddressParser;

public class TestParseAddress {
	private static AddressParser pa;
	private static List<String> sourceLines;
	private static List<String[]> addresses;
	private static int addressDetailsCount;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String sourceFile = "./src/test/resources/All Buddhist centers addresses - November 2014 .csv";
		String outputFile = "./src/test/resources/TestAddressOutput.txt";
		pa = new AddressParser(sourceFile, outputFile);

		addressDetailsCount = 5;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		pa = null;
	}

	@Before
	public void setUp() throws Exception {
		sourceLines = GeneralMethods.readSourceRemoveUnused(pa
				.getSourceFileName());
		addresses = pa.extractAddressDetails(sourceLines);

	}

	@After
	public void tearDown() throws Exception {
		addresses = null;
	}

	@Test
	public void testAddressWithHost() {
		String[] minkovkaAddress = null;
		for (String[] address : addresses) {
			for (String detail : address) {
				if (detail.equals("MINKOVKA")) {
					minkovkaAddress = address;
				}
			}
		}

		assertNotNull(minkovkaAddress);
		assertEquals(minkovkaAddress.length, addressDetailsCount);
		assertEquals("YULIA KASIANOVA", minkovkaAddress[0]);
		assertEquals("", minkovkaAddress[1]);
		assertEquals("MINKOVKA", minkovkaAddress[2]);
		assertEquals("", minkovkaAddress[3]);
		assertEquals(" UKRAINE", minkovkaAddress[4]);
	}

	@Test
	public void testAddressNoHost() {
		String[] beogradAddress = null;
		for (String[] address : addresses) {
			for (String detail : address) {
				if (detail.equals("BEOGRAD")) {
					beogradAddress = address;
				}
			}
		}

		assertNotNull(beogradAddress);
		assertEquals(beogradAddress.length, addressDetailsCount);
		assertEquals("DIAMOND WAY BUDDHIST CENTER", beogradAddress[0]);
		assertEquals("DALMATINSKA 62/4", beogradAddress[1]);
		assertEquals("BEOGRAD", beogradAddress[2]);
		assertEquals("BEOGRAD", beogradAddress[3]);
		assertEquals("SRB-11000 SERBIA", beogradAddress[4]);
	}
	
	@Test
	public void testOutputManually() {
		String output = pa.produceOutput(addresses);
		GeneralMethods.writeToFile(output, pa.getOutputFileName());
	}

}
