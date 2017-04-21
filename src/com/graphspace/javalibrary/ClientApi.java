package com.graphspace.javalibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
 
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
    public String PostGraph(String GraphName, int isPublic){
    	Graph g = new Graph();
        JSONObject data =new JSONObject();
        
        data.put("name",GraphName);
        data.put("is_public", isPublic);
        data.put("owner_email", username);
        data.put("graph_json", g.computeGraph());
        data.put("style_json",g.computeGraphStyle());
        
        String jsonData = data.toString();
        ClientApi httpPostReq=new ClientApi();
        HttpPost httpPost=httpPostReq.createConnectivity(API_HOST , username, password);
        return httpPostReq.executeReq( jsonData, httpPost);
    }
    
     
    HttpPost createConnectivity(String baseURL, String username, String password)
    {
        HttpPost post = new HttpPost(baseURL);
        String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        post.setHeader("AUTHORIZATION", authHeader);
        post.setHeader("Content-Type", "application/json");
            post.setHeader("Accept", "application/json");
            
        return post;
    }
     
    String executeReq(String jsonData, HttpPost httpPost)
    {
        try{
            return executeHttpRequest(jsonData, httpPost);
        }
        catch (UnsupportedEncodingException e){
            System.out.println("error while encoding api url : "+e);
            return "error while encoding api url : "+e;
        }
        catch (IOException e){
            System.out.println("ioException occured while sending http request : "+e);
            return "ioException occured while sending http request : "+e;
        }
        catch(Exception e){
            System.out.println("exception occured while sending http request : "+e);
            return "exception occured while sending http request : "+e;
        }
        finally{
            httpPost.releaseConnection();
        }
    }
     
    String executeHttpRequest(String jsonData,  HttpPost httpPost)  throws UnsupportedEncodingException, IOException
    {
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
}