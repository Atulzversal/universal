package hello.world;
import java.util.*;


import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import static spark.Spark.*;

public class HelloWorld {
	
	static Map<Integer, String> dataStore = new HashMap<>();
    public static void main(String[] args) {
        
        // Create (POST)
        post("/create", (req, res) -> {
        	String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();

            // Extract name and age
            String name="";
            int id=0;
            
            try {
            name = jsonObject.get("name").getAsString();
            id = jsonObject.get("id").getAsInt();
            dataStore.put(id, name);
            }catch(Exception e) {
            	return "Please enter some data first";
            }

            return "Received JSON data. Name: " + name + ", Age: " + id;	
        	
        });
        
        
        get("/read", (req, res) -> {
        	JsonObject jsonObject=new JsonObject();
        	
        	 for (Integer key : dataStore.keySet()) {
        		 String s=Integer.toString(key);
        	      jsonObject.addProperty(s,dataStore.get(key));
        	    }
        	 
        	 return jsonObject;

        });
        
        
        
        put("/update", (req, res) -> {
        	String message="Updated Successfully";
        	JsonObject result=new JsonObject();
        	String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            
            String updatedname = jsonObject.get("name").getAsString();
            int id = Integer.parseInt(req.queryParams("id"));
            if(dataStore.containsKey(id)) {
            	dataStore.put(id, updatedname);
            	result.addProperty(message, true);
            }else {
            	result.addProperty("Data is not available first add the data ", false);
            }
            
            
            
            
         
            
            return result;

        });
        
        
        delete("/delete", (req, res) -> {
        	String message="Deleted Successfully";
        	JsonObject result=new JsonObject();
          int id = Integer.parseInt(req.queryParams("id"));
          if(dataStore.containsKey(id)) {
        	  dataStore.remove(id);
        	  result.addProperty(message, true);
          }else {
        	  result.addProperty("Data is not available", false);
          }
          
           return result;

        });
        
        
        
        
        
       
        
        
    
    	 
  
    	  
    	 
       
    } 
}
