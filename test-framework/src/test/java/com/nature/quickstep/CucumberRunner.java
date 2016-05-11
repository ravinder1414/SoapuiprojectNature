package com.nature.quickstep;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.nature.quickstep.util.Context;
import com.nature.quickstep.util.Context.Mode;

import cucumber.api.cli.Main;
import cucumber.runtime.CucumberException;

public class CucumberRunner {

	boolean success = true;

	int maxThreads = 1;
	Mode mode;
	public static String featuresLocation = "target/test-classes/cucumber/features";

	List<Process> processes;
	List<String> featureFiles;
	List<String> cucumberTags;

	Map<Process, Integer> processMap = new HashMap<Process, Integer>();
	int lastProcessID = 0;

	public static void main(String[] args) throws Throwable {

		new CucumberRunner();

	}

	public CucumberRunner() {

		// Obtain test run info and display
		Context.init();
		maxThreads = Context.getMaxThreads();
		mode = Context.getMode();

		// Build feature files list
		buildFeatureFilesList();

		// Build Cucumber Tag List
		buildCucumberTagList();

		// Display test run parameters to user
		System.out.println("\n\n===========================================");
		System.out.println("Test execution parameters");
		System.out.println("===========================================");
		System.out.println("Mode:        " + mode.toString());
		System.out.println("Max threads: " + maxThreads);
		System.out.println("Browser:     "
				+ Context.getBrowserType().toString());
		System.out.println("Features:    " + featureFiles.size());
		System.out.println("Environment: "
				+ Context.getEnvironment().toString());
		System.out.println("Tags       : " + cucumberTags.toString());
		System.out.println("===========================================\n\n");

		// Save context to maintain consistency with forked processes
		// TODO: Find a better mechanism for this which preserves current
		// properties file
		Context.save();

		// Make magic happen
		manageThreads();
	}

	private void buildCucumberTagList() {

		cucumberTags = new ArrayList<String>();

		if (mode == Mode.dev) {
			cucumberTags.add("@wip");
		} else if (mode == Mode.test) {
			cucumberTags.add("~@new");
			cucumberTags.add("~@manual");
			cucumberTags.add("~@to_automate");
			cucumberTags.add("~@wip");
			cucumberTags.add("~@broken");
		}
		
		String[] customTags = Context.getTags();
		for (String tag : customTags) {
			cucumberTags.add(tag);
		}
	}

