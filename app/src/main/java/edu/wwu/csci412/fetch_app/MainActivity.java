/** Author: Jason Cook
 *  Purpose: show that I can grab JSON data in an app and sort each entry by given parameters.
 *  Date started: 08/15/2021
*/
package edu.wwu.csci412.fetch_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    /* Variables for JSON grabbing with Volley */
    private TextView mTextViewResult;
    private Button myButton;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Grab JSON data from URL using Volley */
        mTextViewResult = findViewById(R.id.textViewResult);
        myButton = findViewById(R.id.button);
        mQueue = Volley.newRequestQueue(this);
        //getDataFromServer();
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getDataFromServer();
            }
        });

    }

    /**
     * The data retrieval function.
     *
     * stores JSON data from the web address locally for sorting
     */
    private void getDataFromServer() {
        String jsonURL = getApplicationContext().getString(R.string.json_source);
        System.out.println(jsonURL);
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, jsonURL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        try {
                            mTextViewResult.append(response.getJSONObject(0).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Got response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        mQueue.add(getRequest);
    /*
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, jsonURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonResponse = response.getJSONArray("");
                            for (int i = 0; i < jsonResponse.length(); i++) {
                                JSONObject entry = jsonResponse.getJSONObject(i);

                                int id = entry.getInt("id");
                                int listId = entry.getInt("listId");
                                String name = entry.getString("mail");

                                mTextViewResult.append(id + ", " + listId + ", " + name + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
        });
        mQueue.add(request);

 */
    }
}