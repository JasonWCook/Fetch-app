/** Author: Jason Cook
 *  Purpose: show that I can grab JSON data in an app and sort each entry by given parameters.
 *  Date started: 08/15/2021
*/
package edu.wwu.csci412.fetch_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* Variables for JSON grabbing with Volley */
    private TextView mTextViewResult;
    private Button myButton;
    private RequestQueue mQueue;

    /* Store the data in an array list for easy sorting later */
    ArrayList<Item> itemList = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Grab JSON data from URL using Volley */
       // mTextViewResult = findViewById(R.id.textViewResult);
        //myButton = findViewById(R.id.button);
        mQueue = Volley.newRequestQueue(this);
        getDataFromServer();
/*
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromServer();
                updateDisplay();
            }
        });*/

    }

    /**
     * The data retrieval function.
     *
     * stores JSON data from the web address locally for sorting
     */
    private void getDataFromServer() {
        /* Prepare the Request */
        String jsonURL = getApplicationContext().getString(R.string.json_source);

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, jsonURL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                /* Create a temp object from the array */
                                JSONObject entry = response.getJSONObject(i);
                                int id = entry.getInt("id");
                                int listId = entry.getInt("listId");
                                String name = entry.getString("name");

                                /* Create an item element and add it to the array list */
                                Item tempItem = new Item(id, listId, name);
                                itemList.add(tempItem);

                               // mTextViewResult.append(id + ", " + listId + ", " + name + "\n\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        updateDisplay();
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
    }

    private void updateDisplay(){
        // Build a View dynamically with all the items
        if( itemList.size() > 0 ) {
            // Create ScrollView and GridLayout
            ScrollView scrollView = new ScrollView( this );
            GridLayout grid = new GridLayout( this );
            grid.setRowCount( itemList.size() );
            grid.setColumnCount( 3 );

            // Create arrays of components
            TextView [] ids = new TextView[itemList.size()];
            TextView [] listIds = new TextView[itemList.size()];
            TextView [] names = new TextView[itemList.size()];

            // Retrieve width of screen
            Point size = new Point( );
            getWindowManager( ).getDefaultDisplay( ).getSize( size );
            //this.getDisplay().getRealSize(size);
            int width = size.x;

            int i = 0;

            for ( Item item : itemList ) {
                // Create the TextView for the item's id
                ids[i] = new TextView( this );
                ids[i].setGravity( Gravity.CENTER );
                ids[i].setText( "" + item.getId( ) );

                // Create list id
                listIds[i] = new TextView( this );
                listIds[i].setText( "" + item.getListId());

                // create the name
                names[i] = new TextView( this );
                names[i].setText( item.getName() );


                // Add the elements to grid ONLY if name was not null, "null", or empty
                if (item.getName() != null && item.getName().compareTo("null") != 0 && item.getName().length() > 0) {
                    grid.addView( ids[i], ( int ) (width * .25), ViewGroup.LayoutParams.WRAP_CONTENT );
                    grid.addView( listIds[i], ( int ) ( width * .5 ), ViewGroup.LayoutParams.WRAP_CONTENT );
                    grid.addView( names[i], ( int ) ( width * .5 ), ViewGroup.LayoutParams.WRAP_CONTENT );
                    i++;
                }
            }
            scrollView.addView( grid );
            setContentView( scrollView );
        }
    }
}