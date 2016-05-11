package com.nature.foxtrot.common;

public class Url {
	
	String hostChoice;

	public Url(String host){
		hostChoice = host;
	}

	public String createUrl(){
		String finalHost = host();
		return "http://" + finalHost;
	}

	private String host(){
		if(hostChoice != null){
			if(hostChoice.equalsIgnoreCase("test")){
				return "test-www.";
			} else if(hostChoice.equalsIgnoreCase("staging")){
				return "staging-www.";
			}
		} else {
			System.out.println("DEBUG: No environment was set for the Url so I am running against www-test.nature.com.  " +
					"Please ignore this if you are running in an IDE");
			return "www.";
		}
		return "";
	}

}
