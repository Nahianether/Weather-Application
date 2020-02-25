package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Main2Activity extends AppCompatActivity{

//        Retrofit retrofit;
//        WeatherService service;
//        LocationHelper helper;
//
//        TextView dayOneTV,dayTwoTV,dayThreeTV,dayFourTV,dayFiveTV;
//        double longitude=0.0;
//        double latitude =0.0;
//        String units = "metric";
//
//
//        public Main2Activity() {
//
//        }
//
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View v= inflater.inflate(R.layout.activity_main2, container, false);
//            dayOneTV = v.findViewById(R.id.dayOne);
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BaseUrl.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            service = retrofit.create(WeatherService.class);
//
//            helper = (LocationHelper) getActivity();
//
//            latitude = helper.getLatitude();
//            longitude = helper.getLongitude();
//            units = helper.getUnit();
//
//            retrofitService();
//
//
//            return v;
//        }
//
//        private void retrofitService() {
//
//            String url = String.format("forecast?lat=%f&lon=%f&units=%s&appid=ac27c3ff05135d5e437f974d0e818186",latitude,longitude,units);
//
//            Call<ForecastWeatherResponse> responseCall = service.getForeCastWeather(url);
//
//            responseCall.enqueue(new Callback<ForecastWeatherResponse>() {
//                @Override
//                public void onResponse(Call<ForecastWeatherResponse> call, Response<ForecastWeatherResponse> response) {
//                    if (response.code()==200){
//                        ForecastWeatherResponse forecastWeatherResponse = response.body();
//
//                        List<ForecastWeatherResponse.ListData> list=forecastWeatherResponse.getList();
//
//
//                        dayOneTV.setText(
//
//                                "Date: "+
//                                        list.get(0).getDtTxt()+
//                                        "\nTemperature:"+
//                                        list.get(0).getMain().getTemp()
//                                        +"\n\nStatus:"+
//                                        list.get(0).getWeather().get(0).getMain()+
//                                        "\n\nMaximum Temperature:"+
//                                        list.get(0).getMain().getTempMax()+
//                                        "\n\nMinimum Temperature:"+
//                                        list.get(0).getMain().getTempMin()+
//                                        "\n\nPressure:"+
//                                        list.get(0).getMain().getPressure()+
//                                        "\n\nHumidity:"+
//                                        list.get(0).getMain().getHumidity()
//
//                        );
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ForecastWeatherResponse> call, Throwable t) {
//
//                    Log.e("Error", t.getMessage());
//                }
//            });
//
//
//
//        }
//
//    }

    ListView listView;
    String title [] = {};
    String description [] = {};
    String temp [] = {};
    TextView t1_temp,t2_city,t3_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1_temp = (TextView) findViewById(R.id.t1_temp);
        t2_city = (TextView) findViewById(R.id.t2_city);
        t3_description = (TextView) findViewById(R.id.t3_description);

        listView = findViewById(R.id.listview);
        //MyAdapter adapter=new MyAdapter(c: this,title, description, temp);
        //listView.setAdapter(adapter);

        find_weather();
    }

    class MyAdapter extends ArrayAdapter<String>{


        Context context;
        String title [];
        String description [];
        String temp [];



        MyAdapter(Context c, String title [],String description []) {
            super(c, R.layout.row, R.id.city,title);
            this.context = c;
            this.title = title;
            this.description = description;
            this.temp = temp;
        }

        public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent);
            TextView title = row.findViewById(R.id.city);
            TextView description = row.findViewById(R.id.description);
            TextView temp = row.findViewById(R.id.temp);



            //title.setText(title[position]);
            //description.setText(description[position]);
            //temp.setText(temp[position]);




            return row;

        }


    }

    public void find_weather(){
        String url = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=88df865985689f8e3fb27c30fad1e1b5";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null , new Response.Listener<JSONObject>(){

            public void onResponse (JSONObject response){

                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                    //t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32) / 1.8000;
                    centi = Math.round(centi);
                    int i = (int) centi;
                    t1_temp.setText(String.valueOf(i));

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
