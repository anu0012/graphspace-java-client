package com.graphspace.javalibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
 
public class ClientApi
{
	private String API_HOST = "http://www.graphspace.org/api/v1/graphs/";
	private String username;
	private String password;
	
    public ClientApi(String username, String password){
    	this.username = username;
    	this.password = password;
    }
    
    //Default constructor
    public ClientApi(){}
    
    // POST Method
    public String postGraph(String GraphName, int isPublic){
    	Graph g = new Graph();
        JSONObject data =new JSONObject();
        
        data.put("name",GraphName);
        data.put("is_public", isPublic);
        data.put("owner_email", username);
        data.put("graph_json", g.computeGraph());
        data.put("id", 12345);
        data.put("style_json",g.computeGraphStyle());
        
        String jsonData = data.toString();
       
        HttpPost httpPost = new HttpPost(API_HOST);
        String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader("AUTHORIZATION", authHeader);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        
        try{
            
        HttpResponse response=null;
        String line = "";
        StringBuffer result = new StringBuffer();
        httpPost.setEntity(new StringEntity(jsonData));
        HttpClient client = HttpClientBuilder.create().build();
        response = client.execute(httpPost);
        System.out.println("Post parameters : " + jsonData );
        System.out.println("Response Code : " +response.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while ((line = reader.readLine()) != null){ result.append(line); }
        System.out.println(result.toString());
        return result.toString();
        }
        catch(Exception e){
        	return null;
        }
        
       
    }
    
    //GET Method
    public String getGraph(String GraphName, String owner_email){
    	if(owner_email == null){
    		owner_email = username;
    	}
    	
    	HttpGet get = new HttpGet(API_HOST+"?owner_email="+owner_email);
        String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        get.setHeader("AUTHORIZATION", authHeader);
        get.setHeader("Content-Type", "application/json");
        get.setHeader("Accept", "application/json");
        
            
        HttpResponse response=null;
        String line = "";
        StringBuffer result = new StringBuffer();
        
        HttpClient client = HttpClientBuilder.create().build();
        try{
        response = client.execute(get);
        
        System.out.println("Response Code : " +response.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while ((line = reader.readLine()) != null){ result.append(line); }
        System.out.println(result.toString());
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        
        return result.toString();
    }
    
    // DELETE Method
    public String deleteGraph(String graphName){
    	//TODO use getGraph to get JSON of the graph and retrieve id from parsed JSON
    	String id="";
    	JSONObject jsonObject = (JSONObject) JSONValue.parse(getGraph(graphName,username));
    	long total = (Long) jsonObject.get("total");
    	if(total > 0)
    	{
    		JSONObject graph = (JSONObject)((JSONArray)jsonObject.get("graphs")).get(0);
    		if(graph.get("name").equals(graphName))
    		{
    			id = graph.get("id")+"";
    		}
    		else{
    			graph = (JSONObject)((JSONArray)jsonObject.get("graphs")).get((int)total-1);
    			id = graph.get("id")+"";
    		}
    	}
    	
    	System.out.println(id);
    	
    	HttpDelete httpDelete = new HttpDelete(API_HOST+id);
    	String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        httpDelete.setHeader("AUTHORIZATION", authHeader);
        httpDelete.setHeader("Content-Type", "application/json");
        httpDelete.setHeader("Accept", "application/json");
        
        HttpResponse response=null;
        String line = "";
        StringBuffer result = new StringBuffer();
        
        HttpClient client = HttpClientBuilder.create().build();
        try{
        response = client.execute(httpDelete);
        
        System.out.println("Response Code : " +response.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while ((line = reader.readLine()) != null){ result.append(line); }
        System.out.println(result.toString());
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return result.toString();
        
    }
    
}