package utils;

import java.io.File;

public class FileProducer {
	private static String sourcePath = "./src/main/resources/";

	public static String[] prepareFiles() {
		String[] files = new String[3];
		files[0] = FileProducer.makeSourceFileName();
		files[1] = FileProducer.makeOutputFileName("./outputs/addresses", "/Addresses.txt");
		files[2] = FileProducer.makeOutputFileName("./outputs/centers", "/Centers.txt");
		return files;
	}

	public static String makeSourceFileName() {
		String sourceFileName = null;
		File file = new File(sourcePath);
		String[] files = file.list();
		for (String fileName : files) {
			if (fileName.matches(".*.csv")) {
				if (sourceFileName == null) {
					sourceFileName = sourcePath + fileName;
				} else if (sourceFileName.equals(fileName)) {
					break;
				} else {
					throw new RuntimeException(
							"source folder should contain only 1 .csv file");
				}
			}
		}
		if (sourceFileName == null) {
			throw new RuntimeException(
					"No source file in the source folder. Put "
							+ "the file or check folder naming.");
		}
		return sourceFileName;
	}

	public static String makeOutputFileName(String path, String fileName) {
		String outputFileName = null;
		File outputPath = new File(path);
		boolean created;
		if (!outputPath.exists()) {
			created = outputPath.mkdirs();
		} else {
			created = true;
		}
		if (created) {
			outputFileName = outputPath.getAbsolutePath() + fileName;
			return outputFileName;
		} else {
			throw new RuntimeException("Failed to create output directory");
		}

	}
}
