package com.iotdevices.registry.util;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotdevices.registry.service.DataPoints;

@Component
public class StringUtil {

	
public String createJsonMsgFromTelemetric(String telemetric)
{
	try {
		HashMap<String,String> units = new HashMap<String,String>();
		units.put("centigrade", "10° C");
		units.put("longitude", "40° N");
		units.put("altitude", "100 feet");
		units.put("fahrenheit", "70° F");
		units.put("longitude", " 75° W");
		units.put("pressure", "36.5 psi");
		units.put("humidity", "70%");
		units.put("battery", "90%");
		units.put("wifi", "00:25:96:12:34:56");
		units.put("percentage", "60 %");
		units.put("meters","100 meters");
		units.put("feet","100 feet");
		units.put("celsius","10° C");
		units.put("fahrenheit","40° F");
		units.put("psi","36.5 psi");
		units.put("%" ,"80%");
		units.put("wi","00:25:96:12:34:56");
		
		
		
		
		
		
		
	  String json = telemetric;	
	  ObjectMapper objectMapper = new ObjectMapper();
	  List<HashMap> list = objectMapper.readValue(json, List.class);
	  JSONObject obj = new JSONObject();
	  for(HashMap mp:list)
	  {
		  String unit = (String)mp.get("unit");
		  unit = unit.toLowerCase();
		  String value = units.get(unit);
		  String name = (String) mp.get("name");
		  obj.put(name,value );
		 
	  }
	  return obj.toString();
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.toString());
		return "";
	}
	
}
	
public String createJsonMsgFromTelemetricWrong(String telemetric)
{
	try {
		HashMap<String,String> units = new HashMap<String,String>();
		units.put("Centigrade", "10° C");
		units.put("longitude", "40° N");
		units.put("altitude", "100 feet");
		units.put("Fahrenheit", "70° F");
		units.put("longitude", " 75° W");
		units.put("pressure", "36.5 psi");
		units.put("Humidity", "70%");
		units.put("battery", "90%");
		units.put("wifi", "00:25:96:12:34:56");
		
	  String json = telemetric;	
	  ObjectMapper objectMapper = new ObjectMapper();
	  List<HashMap> list = objectMapper.readValue(json, List.class);
	  JSONObject obj = new JSONObject();
	  for(HashMap mp:list)
	  {
		  String value = units.get(mp.get("unit"));
		  obj.put((String) mp.get("name")+"er",value );
		
		 
	  }
	  return obj.toString();
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.toString());
		return "";
	}
	
}	
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		StringUtil su = new StringUtil();
		System.out.println(su.createJsonMsgFromTelemetric(""));
	}

}
