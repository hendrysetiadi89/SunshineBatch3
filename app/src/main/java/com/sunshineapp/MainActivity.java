package com.sunshineapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunshineapp.weathernow.WeatherNow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    class AmbilCuacaSekarangTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection conn= null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather" +
                        "?q=jakarta&units=metric&appid=bae2e88a50774eff50692ce8af26c060");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream is = conn.getInputStream();
                StringBuilder hasilStringBuilder = new StringBuilder();

                BufferedReader bReader =
                        new BufferedReader(
                                new InputStreamReader(is));
                String strLine;
                /* Reading the contents of the file , line by line */
                while ((strLine = bReader.readLine()) != null) {
                    hasilStringBuilder.append(strLine);
                }
                String jsonString = hasilStringBuilder.toString();
                WeatherNow weatherNow = new Gson().fromJson(jsonString, WeatherNow.class);
                String description = weatherNow.getWeather().get(0).getDescription();
                return description;

//                JSONObject jsonMainObject = jsonObject.getJSONObject("main");
//                String temperature = jsonMainObject.getString("temp");
//                return temperature;
            }
            catch (Exception e) {
                return null;
            }
            finally {
                if (null!= conn) conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Test", s);
            textView.setText(s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.text);

        // jalankan asynctask di background
        new AmbilCuacaSekarangTask().execute();

        // code
        //code
    }
}
