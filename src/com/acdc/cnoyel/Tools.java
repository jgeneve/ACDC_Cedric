package com.acdc.cnoyel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Tools {

	private static Process process;
	
	/**
	 * Method that wait user to enter text
	 * 
	 * @return userInput - String entered by the user
	 * @throws IOException on invalid user input
	 */
	public static String getStringUserInput() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method that returns a list of elements separated by a separator in a String
	 * 
	 * @param str - String with elements separated with a separator
	 * @param separator - String to use to split elements
	 * @return list - List<String> elements from the input string split by separator
	 */
	public static List<String> stringToList(String str, String separator) {
		List<String> list = new ArrayList<>();
		list = new ArrayList<String>();
		
		if (str.isEmpty()) {
			return list;
		}
		
		String[] arrayLink = str.split(separator);
		for(int i=0; i<arrayLink.length; i++) {
			list.add(arrayLink[i]);
		}
		return list;
	}

	/** Create a file into the '_post' directory
	 * @param markdownString - String containing all the markdown to insert in the file
	 * @param filePath - String of the filepath to create
	 * @return file - File containing the generated markdown
	 * @throws IOException
	 */
	public static File createMarkdownFile(String markdownString, String filePath) {
		File file = new File(filePath);
		BufferedWriter output;
		try {
			output = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
			output.write(markdownString);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	private static class StreamGobbler implements Runnable {
	    private InputStream inputStream;
	    private InputStream errorStream;
	    private Consumer<String> consumer;
	    public StreamGobbler(InputStream inputStream, Consumer<String> consumer, InputStream errorStream) {
	        this.inputStream = inputStream;
	        this.consumer = consumer;
	        this.errorStream = errorStream;
	    }		 
	    @Override
	    public void run() {
	        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
	    }
	}
		
	/**
	 * Method to execute a command depending of the OS
	 * @param commande - String of the command to execute
	 * @param path - String of the path where the command should be executed
	 * @param stopThread - Boolean used to know if we need to stop the process by ourself or not 
	 */
	public static void executeCmd(String commande, String path, boolean stopThread) {
		final boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");	// To verify if the OS is windows or another
		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
			builder.command("cmd.exe", "/c", commande);	
		} else {
			builder.command("sh", "-c", commande);
		}
		builder.directory(new File(path));
		try {
			Process process = builder.start();
			StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println, process.getErrorStream());		
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			TimeUnit.SECONDS.sleep(3);
			if(!stopThread) {
				int exitCode = process.waitFor();
				assert exitCode == 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
}

	/**
	 * Method to execute in a new thread a given command on a given path
	 * @param cmd - String of the command to execute
	 * @param path - String of the path where to execute the command
	 */
	public static void executeCmd(String cmd, String path) {
		executeCmd(cmd, path, false);
	}
	
	public static void killJekyll() {
		if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
			executeCmd("TASKKILL -F -IM ruby.exe", PropertiesAccess.getInstance().getLocalRepository());
		} else {
			process.destroyForcibly();
		}
	}
	
	/**
	 * Method that runs the commit and push commands of Git
	 * @param githubDirectory : String - link to the remote git adress
	 * @param gitDirectory : String - link to the local website
	 */
	public static void gitCommitAndPush(String localDirectory) {
		Tools.executeCmd("git add .", localDirectory);
		Tools.executeCmd("git commit -m \"Add markdown file\"", localDirectory);
		Tools.executeCmd("git push", localDirectory);
}
}

