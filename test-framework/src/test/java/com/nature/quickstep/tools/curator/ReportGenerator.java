package com.nature.quickstep.tools.curator;

import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.w3c.tidy.Tidy;

import com.google.gson.Gson;
import com.nature.quickstep.CucumberRunner;

import cucumber.api.cli.Main;

public class ReportGenerator {

	Feature[] features = null;
	
	StringBuffer reportBuffer = new StringBuffer();

	SortedMap<String, Step> stepsMap = new TreeMap<String, Step>();

	String jsonPath = "target/dry-report.json";
	String dslReportPath = "target/dsllookup.html";
	String scenariosReportPath = "target/scenarioslookup.html";

	public static void main(String[] args) {
		new ReportGenerator();
	}

	public ReportGenerator() {

		// Since Cucumber calls System.exit when it's done, the report needs to
		// be generated in a shutdown hook.
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				parseCucumberJSON();
				generateStepsReport();
				generateScenariosReport();
			}
		});

		executeDryRun();

	}

	public void generateStepsReport() {

		reportBuffer = new StringBuffer();
		
		generateJavascriptFunctionsForStepsReport();
		generateReportBodyForStepsReport();

		saveReport(dslReportPath);

	}
	
	public void generateScenariosReport() {
		
		reportBuffer = new StringBuffer();
		
		generateJavascriptFunctionsForScenariosReport();
		generateReportBodyForScenariosReport();

		saveReport(scenariosReportPath);
		
	}
	
	public void generateJavascriptFunctionsForScenariosReport() {
		
		report("<script type=\"text/javaScript\">");
		report("var allFeatures = new Array();");

		int featureNumber = 0;

		
		for (Feature f : features) {
			
			report("allFeatures[" + featureNumber + "] = new Array();"); 
			report("allFeatures[" + featureNumber + "][0] = \"Feature: " + f.getName() + "\\n\\n" + "\";");
			
			int scenarioNumber = 1;
			
			for (Element e : f.elements) {
				
				StringBuffer fText = new StringBuffer();
				
				if (e.getTags() != null && e.getTags().length > 0) {
					
					String tagLine = "";
					for (Tag tag : e.getTags()) {
						tagLine += tag.getName();
						tagLine += " ";
					}
					
					fText.append(tagLine);
					fText.append("\\n");
					
				}
				
				fText.append(e.getKeyword() + ": " + e.getName() + "\\n");
				for (Step step : e.getSteps()) {
					fText.append(step.getKeyword() + escape(step.getName()) + "\\n");
				}
				
				fText.append("\\n");
				
				report("allFeatures[" + featureNumber + "]["+ scenarioNumber + "] = \"" + escape(fText.toString()) + "\";");
				
				scenarioNumber++;
			
			}
			
			featureNumber++;
			
		}
		

		report("function populateScenariosTextArea() {");
		report("var filterText= document.getElementById('txtFilter').value;");
		report("var scenariosTextArea=document.getElementById('txtScenarios');");
		
		
		report("scenariosTextArea.value='';");
		
		report("for ( var i = 0; i < allFeatures.length; i++) {");
		report("var s = \"\"");
		
		report("for (var j = 1; j < allFeatures[i].length; j++) {");
		report("if (allFeatures[i][j].toLowerCase().indexOf(filterText.toLowerCase()) != -1) { s = s + allFeatures[i][j];}");
		report("}");
		
		report("if (s.trim() != '') {s = scenariosTextArea.value + allFeatures[i][0] + s; scenariosTextArea.value = s; scenariosTextArea.value=s;} ");
		
		report("}");
		report("}");

		report("</script>");
		
	}
	
	public void generateReportBodyForScenariosReport() {
		
		report("<html>");
		report("<head><title>Foxtrot Scenarios Lookup</title></head>");
		report("<body onload=\"populateScenariosTextArea()\">");
		report("<H1>Foxtrot Scenarios Lookup</H1>");
		report("<table>");
		report("<tr><td><B>Filter:</B></td><td><input type=\"text\" id=\"txtFilter\" autofocus=\"true\" onkeyup='populateScenariosTextArea()' style=\"display:table-cell; width:100%\"/></td></tr>");
		report("<tr><td valign = \"top\"><B>Scenarios:</B></td>");
		report("<td><textarea rows=\"40\" cols=\"120\" readonly=\"true\" id=\"txtScenarios\">");
		report("</textarea>");
		report("</td></tr>");
		report("</body>");
		report("</html>");
		
	}
	
	public String escape(String str) {
		return StringUtils.replaceEach(str, new String[]{"&", "\"", "<", ">"}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"});
	}
	
	public void report(String s) {
		reportBuffer.append(s);
		reportBuffer.append('\n');
	}
	
	public void report(String s, boolean escape) {
		
		if (escape) {
			report(escape(s));
		} else {
			report(s);
		}
		
	}
	
	public void saveReport(String filename) {
		try {
			
			//Prepare html beautifier 
			Tidy tidy = new Tidy();
			//tidy.setIndentContent(true);
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			
			//Write to file
			FileWriter writer = new FileWriter(filename);
			//tidy.parse(new StringReader(reportBuffer.toString()), writer);
			writer.write(reportBuffer.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateJavascriptFunctionsForStepsReport() {

		report("<script type=\"text/javaScript\">");
		report("var allSteps = new Array();");

		Iterator<Step> iterator = stepsMap.values().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			String curStep = iterator.next().getName();
			report("allSteps[" + i + "] = \"" + curStep + "\";");
			i++;
		}

		report("function populateStepsList() {");
		report("var lstSteps=document.getElementById('lstSteps');");
		report("var filterText= document.getElementById('txtFilter').value;");

		report("while (lstSteps.length > 0) { lstSteps.remove(0); }");

		report("for ( var i = 0; i < allSteps.length; i++) {");
		report("if (filterText.trim() == '' || allSteps[i].toLowerCase().indexOf(filterText.toLowerCase()) != -1) {");
		report("step = document.createElement('option');");
		report("step.text = allSteps[i];");
		report("lstSteps.add(step,null);");
		report("}");
		report("}");

		report("}");

		report("</script>");

	}

	public void generateReportBodyForStepsReport() {
		report("<html>");
		report("<head><title>Foxtrot DSL Lookup</title></head>");
		report("<body onload='populateStepsList()'>");
		report("<H1>Foxtrot DSL Lookup</H1>");
		report("<p>Your DSL currently contains ");
		report("<script type=\"text/javaScript\">document.write(allSteps.length);</script>");
		report(" steps.</p><BR/>");
		report("<table>");
		report("<tr><td><B>Filter:</B></td><td><input type=\"text\" autofocus=\"true\" id=\"txtFilter\" onkeyup='populateStepsList()' style=\"display:table-cell; width:100%\"/></td></tr>");
		report("<tr><td valign = \"top\"><B>Steps:</B></td>");
		report("<td><select id=\"lstSteps\" size=\"20\">");
		report("</select>");
		report("</td></tr>");
		report("</body>");
		report("</html>");
	}

	public void executeDryRun() {

		List<String> args = new ArrayList<String>();
		args.add(CucumberRunner.featuresLocation);
		args.add("-m");
		args.add("-d");
		args.add("--glue");
		args.add("com.nature.quickstep");
		args.add("--format");
		args.add("json:" + jsonPath);

		try {
			Main.main(args.toArray(new String[0]));
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	public void parseCucumberJSON() {

		features = null;
		Gson gson = new Gson();

		// Read file
		try {
			FileReader reader = new FileReader(jsonPath);

			features = gson.fromJson(reader, Feature[].class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Feature f : features) {
			for (Element element : f.elements) {
				for (Step step : element.getSteps()) {
					stepsMap.put(step.getName(), step);
				}
			}
		}
	}

}
