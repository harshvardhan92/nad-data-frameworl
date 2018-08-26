package com.rest.api.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.*;






public class ApiResponse {
	
	Parser parser = new Parser();
	
	/*This method is used to get call login api so that we can get authorization token for portflio api
	 * 
	 * @Param requestUrl
	 * @Param payload
	 * @Param requestProperty
	 * @return mainKey (authrization key)
	 */
	
	public  String sendPostRequest(String requestUrl, String payload, String requestProperty) throws JSONException 
	{
		Logger LOGGER = Parser.LOGGER;
		StringBuffer jsonString = new StringBuffer();
		try {
	        URL url = new URL(requestUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        connection.setRequestProperty("Authorization", requestProperty);
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	       // StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	        
	    } catch (Exception e) {
	            e.printStackTrace();
	           
	    }
		String apiResponse = jsonString.toString();
		
		JSONObject jsonObj = new JSONObject(apiResponse);
		
		// get the value of the key token_with_timestamp
		JSONObject jsonObj1 =  (JSONObject) jsonObj.get("result");
		String mainKey = "Basic " + (String) jsonObj1.get("token_with_timestamp");
		LOGGER.info("authrization key to call portfolio api " +mainKey);
		return mainKey;
	}
	
	
	
	/* Common method to get response from api using GET request method
	 * @Param apiUrl
	 * @Param requestProperty
	 *@return  responseData (return api Response)
	 */
	
	public String sendGetRequest(String apiUrl,String requestProperty) throws Exception {
	    
		Logger LOGGER = Parser.LOGGER;
	     URL obj = new URL(apiUrl);
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	     // optional default is GET
	     con.setRequestMethod("GET");
	     //add request header
	     con.setRequestProperty("Authorization", requestProperty);
	     
	     int responseCode = con.getResponseCode();
	     LOGGER.info("\nSending 'GET' request to URL : " + apiUrl);
	     LOGGER.info("Response Code : " + responseCode);
	     BufferedReader in = new BufferedReader(
	             new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	response.append(inputLine);
	     }
	     in.close();
	     //print in String
	     //System.out.println(response.toString());
	     String responseData = response.toString();
	     return responseData;
	     
	}
	
	
	
	
	
	
	
}