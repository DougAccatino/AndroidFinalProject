package com.example.doug.disastermapalert;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class answer extends AppCompatActivity {
    ListView list;
    TextView t1;
    Button b1;
    ArrayList al;
    String url = "https://learncodeonline.in/api/android/datastructure.json";
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        t1=(TextView)findViewById(R.id.textV);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        b1=(Button)findViewById(R.id.back);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(answer.this,MapActivity.class);
                startActivity(i);
                finish();
            }
        });

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(answer.this);
        rQueue.add(request);

    }

    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray arr = object.getJSONArray("questions");
            int pos = getIntent().getIntExtra("index", 0);
            String s = arr.getJSONObject(pos).getString("Answer");
            t1.setText(s);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }
}