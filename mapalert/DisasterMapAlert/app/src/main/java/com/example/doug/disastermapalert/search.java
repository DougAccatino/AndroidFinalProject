package com.example.doug.disastermapalert;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class search extends AppCompatActivity {

    private String TAG = search.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> alertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(search.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://weather.cit.api.here.com/weather/1.0/report.json?product=nws_alerts&name=new%20york&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    // Getting JSON Array node
                    JSONArray nwsAlerts = jsonObj
                                                .getJSONObject("nwsAlerts")
                                                .getJSONArray("watch");

                    Log.e(TAG, "nwsAlerts: " + nwsAlerts);


                    // looping through All Contacts
                    for (int i = 0; i < nwsAlerts.length(); i++) {
                        JSONObject alert = nwsAlerts.getJSONObject(i);
                        String severity = alert.getString("severity");
                        String longitude = alert.getString("longitude");
                        String latitude = alert.getString("latitude");

                        // tmp hash map for single contact
                        HashMap<String, String> newAlert = new HashMap<>();

//                         adding each child node to HashMap key => value
                        newAlert.put("severity", severity);
                        newAlert.put("longitude", longitude);
                        newAlert.put("latitude", latitude);


                        // adding contact to contact list
                        alertList.add(newAlert);
                    }
                    Log.e(TAG, "Alert List " + alertList);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }
                //if json contains nothing...do this
                //if json contains nothing, that means a weather alert
//                Intent i = new Intent(search.this, MapActivity.class);
//                startActivity(i);
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(search.this, alertList,
                    R.layout.list_item, new String[]{ "severity","latitude", "longitude"},
                    new int[]{R.id.severity, R.id.latitude,R.id.longitude});
            lv.setAdapter(adapter);
        }
    }
}