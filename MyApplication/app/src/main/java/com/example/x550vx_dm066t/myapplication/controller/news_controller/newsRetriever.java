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
 * Created by x550vx-dm066t on 15/10/2017.
 */

public class newsRetriever extends AsyncTask<String, Integer, Boolean> {
    private String msg;
    private Context context;
    private OnTaskCompleted listener;
    private ProgressDialog dialog;
    private   news_DB_controller con;
    private JSONArray array;
    private int current;
    private HttpURLConnection urlConnection;

    public newsRetriever(Context ctx, OnTaskCompleted _listener, ProgressDialog _dialog, int _pages){
        context = ctx;
        listener = _listener;
        dialog = _dialog;
        current = _pages;
    }

    @Override
    protected void onPreExecute() {
        if(!dialog.isShowing())
        dialog.show();
        con = new news_DB_controller(context, "NEWS_LIST");
        con.deleteStory();
        MainActivity.count = 0;
        super.onPreExecute();
    }

    protected Boolean doInBackground(String... urls) {
        try {
            URL url = callGetMethod(urls[0]);
            msg = readResult(url);
            array = new JSONArray(msg);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    protected void onPostExecute(Boolean success) {
        try{
            MainActivity.max_pages =  array.length()/MainActivity.pages;
        for(int i = ((current-1)*MainActivity.pages);i<current*MainActivity.pages;i++){
            String s ="https://hacker-news.firebaseio.com/v0/item/"+array.getString(i)+".json";
            NormalAsyncRequest nor = new NormalAsyncRequest(s,listener,dialog,con,"story");
            nor.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    private URL callGetMethod(String urls){
        try {
            urlConnection = null;
            URL url = new URL(urls);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            return news;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
