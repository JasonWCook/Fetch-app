/** Author: Jason Cook
 *  Purpose: show that I can grab JSON data in an app and sort each entry by given parameters.
 *  Date started: 08/15/2021
*/
package edu.wwu.csci412.fetch_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        mQueue = Volley.newRequestQueue(this);
        getDataFromServer();
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

                                /* Create an item element and add it to the array list IFF the name isnt null, "null", or "" */
                                if (name != null && name.compareTo("null") != 0 && name.length() > 0) {
                                    Item tempItem = new Item(id, listId, name);
                                    itemList.add(tempItem);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        sortValues();
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

    private void sortValues() {
        Collections.sort(itemList, new Comparator() {

            public int compare(Object o1, Object o2) {

                int x1 = ((Item) o1).getListId();
                int x2 = ((Item) o2).getListId();

                int diff = x1 - x2;
                if (diff != 0) {
                    return diff;
                }

                String s1 = ((Item) o1).getName();
                String s2 = ((Item) o2).getName();
                return s1.compareTo(s2);
            }});
    }
    private void updateDisplay(){
        // Build a View dynamically with all the items
        if( itemList.size() > 0 ) {

            // Create a container for the heading and recycler view
            LinearLayout outerLayout = new LinearLayout(this);
            outerLayout.setOrientation(LinearLayout.VERTICAL);

            // Create the heading so users know what the numbers mean
            LinearLayout headingLayout = new LinearLayout(this);
            headingLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView idLabel = new TextView(this);
            idLabel.setText("ID");
            TextView listIdLabel = new TextView(this);
            listIdLabel.setText("LIST ID");
            TextView itemLabel = new TextView(this);
            itemLabel.setText("ITEM NAME");
            headingLayout.addView(idLabel);
            headingLayout.addView(listIdLabel);
            headingLayout.addView(itemLabel);

            // Create Recyclerview and bind the adapter to it
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            ItemAdapter mAdapter = new ItemAdapter(itemList);

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(mAdapter);

            // Set the view to the content
            outerLayout.addView(headingLayout);
            outerLayout.addView(recyclerView);
            setContentView( outerLayout );
            /*
             ScrollView scrollView = new ScrollView( this );
            GridLayout grid = new GridLayout( this );
            grid.setRowCount( itemList.size() );
            grid.setPaddingRelative(25,50,25,50);
            grid.setColumnCount( 3 );

            // Create arrays of components
            TextView [] ids = new TextView[itemList.size()];
            TextView [] listIds = new TextView[itemList.size()];
            TextView [] names = new TextView[itemList.size()];

            // Retrieve width of screen
            Point size = new Point( );
            this.getDisplay().getRealSize(size);
            int width = size.x;

            int i = 0;

            for ( Item item : itemList ) {
                // Create the TextView for the item's id
                ids[i] = new TextView( this );
                ids[i].setGravity( Gravity.RIGHT );
                ids[i].setText( "" + item.getId( ) );
                ids[i].setTextSize(25);

                // Create list id
                listIds[i] = new TextView( this );
                listIds[i].setGravity( Gravity.RIGHT );
                listIds[i].setText( "" + item.getListId());
                listIds[i].setTextSize(25);

                // Create the name
                names[i] = new TextView( this );
                names[i].setGravity( Gravity.LEFT );
                names[i].setText( item.getName() );
                names[i].setTextSize(25);

                // Add the elements to grid ONLY if name was not null, "null", or empty
                if (item.getName() != null && item.getName().compareTo("null") != 0 && item.getName().length() > 0) {
                    grid.addView( ids[i], ( int ) (width / 3 ), ViewGroup.LayoutParams.MATCH_PARENT );
                    grid.addView( listIds[i], ( int ) ( width / 3 ), ViewGroup.LayoutParams.MATCH_PARENT );
                    grid.addView( names[i], ( int ) ( width / 3 ), ViewGroup.LayoutParams.MATCH_PARENT );
                    i++;
                }
            }
            scrollView.addView( grid );*/

            /*scrollView.addView();
            setContentView( scrollView );*/
        }
    }
}