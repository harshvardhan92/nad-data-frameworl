package com.rest.api.parser;


import java.util.Properties;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Parser {
	
	public final static Logger LOGGER = Logger.getLogger(Parser.class.getName());
	public static void main(String[] args) throws Exception {
		LOGGER.setLevel(Level.INFO);
		
		if (args.length < 1) {
			LOGGER.info("Insufficient arguments.\n");
			System.exit(1);
		}
		
		
    	 //String requestProperty = args[1];
    	 Properties props = new Properties();
    	 props.load(new FileInputStream(new File(args[0])));
    	 ApiResponse response = new ApiResponse();
    	 
    	
    	 String requestProperty;
    	 
    	 String apiUrl = props.getProperty("apiUrl");
    	 String path = props.getProperty("path");
    	 
    	 if(apiUrl.contains("getAllPortfolios"))
    	 {
    		String requestUrl = "https://ms-shared-nad.techmahindra.com/000000000032856-nad-rbac-microservice-staging/login";
    		String payload="{\"email\":\"harshvardhan@dataeaze.io\",\"password\":\"U2FsdGVkX19vqbrk1Qw/O5TEhN57IebAAqwTJXppCng=\"}";
    		 String requestPropertyKey = "Basic TE4wVDEyMDg5OkhHVDZoR3RHckRfMzI4NTY=";
    		
    		  requestProperty = response.sendPostRequest(requestUrl, payload,requestPropertyKey);
    		  
    	 }
    	
    	 else
    	 {
    	 requestProperty = props.getProperty("apiKey");
    	
    	 }
    	 String jsonPath = props.getProperty("path");
    	 
    	
    	

    		 
    		 
    		  // TODO Auto-generated catch block
    	 
    	String responseData =  response.sendGetRequest(apiUrl, requestProperty);
    	 
         Parser.saveResponse(responseData,path);
         
    }
	
	
	/*
	 * @Params responseData (json in string format to be saved)
	 * 
	 * @Params path (path of the file where data has to be stored)
	 *    
	 *    
	 */
	
	
public static void saveResponse(String responseData,String path) throws Exception {
	 
	 
	 
    
    // System.out.println(responseData);
	
	
     
     JSONParser parse = new JSONParser();
     
    //Type caste the parsed json data in json object
    JSONObject jobj = (JSONObject)parse.parse(responseData);
   //System.out.println(jobj);
   
    
   //System.out.print(company);
   
   
   
   try {
	    
	    FileWriter file = new FileWriter(path);
	    String json = jobj.toJSONString();
	    file.write(json);
	    file.close();
	    
	} catch (Exception e) {
	    LOGGER.info("exception occured " + e.getMessage());
	    System.exit(1);   
	   
	   
	}
   

   
	  
   
   
  
   /* this section saves file in hdfs
   
   String json = jobj.toJSONString();
   Configuration conf = new Configuration();
   FileSystem fs = null;
  
   Path opPath = new Path(path);
   BufferedWriter bw = null;
   FSDataOutputStream fsd = null;
   try {

     fs = opPath.getFileSystem(conf);
     //fsd = fs.create(opPath, true);
     bw = new BufferedWriter(new OutputStreamWriter(fs.create(opPath, true)));
     bw.write(json);
     bw.close();
   } catch (IOException e1) {
     LOGGER.info("exception occured " + e.getMessage());
   }
   */
   LOGGER.info("file saved successfully on " + path);
   



}

}
