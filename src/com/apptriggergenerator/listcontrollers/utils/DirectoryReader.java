package com.apptriggergenerator.listcontrollers.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class DirectoryReader {
	static int spc_count = -1;

	static void Process(File aFile, List<File> aux) {
		spc_count++;
		String spcs = "";
		for (int i = 0; i < spc_count; i++)
			spcs += " ";
		if (aFile.isFile()) {
			int longitud = aFile.getName().length();
			if (longitud > 15 && aFile.getName().substring(longitud - 15, longitud).contains("Controller.java")) {
				// System.out.println(spcs + "[FILE] " + aFile.getName());
				aux.add(aFile);
			}
		} else if (aFile.isDirectory()) {
			// System.out.println(spcs + "[DIR] " + aFile.getName());
			File[] listOfFiles = aFile.listFiles();
			if (listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++)
					Process(listOfFiles[i], aux);
			} else {
				System.out.println(spcs + " [ACCESS DENIED]");
			}
		}
		spc_count--;
	}

	static void ProcessDomains(File aFile, List<File> aux) {
		spc_count++;
		String spcs = "";
		for (int i = 0; i < spc_count; i++)
			spcs += " ";
		if (aFile.isFile()) {
			aux.add(aFile);
		} else if (aFile.isDirectory()) {
			// System.out.println(spcs + "[DIR] " + aFile.getName());
			File[] listOfFiles = aFile.listFiles();
			if (listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++)
					ProcessDomains(listOfFiles[i], aux);
			} else {
				System.out.println(spcs + " [ACCESS DENIED]");
			}
		}
		spc_count--;
	}

	public static void main(String[] args) {
		/*
		 * List<File> path = new ArrayList<File>(); Process(showFileChooser(),
		 * path);
		 */
		List<File> path = new ArrayList<File>();
		ProcessDomains(showFileChooser(), path);
		for (File file : path) {
			System.out.println("<class>com.appmodbasico.common.domains." + file.getName().subSequence(0, file.getName().length() - 5) + "</class>");
		}
		// readFiles(path, "@RequestMapping(");
	}

	public static void readFiles(List<File> path, String text) {
		String thisLine;
		int counter = 1;
		try {
			FileWriter fstream = new FileWriter("controllers.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			for (File string : path) {
				FileInputStream fin = new FileInputStream(string);
				// JDK1.1+
				BufferedReader myInput = new BufferedReader(new InputStreamReader(fin));
				while ((thisLine = myInput.readLine()) != null) {
					if (thisLine.contains(text)) {
						out.write(counter + ") " + string.getName() + "\t" + thisLine.trim() + "\r\n");
						counter++;
					}
				}
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File showFileChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
		return f;
	}

	public static String getCurrentPath() throws IOException {
		String currentDir = "";
		if (OSValidator.isWindows()) {
			currentDir = new java.io.File(".").getCanonicalPath() + System.getProperty("file.separator");
		} else {
			if (OSValidator.isUnix()) {
				currentDir = ClassLoader.getSystemClassLoader().getResource(".").getPath();
			}
		}
		return currentDir;
	}
}