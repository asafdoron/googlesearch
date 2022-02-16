package com.forwrd.googlesearch.controllers;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;


@RestController
public class GoogleSearchController {
    
    @GetMapping("/search")
    public String getSearchResult(@RequestParam String query)
    {


        String stSearchUrl = "https://www.googleapis.com/customsearch/v1?key=AIzaSyDtI3zjzQPOATz6v68va9z_z6JVIIs05uI&cx=26fde055a2ccf8561&q=";

        stSearchUrl += query;

        String title = "";
        String stSearchResult = "";


        try {

            URL url = new URL(stSearchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
    
            if (conn.getResponseCode() != 200) {
                return ("Failed : HTTP error code : " + conn.getResponseCode());
            }
    
            Reader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");

            GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
            
		        //int total = results.getResponseData().getResults().size();
            int total = results.items.size();
		        System.out.println("total: "+total);
                  
            // limit to 10 results
            if(total > 10)
            {
              total = 10;
            }

            stSearchResult = "<ul>";

            // Show title  of each results
            for(int i=0; i<=total-1; i++)
            {
              Object obj = results.items.get(i);
                
              System.out.println(obj);

              LinkedTreeMap<Object,Object> t = (LinkedTreeMap) obj;
              title = t.get("title").toString();
                
                stSearchResult += "<li>";
                stSearchResult += title;
                stSearchResult += "</li>";

            }
            
            
            stSearchResult += "</ul>";

            conn.disconnect();
    
          } catch (MalformedURLException e) {
    
            e.printStackTrace();
    
          } catch (IOException e) {
    
            e.printStackTrace();
    
          }


          return stSearchResult;

    }

  
}
