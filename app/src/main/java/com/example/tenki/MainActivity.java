package com.example.tenki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "88a2cd7ecb71b61003a92fe2f4553eda";
    private final String URL = "https://api.openweathermap.org/data/2.5/weather?q=Jakarta&appid="+API_KEY;
    DecimalFormat df = new DecimalFormat("##.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonWeatherObject = jsonArray.getJSONObject(0);
                    String description = jsonWeatherObject.getString("description");

                    JSONObject jsonMainObject  = jsonObject.getJSONObject("main");
                    double temp = jsonMainObject.getDouble("temp") - 273.15;

                    Toast.makeText(MainActivity.this,"Jakarta Weather: "+description+" "+df.format(temp) + "C",Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Something went wrong with the API" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}