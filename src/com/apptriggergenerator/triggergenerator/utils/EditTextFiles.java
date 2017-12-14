package com.apptriggergenerator.triggergenerator.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

import com.apptriggergenerator.listcontrollers.utils.ConfigManager;

public final class EditTextFiles {
	private static final Logger LOGGER = ConfigManager.getLogger(EditTextFiles.class);
	private static Properties functions;
	private static InputStream in = null;
	private String[] auxCommonFiles = { "delegateFormat.txt", "ejbFormat.txt", "ejbRemoteFormat.txt", "persistenceFormat.txt", "web-application-configFormat.txt",
	        "web-application-configPropertiesFormat.txt" };
	private List<String> commonFiles = new ArrayList<String>(Arrays.asList(auxCommonFiles));

	public EditTextFiles() {
		functions = new Properties();
		functions.setProperty("${fn:forAtributes}", "forAtributes");
		functions.setProperty("${fn:for}", "for");
	}

	public void readTemplateAndCreateFileDomains(File destinationFolder, String subFolder, Properties properties, String sourceFile, String resultFile, List<Properties> records)
	        throws FileNotFoundException, IOException {
		String str;
		File parentFolder;
		if (subFolder != null) {
			parentFolder = new File(destinationFolder, subFolder);
		} else {
			parentFolder = destinationFolder;
		}
		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}
		// reader
		in = EditTextFiles.class.getResourceAsStream("/" + sourceFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		// BufferedReader br = new BufferedReader(new
		// FileReader(DirectoryReader.getCurrentPath() + sourceFile));
		// writer
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(parentFolder, resultFile)));
		// for each property key, replace all occurrences with appropriate
		// element.
		Set<Object> keys = properties.keySet();
		boolean functionFound = false;
		String auxStringFunctions = "";
		while ((str = br.readLine()) != null) {
			Set<Object> functionsKeys = functions.keySet();
			for (Object functionsKey : functionsKeys) {
				if (!functionFound) {
					functionFound = str.contains(String.valueOf(functionsKey));
					if (functionFound) {
						str = br.readLine();
						auxStringFunctions = "";
					}
				} else {
					if (str.contains("/" + String.valueOf(functionsKey))) {
						functionFound = false;
						str = executeFunctionCreateAtributes(functions.getProperty(String.valueOf(functionsKey)), auxStringFunctions, records);
						br.readLine();
						auxStringFunctions = "";
					}
				}
			}
			if (functionFound) {
				auxStringFunctions += str + "\n";
			} else {
				for (Object key : keys) {
					String keyName = (String) key;
					String value = (String) properties.get(key);
					str = str.replace(keyName, value);
				}
				str = str + "\n";
				bw.write(str);
			}
		}
		// must close file when done.
		bw.close();
	}

	public void readTemplateAndCreateFile(File destinationFolder, String subFolder, Properties properties, String sourceFile, String resultFile, List<Properties> records)
	        throws FileNotFoundException, IOException {
		String str;
		File parentFolder;
		if (subFolder != null) {
			parentFolder = new File(destinationFolder, subFolder);
		} else {
			parentFolder = destinationFolder;
		}
		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}
		// reader
		File auxDestinationFile = new File(parentFolder, resultFile);
		boolean addToFile = auxDestinationFile.exists() && commonFiles.contains(sourceFile);
		if (addToFile) {
			sourceFile = sourceFile.replace(".txt", "Created") + ".txt";
		}
		in = EditTextFiles.class.getResourceAsStream("/" + sourceFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		// BufferedReader br = new BufferedReader(new
		// FileReader(DirectoryReader.getCurrentPath() + sourceFile));
		// writer
		// for each property key, replace all occurrences with appropriate
		// element.
		Set<Object> keys = properties.keySet();
		boolean functionFound = false;
		String auxStringFunctions = "";
		String finalString = "";
		while ((str = br.readLine()) != null) {
			Set<Object> functionsKeys = functions.keySet();
			for (Object functionsKey : functionsKeys) {
				if (!functionFound) {
					functionFound = str.contains(String.valueOf(functionsKey));
					if (functionFound) {
						str = br.readLine();
						auxStringFunctions = "";
					}
				} else {
					if (str.contains("/" + String.valueOf(functionsKey))) {
						functionFound = false;
						str = executeFunction(functions.getProperty(String.valueOf(functionsKey)), auxStringFunctions, records);
						auxStringFunctions = "";
					}
				}
			}
			if (functionFound) {
				auxStringFunctions += str + "\n";
			} else {
				for (Object key : keys) {
					String keyName = (String) key;
					String value = (String) properties.get(key);
					str = str.replace(keyName, value);
				}
				str = str + "\n";
				finalString += str;
			}
		}
		if (addToFile) {
			if (sourceFile.equals("persistenceFormatCreated.txt")) {
				if (!(findSpecificLine(auxDestinationFile, "<class>" + properties.getProperty("${domainPackage}") + "." + properties.getProperty("${domain_name}") + "</class>") != -1)) {
					int posDs = findSpecificLine(auxDestinationFile, "<!--DS -->");
					if (posDs != -1) {
						removeLineFromFile(auxDestinationFile, posDs);
						addLinesToFile(auxDestinationFile, posDs, "     <jta-data-source>java:/" + properties.getProperty("${application_data_source}") + "</jta-data-source>");
					}
					int posDomain = findSpecificLine(auxDestinationFile, "<!--Mapping Files & Domains -->");
					if (posDomain != -1) {
						removeLineFromFile(auxDestinationFile, posDomain);
						String newPersistenceDomain = "        <mapping-file>META-INF/named-query-" + properties.getProperty("${domain_name}") + ".xml</mapping-file>\n"
						        + "        <!--Mapping Files & Domains -->\n" + "        <class>" + properties.getProperty("${domainPackage}") + "." + properties.getProperty("${domain_name}")
						        + "</class>";
						if (findSpecificLine(auxDestinationFile, newPersistenceDomain) == -1) {
							addLinesToFile(auxDestinationFile, posDomain, newPersistenceDomain);
						}
					}
				}
			} else {
				if (sourceFile.equals("web-application-configFormatCreated.txt")) {
					int posTCF = findSpecificLine(auxDestinationFile, "<!-- Reserved For Tiles Config Files -->");
					if (posTCF != -1) {
						String newPersistenceDomain = "                 <value>/WEB-INF/config/tiles/" + properties.getProperty("${webModule_name}") + "/tiles-"
						        + properties.getProperty("${domain_name}") + ".xml</value>";
						if (findSpecificLine(auxDestinationFile, newPersistenceDomain) == -1) {
							addLinesToFile(auxDestinationFile, posTCF, newPersistenceDomain);
						}
					}
				} else {
					if (sourceFile.equals("web-application-configPropertiesFormatCreated.txt")) {
						int posTCF = findSpecificLine(auxDestinationFile, "<!-- Reserved For Properties Files -->");
						if (posTCF != -1) {
							String newPersistenceDomain = "               <value>" + properties.getProperty("${webModule_name}") + "/" + properties.getProperty("${domain_name}") + "</value>";
							if (findSpecificLine(auxDestinationFile, newPersistenceDomain) == -1) {
								addLinesToFile(auxDestinationFile, posTCF, newPersistenceDomain);
							}
						}
					} else {
						if (!((sourceFile.equals("ejbFormatCreated.txt") || sourceFile.equals("ejbRemoteFormatCreated.txt") || sourceFile.equals("delegateFormatCreated.txt")) && findSpecificLine(
						        auxDestinationFile, "// " + properties.getProperty("${domain_name}")) != -1)) {
							removeCharactersFromFile(auxDestinationFile, 2);
							String packageName = readSpecificLine(auxDestinationFile, 0);
							System.out.println(packageName);
							packageName += "\n" + "import " + properties.getProperty("${domainPackage}") + "." + properties.getProperty("${domain_name}") + ";\n";
							removeLineFromFile(auxDestinationFile, 0);
							AddLinesToBeginingOfFile(auxDestinationFile, packageName);
							AddLinesToFile(auxDestinationFile, finalString);
						}
					}
				}
			}
		} else {
			BufferedWriter bw = new BufferedWriter(new FileWriter(auxDestinationFile));
			bw.write(finalString);
			bw.close();
		}
		// must close file when done.
	}

	public String executeFunction(String functionName, String content, List<Properties> records) {
		String result = "";
		String subResult = "";
		if (functionName.equals("for")) {
			if (records != null) {
				for (Properties properties : records) {
					subResult = content;
					Set<Object> keys = properties.keySet();
					for (Object key : keys) {
						String keyName = (String) key;
						String value = (String) properties.get(key);
						subResult = subResult.replace(keyName, value);
					}
					result += subResult + "\n";
				}
			}
		}
		return result;
	}

	public static String executeFunctionCreateAtributes(String functionName, String content, List<Properties> records) {
		String result = "";
		String subResult = "";
		if (functionName.equals("forAtributes")) {
			for (Properties properties : records) {
				subResult = content;
				Set<Object> keys = properties.keySet();
				for (Object key : keys) {
					String keyName = (String) key;
					String value = (String) properties.get(key);
					if (keyName.equals("${column_length}") && value.equals("0")) {
						subResult = subResult.replace(", length=${column_length}", "");
					} else {
						subResult = subResult.replace(keyName, value);
					}
				}
				if (properties.get("${column_type}").equals("Calendar")) {
					subResult = "	@Temporal(TemporalType.TIMESTAMP)\n" + subResult;
				}
				result += subResult + "\n";
			}
		} else {
			if (functionName.equals("for")) {
				for (Properties properties : records) {
					subResult = content;
					Set<Object> keys = properties.keySet();
					for (Object key : keys) {
						String keyName = (String) key;
						String value = (String) properties.get(key);
						subResult = subResult.replace(keyName, value);
					}
					result += subResult + "\n";
				}
			}
		}
		return result;
	}

	public void AddLinesToFile(File file, String line) {
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(line);// appends the string to the file
			fw.close();
		} catch (IOException e) {
			ConfigManager.printException(LOGGER, e);
		}
	}

	public void AddLinesToBeginingOfFile(File originalFile, String line) {
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(originalFile, "rws");
			byte[] text = new byte[(int) file.length()];
			file.readFully(text);
			file.seek(0);
			file.writeBytes(line);
			file.write(text);
			file.close();
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
		}
	}

	public String readSpecificLine(File originalFile, int lineNumberToReturn) {
		int count = 0;
		Scanner scanner;
		try {
			scanner = new Scanner(originalFile);
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				// if (nextLine.equalsIgnoreCase(word))
				if (lineNumberToReturn == count) {
					return nextLine;
				}
				count++;
			}
		} catch (FileNotFoundException e) {
			ConfigManager.printException(LOGGER, e);
		}
		return null;
	}

	public int findSpecificLine(File originalFile, String textToFind) {
		int count = 0;
		Scanner scanner;
		textToFind = textToFind.trim();
		try {
			scanner = new Scanner(originalFile);
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if (nextLine.trim().equalsIgnoreCase(textToFind)) {
					return count;
				}
				count++;
			}
		} catch (FileNotFoundException e) {
			ConfigManager.printException(LOGGER, e);
		}
		return -1;
	}

	public int findSpecificContentOfLine(File originalFile, String textToFind) {
		int count = 0;
		Scanner scanner;
		textToFind = textToFind.trim();
		try {
			scanner = new Scanner(originalFile);
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if (nextLine.trim().contains(textToFind)) {
					return count;
				}
				count++;
			}
		} catch (FileNotFoundException e) {
			ConfigManager.printException(LOGGER, e);
		}
		return -1;
	}

	public int countWord(String word, File originalFile) {
		int count = 0;
		Scanner scanner;
		try {
			scanner = new Scanner(originalFile);
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if (nextLine.equalsIgnoreCase(word))
					count++;
			}
			return count;
		} catch (FileNotFoundException e) {
			ConfigManager.printException(LOGGER, e);
			return 0;
		}
	}

	public void removeCharactersFromFile(File originalFile, int numberCharactersToRemove) {
		try {
			RandomAccessFile file = new RandomAccessFile(originalFile, "rw");
			long length = file.length();
			// Assume that last line is of 10 char
			file.setLength(length - numberCharactersToRemove);
			file.close();
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
		}
	}

	public void removeLineFromFile(File inFile, Integer... linesToRemove) {
		try {
			Scanner scanner = new Scanner(inFile);
			ArrayList<String> coll = new ArrayList<String>();
			int counter = 0;
			List<Integer> auxLinesToRemove = new ArrayList<Integer>(Arrays.asList(linesToRemove));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!auxLinesToRemove.contains(counter)) {
					coll.add(line);
				}
				counter++;
			}
			scanner.close();
			FileWriter writer = new FileWriter(inFile);
			for (String line : coll) {
				writer.write(line + "\n");
			}
			writer.close();
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
		}
	}

	public void addLinesToFile(File inFile, int lineNumber, String text) {
		try {
			Scanner scanner = new Scanner(inFile);
			ArrayList<String> coll = new ArrayList<String>();
			int counter = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (lineNumber == counter) {
					coll.add(text);
				}
				coll.add(line);
				counter++;
			}
			scanner.close();
			FileWriter writer = new FileWriter(inFile);
			for (String line : coll) {
				writer.write(line + "\n");
			}
			writer.close();
		} catch (Exception e) {
			ConfigManager.printException(LOGGER, e);
		}
	}

	public void replaceLinesToFile(File inFile, int lineNumber, String textToSearch, String textToReplace) {
		String lineText = readSpecificLine(inFile, findSpecificContentOfLine(inFile, textToSearch));
		lineText = lineText.replace(textToSearch, textToReplace);
		removeLineFromFile(inFile, lineNumber);
		addLinesToFile(inFile, lineNumber, lineText);
	}

	public void searchAndAddToFile(File auxDestinationFile, String textToSearch, String textIdentifiyer, String textToReplace) {
		int posTCF = findSpecificContentOfLine(auxDestinationFile, textToSearch);
		if (posTCF != -1) {
			if (findSpecificLine(auxDestinationFile, textIdentifiyer) == -1) {
				addLinesToFile(auxDestinationFile, posTCF, textToReplace);
			}
		}
	}

	public void searchLineCOntentAndAddToFile(File auxDestinationFile, String textToSearch, String textIdentifiyer, String textToReplace) {
		int posTCF = findSpecificContentOfLine(auxDestinationFile, textToSearch);
		if (posTCF != -1) {
			if (findSpecificContentOfLine(auxDestinationFile, textIdentifiyer) == -1) {
				replaceLinesToFile(auxDestinationFile, posTCF, textToSearch, textToReplace + textToSearch);
			}
		}
	}

	public static void main(String arg[]) {
		EditTextFiles editTextFiles = new EditTextFiles();
		String textToWrite = "test";
		editTextFiles.searchLineCOntentAndAddToFile(new File(
		        "D:\\Percy\\cmerke_workspace\\AppCMerkeWeb\\programacionacademica\\com\\appcmerkeweb\\programacionacademica\\controllers\\Cmtbl_MateriasController.java"),
		        "/* Reserved for Details Domains */", textToWrite, textToWrite);
	}
}
