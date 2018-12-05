package com.example.doug.disastermapalert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

public class search extends AppCompatActivity {

    // Will show the string "data" that holds the results
    TextView results;
    // URL of object to be parsed
    String JsonURL = "https://weather.cit.api.here.com/weather/1.0/report.json?product=nws_alerts&name=new%20york&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg";    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;


    private static String TAG = "MY_TAG";
    private TextView tw_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw_weather = (TextView) findViewById(R.id.jsonData);

        /*A*/	JSONWeatherTask wt = new JSONWeatherTask( (LocationManager)getSystemService(Context.LOCATION_SERVICE) );
        //B		JSONWeatherTask wt = new JSONWeatherTask("Florence");//if i want to access a specific city
        //C		JSONWeatherTask wt = new JSONWeatherTask( 41.9d, 12.5d );//if i want to access a coordinates
        /*D*/	wt.setLang( Locale.getDefault().getLanguage() );		//language setup
        /*E*/		wt.register( this );	//register the observer
        wt.execute();			//run the async task

    }

    /**
     * Method used to "observe" event from every source
     */
    @Override
    public void update(Observable observable, Object data) {
        Log.d(TAG, "data received: "+data);
        if(data instanceof Weather) {
            tw_weather.setText((Weather) data );
        }
    }

}