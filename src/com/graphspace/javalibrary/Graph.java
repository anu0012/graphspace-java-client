package com.graphspace.javalibrary;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Graph {
	
	public JSONObject computeGraph(){
		
        JSONObject graphJsonObject = new JSONObject();
        
        JSONObject elementsJsonObject = new JSONObject();
        // nodes array
        JSONArray nodesArray = new JSONArray();
        JSONObject nodeElement = new JSONObject();
        JSONObject positionObject = new JSONObject();
        positionObject.put("y",277.5);
        positionObject.put("x",297.5);
        
        nodeElement.put("position", positionObject);
        
        JSONObject dataObject = new JSONObject();
        dataObject.put("k",0);
        dataObject.put("id","P4314611");
        dataObject.put("name","P4314611");
        dataObject.put("label","Updated Node A");
        
        nodeElement.put("data", dataObject);
        
        nodesArray.add(nodeElement);
        
        //edges array
        
        JSONArray edgesArray = new JSONArray();
//        JSONObject edgeElement = new JSONObject();
//        JSONObject dataObjectEdge = new JSONObject();
//        dataObjectEdge.put("target",0);
//        dataObjectEdge.put("k",0);
//        dataObjectEdge.put("id","P4314611");
//        dataObjectEdge.put("name","P4314611");
//        dataObjectEdge.put("label","Node A");
//        
//        JSONObject styleObject = new JSONObject();
//        styleObject.put("y",277.5);
//        styleObject.put("x",297.5);
//        
//        edgeElement.put("style", styleObject);
//        
//        
//        
//        edgeElement.put("data", dataObjectEdge);
//        
//        edgesArray.add(edgeElement);
        
        
        elementsJsonObject.put("nodes", nodesArray);
        elementsJsonObject.put("edges", edgesArray);
        
        JSONObject graphData = new JSONObject();
        graphData.put("name", "my-first-graph");
        graphData.put("description", "sample graph with one node");
        graphData.put("tags",new JSONArray());
        
        
        graphJsonObject.put("elements", elementsJsonObject);
        graphJsonObject.put("data", graphData);
        
        return graphJsonObject;
	}
	
	public JSONObject computeGraphStyle(){
		JSONObject styleJsonObject = new JSONObject();
		return styleJsonObject;
	}
		
}
