package com.demo.dowloadjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadJSONTask task = new DownloadJSONTask();
//        task.execute("https://api.openweathermap.org/data/2.5/weather?q=%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0&appid=d2b45135f0d4999b29b42882c0e4bcb6");
        task.execute("https://api.openweathermap.org/data/2.5/weather?q=Краснодар&appid=d2b45135f0d4999b29b42882c0e4bcb6");
    }

    private static class DownloadJSONTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String line = reader.readLine();
                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
                return result.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
//            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("MyResult", s);

            try {
                JSONObject jsonObject = new JSONObject(s);

//                String name = jsonObject.getString("name");
//                Log.i("MyResult", name);

//                JSONObject main = jsonObject.getJSONObject("main");
//                String temp = main.getString("temp");
//                String pressure = main.getString("pressure");
//                Log.i("MyResult", temp + " " + pressure);

                JSONArray jsonArray = jsonObject.getJSONArray("weather");
                JSONObject weather = jsonArray.getJSONObject(0);
                String main = weather.getString("main");
                String description = weather.getString("description");
                Log.i("MyResult", main + " " + description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}