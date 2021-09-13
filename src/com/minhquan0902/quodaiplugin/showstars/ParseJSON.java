package com.minhquan0902.quodaiplugin.showstars;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ParseJSON {

    public static HashMap<String, Integer> putDataToHashMap(String responseBody, HashMap<String, Integer> dataHashMap){
        JSONArray albums = new JSONArray(responseBody);
        for (int i = 0; i < albums.length(); i ++){
            JSONObject album = albums.getJSONObject(i);
            String name = album.getString("name");
            String upperName = name.toUpperCase();
            int stars = album.getInt("stargazers_count");
            dataHashMap.put(upperName, stars);

        }
        return dataHashMap;

    }
    public static ArrayList<String> putDataToArrayList(String responseBody, ArrayList<String> dataList){
        JSONArray albums = new JSONArray(responseBody);
        for (int i = 0; i < albums.length(); i ++){
            JSONObject album = albums.getJSONObject(i);
            String name = album.getString("name");

            // Make all letters capitalized for easy searching
            String upperCaseName = name.toUpperCase();

            dataList.add(upperCaseName);

        }
        return dataList;
    }



}