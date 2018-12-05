//package com.example.doug.disastermapalert;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//    ListView list;
//    ImageView i1;
//    String url = "https://learncodeonline.in/api/android/datastructure.json";
//    ProgressDialog dialog;
//    ArrayList al = new ArrayList();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        list = (ListView) findViewById(R.id.list);
//        i1=(ImageView)findViewById(R.id.lco_ad);
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Loading....");
//        dialog.show();
//
//        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String string) {
//                parseJsonData(string);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
//        rQueue.add(request);
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i , long l) {
//                Intent intent = new Intent(getApplicationContext(),answer.class);
//                intent.putExtra("index",i);
//                //Toast.makeText(MainActivity.this, "index "+i , Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//                finish();
//
//            }
//        });
//        i1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), lco_ad.class);
//                intent.putExtra("url", "https://courses.learncodeonline.in/");
//                startActivity(intent);
//            }
//        });
//    }
//
//    void parseJsonData(String jsonString) {
//        try {
//            JSONObject object = new JSONObject(jsonString);
//            JSONArray arr = object.getJSONArray("questions");
//
//
//
//            for (int i = 0; i < arr.length(); ++i) {
//                al.add( arr.getJSONObject(i).getString("question"));
//            }
//
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
//            list.setAdapter(adapter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        dialog.dismiss();
//    }
//}