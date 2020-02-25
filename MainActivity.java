package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView t1_temp,t2_city,t3_description,t4_humidity,t5_mintemp,t6_maxtemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1_temp = (TextView) findViewById(R.id.t1_temp);
        t2_city = (TextView) findViewById(R.id.t2_city);
        t3_description = (TextView) findViewById(R.id.t3_description);
        t4_humidity = (TextView) findViewById(R.id.t4_humidity);
        t5_mintemp = (TextView) findViewById(R.id.t5_mintemp);
        t6_maxtemp = (TextView) findViewById(R.id.t6_maxtemp);

        find_weather();
    }

        public void find_weather(){
            String url = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=88df865985689f8e3fb27c30fad1e1b5";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null , new Response.Listener< JSONObject >(){

                public void onResponse (JSONObject response){

                    try {
                        JSONObject main_object = response.getJSONObject("main");
                        JSONArray array = response.getJSONArray("weather");
                        JSONObject object = array.getJSONObject(0);
                        String temp = String.valueOf(main_object.getDouble("temp"));
                        String description = object.getString("description");
                        String city = response.getString("name");
                        String humidity = String.valueOf(main_object.getDouble("humidity"));
                        String minTemp = String.valueOf(main_object.getDouble("temp_min"));
                        String maxTemp = String.valueOf(main_object.getDouble("temp_max"));

                        //t1_temp.setText(temp);
                        t2_city.setText(city);
                        t3_description.setText(description);

                        double temp_int = Double.parseDouble(temp);
                        double centi = (temp_int - 32) / 1.8000;
                        centi = Math.round(centi);
                        int i = (int) centi;
                        t1_temp.setText(String.valueOf(i));

                        t4_humidity.setText(humidity);
                        //t5_mintemp.setText(minTemp);

                        double tempMin_int = Double.parseDouble(minTemp);
                        double centi1 = (tempMin_int - 32) / 1.8000;
                        centi1 = Math.round(centi1);
                        int j = (int) centi1;
                        t5_mintemp.setText(String.valueOf(j));

                        //t6_maxtemp.setText(maxTemp);

                        double tempMax_int = Double.parseDouble(maxTemp);
                        double centi2 = (tempMax_int - 32) / 1.8000;
                        centi2 = Math.round(centi2);
                        int k = (int) centi2;
                        t6_maxtemp.setText(String.valueOf(k));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);

        }

    }
