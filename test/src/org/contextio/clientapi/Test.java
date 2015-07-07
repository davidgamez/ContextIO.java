package org.contextio.clientapi;

import java.util.HashMap;
import java.util.Map;

import org.contextio.clientapi.ContextIO;

/**
 * 
 * @author Thomas Taschauer | tomtasche.at
 *
 */
public class Test {

	public static void main(String[] args) {
		ContextIO dokdok = new ContextIO("dmerrmli", "2ecw2YSjc7Gi7d3B");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("since", "0");
		
		System.out.println(dokdok.allMessages("tomtasche@gmail.com", params).rawResponse.getBody());
		
	}
}
