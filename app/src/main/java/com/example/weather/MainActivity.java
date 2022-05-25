package com.example.weather;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private LocationManager locationManager;
    StringBuilder sbGPS = new StringBuilder();
    FileInputStream fin = null;
    String[][] myArray = {{"50","50"}, {"54", "55"}, {"61", "70"}, {"55", "60"},  {"70", "60"}, {"62", "60"}};
    private Button button;
    private TextView textView;
    private TextView country;
    private TextView humidity;
    private TextView pressure;
    private ImageView imageView;
    private Button right;
    private Button left;
    private int pos = -1;
    private String lat;
    private String lon;
    private boolean check = false;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String url = "";
        try {
            Bundle arguments = getIntent().getExtras();
            String name = arguments.get("hello").toString();
            System.out.println(name + "АААААААААААААААААААААААААААААААААААААААААААААААА");
            url = "https://api.openweathermap.org/data/2.5/weather?q=" + name + "&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";
            check = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        button = findViewById(R.id.button);
        right = findViewById(R.id.button3);
        left = findViewById(R.id.button4);

        textView = findViewById(R.id.textView);
        country = findViewById(R.id.textView2);
        humidity = findViewById(R.id.textView3);
        pressure = findViewById(R.id.textView4);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.start);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, locationListener);

        String messageText = showLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        System.out.println(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude());
        System.out.println(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());
        if (check == false){
            url = "https://api.openweathermap.org/data/2.5/weather?lat="+locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude()+"&lon=" + locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude() + "&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";
        }

        String url1 = "https://api.openweathermap.org/data/2.5/weather?lat=50&lon=-132&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";
        new GetUrlData().execute(url);
        // кнопка бесполезная
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.openweathermap.org/data/2.5/weather?lat="+locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude()+"&lon=" + locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude() + "&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";
                String url1 = "https://api.openweathermap.org/data/2.5/weather?lat=50&lon=-132&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";
                new GetUrlData().execute(url);


            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url1 = "";
                pos = pos - 1;
                System.out.println(pos);

                if (pos < 0){
                    pos = -1;
                    url1 = "https://api.openweathermap.org/data/2.5/weather?lat="+locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude()+"&lon=" + locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude() + "&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";

                    System.out.println(url1);
                    new GetUrlData().execute(url1);
                }
                else{
                    url1 = "https://api.openweathermap.org/data/2.5/weather?lat="+myArray[pos][0]+"&lon=" + myArray[pos][1] + "&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";
                    System.out.println(url1);
                    new GetUrlData().execute(url1);

                }



            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url1 = "";
                pos = pos + 1;
                System.out.println(pos);
                if (pos >= myArray.length){
                    pos = myArray.length-1;
                    url1 = "https://api.openweathermap.org/data/2.5/weather?lat="+myArray[pos][0]+"&lon=" + myArray[pos][1] + "&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";
                    System.out.println(url1);

                }
                else{
                    url1 = "https://api.openweathermap.org/data/2.5/weather?lat="+myArray[pos][0]+"&lon=" + myArray[pos][1] + "&appid=508dabe2e8078280e8b3e4198e0c0068&lang=ru&units=metric";
                    System.out.println(url1);
                    new GetUrlData().execute(url1);
                }



            }
        });





    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };


    private class GetUrlData extends AsyncTask<String, String, String>{
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line).append("\n");
                }
                System.out.println(buffer);
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject obj = new JSONObject(result);
                textView.setText("ТЕМПЕРАТУРА " + obj.getJSONObject("main").getDouble("temp"));
                country.setText(obj.getString("name"));
                humidity.setText("Влажность воздуха " + obj.getJSONObject("main").getDouble("humidity"));
                pressure.setText("Давление атм: " + obj.getJSONObject("main").getDouble("pressure"));
                JSONArray obj1 = obj.getJSONArray("weather");
                String obj2 = obj1.getJSONObject(0).getString("icon");
                if (obj2.equals("01d")){
                    imageView.setImageResource(R.drawable.a01d);
                }
                if (obj2.equals("01n")){
                    imageView.setImageResource(R.drawable.a01n);
                }
                if (obj2.equals("02d")){
                    imageView.setImageResource(R.drawable.a02d);
                }
                if (obj2.equals("02n")){
                    imageView.setImageResource(R.drawable.a02n);
                }
                if (obj2.equals("03d")){
                    imageView.setImageResource(R.drawable.a03d);
                }
                if (obj2.equals("03n")){
                    imageView.setImageResource(R.drawable.a03n);
                }
                if (obj2.equals("04d")){
                    imageView.setImageResource(R.drawable.a04d);
                }
                if (obj2.equals("04n")){
                    imageView.setImageResource(R.drawable.a04n);
                }
                if (obj2.equals("09d")){
                    imageView.setImageResource(R.drawable.a09d);
                }
                if (obj2.equals("09n")){
                    imageView.setImageResource(R.drawable.a09n);
                }
                if (obj2.equals("10d")){
                    imageView.setImageResource(R.drawable.a10d);
                }
                if (obj2.equals("10n")){
                    imageView.setImageResource(R.drawable.a10n);
                }
                if (obj2.equals("11d")){
                    imageView.setImageResource(R.drawable.a10d);
                }
                if (obj2.equals("10n")){
                    imageView.setImageResource(R.drawable.a10n);
                }
                if (obj2.equals("11d")){
                    imageView.setImageResource(R.drawable.a11d);
                }
                if (obj2.equals("11n")){
                    imageView.setImageResource(R.drawable.a11n);
                }
                if (obj2.equals("13d")){
                    imageView.setImageResource(R.drawable.a13d);
                }
                if (obj2.equals("13n")){
                    imageView.setImageResource(R.drawable.a13n);
                }
                if (obj2.equals("50d")){
                    imageView.setImageResource(R.drawable.a50d);
                }
                if (obj2.equals("50n")){
                    imageView.setImageResource(R.drawable.a50n);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    private String showLocation(Location location) {
        return String.format(
                "Широта: %1$.4f\nДолгота: %2$.4f\nВремя: %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    public void onCountry(View view) {
        Intent intent = new Intent(this, CountryActivity.class);
        startActivity(intent);

    }

}