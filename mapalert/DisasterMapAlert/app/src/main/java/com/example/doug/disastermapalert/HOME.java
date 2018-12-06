package com.example.doug.disastermapalert;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import static com.example.doug.disastermapalert.R.id.latitude;
import static com.example.doug.disastermapalert.R.id.longitude;
import static java.lang.Thread.*;
import static java.security.AccessController.getContext;

public class HOME extends AppCompatActivity {
    private String TAG = search.class.getSimpleName();
//    ListView lv = findViewById(R.id.list);
    ArrayList<HashMap<String, String>> alertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView image = (TextView)findViewById(R.id.textView4);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade);
//        animation1.setRepeatCount(Animation.INFINITE);
        image.startAnimation(animation1);


        alertList = new ArrayList<>();
//        lv = findViewById(R.id.list);

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.fab));

        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();


        //SubActionButton.Builder itemBuilder2 = new SubActionButton.Builder(this);
        int subActionButtonSize = 150;

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(HOME.this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(subActionButtonSize, subActionButtonSize);
        itemBuilder.setLayoutParams(params);


        //button 1
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.warning_float));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HOME.this, "SEVERE WEATHER DETECTED", Toast.LENGTH_SHORT).show();


                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent k = new Intent(HOME.this, MapActivity.class);
                            startActivity(k);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

            }
        });

        //button 2
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.home_float));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon).build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HOME.this, "Home", Toast.LENGTH_SHORT).show();
            }
        });

        //button 3
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.settings_float));
        SubActionButton button3 = itemBuilder.setContentView(itemIcon).build();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HOME.this, "Settings", Toast.LENGTH_SHORT).show();
                currentThread().interrupt();
                onStop();
                Intent i = new Intent(HOME.this, settings.class);
                startActivity(i);
            }
        });


        //button 4
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.phone_float));
        SubActionButton button4 = itemBuilder.setContentView(itemIcon).build();
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HOME.this, "Contacts", Toast.LENGTH_SHORT).show();
            }
        });


        //add everything now
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)


                // ...
                .attachTo(actionButton)
                .build();


    }

    //if you ever had a headache, try merging two projects not on the same repo :)

    @Override
    protected void onRestart()
    {
        // TODO Auto-generated method stub
        super.onRestart();

    }


    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();

        new HOME.GetContacts().execute();

    }

    public class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(HOME.this,"Gathering weather data",Toast.LENGTH_LONG).show();

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

//                Intent i = new Intent(HOME.this, MapActivity.class);
//                startActivity(i);
//                try {
//
//                    sleep(6000);
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            final Toast toast = Toast.makeText(getApplicationContext(),"SEVERE WEATHER DETECTED",Toast.LENGTH_LONG);
//                            toast.show();
//                        }
//                    });
//                    sleep(6000);
//                    Intent k = new Intent(HOME.this, MapActivity.class);
//                    startActivity(k);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

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
            if(getSearchLatitude() == getLatitude() && getSearchLongitude() == getLongitude() && getSeverity() > 60){
                //idk something
                Intent i = new Intent(HOME.this, MapActivity.class);
                startActivity(i);
            }

            return null;

        }

        public double getSearchLatitude(){
            Integer result = latitude;
            Log.e(TAG,"searchlatitude="+result);
            return result;

        }

        public double getSearchLongitude(){
            Integer result = longitude;
            Log.e(TAG,"searchlongitude="+result);
            return result;

        }

        public double getSeverity(){
            Integer result = Integer.valueOf(R.id.severity);
            Log.e(TAG,"severity="+result);
            return result;
        }

        public double getLatitude(){
            Log.e(TAG,"latitude="+MapActivity.latitude);
            return MapActivity.latitude;
        }

        public double getLongitude(){
            Log.e(TAG,"longitude="+MapActivity.longitude);
            return MapActivity.longitude;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);



//            ListAdapter adapter = new SimpleAdapter(HOME.this, alertList,
//                    R.layout.list_item, new String[]{ "severity","latitude", "longitude"},
//                    new int[]{R.id.severity, latitude, longitude});
//            lv.setAdapter(adapter);


//            if(getSearchLongitude() == getLongitude()){
//                //yea i still dont know do something
//                //i didnt think id get this far
//
//            }

        }
    }
}