	private void buildFeatureFilesList() {

		featureFiles = new ArrayList<String>();

		// Recursively list all files in featuresLocation
		File featuresDirectory = new File(featuresLocation);
		Collection<File> allFiles = FileUtils.listFiles(featuresDirectory,
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

		// Filter out files which are not feature files
		for (File f : allFiles) {
			if (f.getAbsolutePath().endsWith(".feature")) {
				featureFiles.add(f.getPath());
			}
		}

	}

	private void killAllProcesses() {
		for (Process p : processes) {
			p.destroy();
		}
	}

	private void singleThreadExecution() {

		long startTime = System.currentTimeMillis();

		System.out.println("Running all features in" + featuresLocation);

		List<String> args = new ArrayList<String>();
		args.add(featuresLocation);
		args.add("--glue");
		args.add("com.nature.quickstep");

		for (String tag : cucumberTags) {
			args.add("--tags");
			args.add(tag);
		}

		args.add("--format");
		args.add("json:target/cucumber-report/report" + ".json");
		
		args.add("--format");
		args.add("html:target/cucumber-report/report" + ".html");

		try {

			Main.main(args.toArray(new String[0]));
		} catch (CucumberException ce) {
			if (ce.getMessage().contains("None of the features at")) {
				System.out
						.println("None of the features matched the filters provided.");
			} else {
				ce.printStackTrace();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.out.println("\n\n===========================================");
		System.out.println("Total System Test Duration: "
				+ (System.currentTimeMillis() - startTime) / 1000 + " seconds");
		System.out.println("===========================================\n\n");

	}

	private void manageThreads() {

		if (maxThreads == 1) {
			singleThreadExecution();
		} else {
			multiThreadExecution();

			if (!success) {
				System.exit(1);
			}
		}
	}

	public void multiThreadExecution() {

		long startTime = System.currentTimeMillis();

		// Install a shutdown hook to kill all processes in case of a forced
		// shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				killAllProcesses();
			}
		});

		processes = new ArrayList<Process>();

		while (!featureFiles.isEmpty()) {

			// Check if a free thread exists
			if (processes.size() < maxThreads) {
				startProcess(featureFiles.get(0));

				try {
					// Allow 1 second before starting the next process in order
					// to mitigate risk of multiple processes trying to start a
					// browser at exactly the same time
					Thread.sleep(1000);
				} catch (Exception e) {
				}

				featureFiles.remove(0);
			} else {

				// Wait for a free thread
				try {
					Thread.sleep(1000);
					for (Process process : processes) {

						try {
							int exitValue = process.exitValue();

							// Check if there were any failures
							success = success & (exitValue == 0);

							dumpProcessOutput(process);
							processes.remove(process);
							break;
						} catch (IllegalThreadStateException itse) {
							// Thread is still alive
						}

					}
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}

		// Wait for all threads to finish
		while (!processes.isEmpty()) {
			try {
				Thread.sleep(1000);
				for (Process process : processes) {
					try {
						int exitValue = process.exitValue();

						// Check if there were any failures and update build
						// success flag
						success = success & (exitValue == 0);

						dumpProcessOutput(process);
						processes.remove(process);
						break;
					} catch (IllegalThreadStateException itse) {
						// Thread is still alive
					}

				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		System.out.println("\n\n===========================================");
		System.out.println("Total System Test Duration: "
				+ (System.currentTimeMillis() - startTime) / 1000 + " seconds");
		System.out.println("===========================================\n\n");

	}

	private void startProcess(String featureFile) {

		lastProcessID++;
		int processID = lastProcessID;

		System.out.println("Running " + featureFile + " (Process ID: "
				+ lastProcessID + ")");

		// Build Classpath
		String classpath = System.getProperty("java.class.path");

		String separator = System.getProperty("file.separator");

		String path = System.getProperty("java.home") + separator + "bin"
				+ separator + "java";

		List<String> args = new ArrayList<String>();

		args.add(path);
		args.add("-cp");
		args.add(classpath);
		args.add(Main.class.getName());
		args.add(featureFile);
		args.add("--glue");
		args.add("com.nature.quickstep");

		for (String tag : cucumberTags) {
			args.add("--tags");
			args.add(tag);
		}

		args.add("--format");
		args.add("json:target/cucumber-report/report-" + processID + ".json");

		ProcessBuilder processBuilder = new ProcessBuilder(args);

		Process process;

		try {
			process = processBuilder.start();
			processMap.put(process, new Integer(processID));
			processes.add(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dumpProcessOutput(Process p) {
		System.out.println("\n\n===========================================");
		System.out.println("Process output stream dump for process with ID "
				+ processMap.get(p));
		System.out.println("===========================================");
		System.out.println(getProcessOutput(p));
		System.out.println("===========================================");
		System.out.println("Process error stream dump for process with ID "
				+ processMap.get(p));
		System.out.println("===========================================");
		System.out.println(getProcessErrors(p));
		System.out.println("===========================================\n\n");

	}

	public String getProcessOutput(Process p) {

		StringBuffer result = new StringBuffer();

		InputStream is = p.getInputStream();

		try {
			while (is.available() > 0) {
				result.append((char) is.read());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();

	}

	public String getProcessErrors(Process p) {

		StringBuffer result = new StringBuffer();

		InputStream is = p.getErrorStream();

		try {
			while (is.available() > 0) {
				result.append((char) is.read());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();

	}
}
