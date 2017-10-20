package com.example.x550vx_dm066t.myapplication.controller.news_controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.x550vx_dm066t.myapplication.MainActivity;
import com.example.x550vx_dm066t.myapplication.controller.DB_controller.news_DB_controller;
import com.example.x550vx_dm066t.myapplication.interfaces.OnTaskCompleted;
import com.example.x550vx_dm066t.myapplication.property.story;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by x550vx-dm066t on 19/10/2017.
 */

public class NormalAsyncRequest extends AsyncTask<String, Integer, Boolean> {
    private String url;
    private OnTaskCompleted listener;
    private ProgressDialog dialog;
    private news_DB_controller con;
    private String type;

    public NormalAsyncRequest(String _url, OnTaskCompleted _listener, ProgressDialog _dialog,news_DB_controller _con, String _type){
        url = _url;
        listener = _listener;
        dialog = _dialog;
        con = _con;
        type = _type;
    }

    @Override
    protected void onPreExecute() {
        if(!dialog.isShowing())
            dialog.show();
        super.onPreExecute();
    }

    protected Boolean doInBackground(String... urls) {
        try {
                URL tmp_url = callGetMethod(url);
                String tmp_msg = readResult(tmp_url);
            if(type.equals("story")){
                story news = getStory(tmp_msg);
                con.addStory(news);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private URL callGetMethod(String urls){
        try{
            HttpURLConnection urlConnection = null;
            URL url = new URL(urls);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(Boolean success) {
        MainActivity.count++;
        if(MainActivity.count>=15) {
            listener.onTaskCompleted();
            dialog.dismiss();
        }
    }

    private String readResult(URL url){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            String s = sb.toString();
            return s;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private story getStory(String json){
        try{
            story news = new story();
            JSONObject jo = new JSONObject(json);
            String kids = "";
            if(jo.has("kids")){
                JSONArray arry = jo.getJSONArray("kids");
                for(int i = 0;i<arry.length();i++){
                    if(i == 0)
                        kids = arry.getString(i);
                    else
                        kids = kids+","+arry.getString(i);
                }
            }
            news.setID(jo.getString("id"));
            news.seTitle(jo.getString("title"));
            news.setType(jo.getString("type"));
            news.setKids(kids);
            news.setBy(jo.getString("by"));
            news.set_score(jo.getString("score"));
            return news;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
