package com.test.currentprices;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Submissions extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;
    
    //testing on Emulator:
    private static final String GET_SUB_URL = "http://10.0.2.2/currentpriceservice/get_submissions.php";
    
  //testing from a real server:
    //private static final String GET_SUB_URL = "http://currentpriceservice.herobo.com/get_submissions.php";
   
  //JSON IDS:
    //private static final String TAG_SUCCESS = "success";
    private static final String TAG_SUB = "submissions";
    //private static final String TAG_ID = "sub_id";
    private static final String TAG_NAME = "vendor_name";
    private static final String TAG_MKT = "market_name";
    private static final String TAG_CMD = "commodity_name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_UNIT = "unit_name"; 
    
   //An array of all of our submissions
    private JSONArray submissions = null;
    //manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> submissionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //note that use read_comments.xml instead of our single_post.xml
        setContentView(R.layout.submissions);   
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	//loading the submissions via AsyncTask
    	new LoadSubmissions().execute();
    }

	public void submit(View v)
    {
        Intent i = new Intent(Submissions.this, Submit.class);
        startActivity(i);
    }

	/**
     * Retrieves recent post data from the server.
     */
    public void updateJSONdata() {

        // Instantiate the arraylist to contain all the JSON data.
    	// we are going to use a bunch of key-value pairs, referring
    	// to the json element name, and the content
    	
        submissionList = new ArrayList<HashMap<String, String>>();
        
        //
        JSONParser jParser = new JSONParser();
        //
        JSONObject json = jParser.getJSONFromUrl(GET_SUB_URL);

        //try to catch any exceptions:
        try {
            
        	//returns number of submissions available
            submissions = json.getJSONArray(TAG_SUB);
 
            // looping through all submissions according to the json object returned
            for (int i = 0; i < submissions.length(); i++) {
                JSONObject c = submissions.getJSONObject(i);

                //gets the content of each tag
                String commodity = c.getString(TAG_CMD);
                String price = c.getString(TAG_PRICE);
                String unit = c.getString(TAG_UNIT);
                String market = c.getString(TAG_MKT);
                String name = c.getString(TAG_NAME);
                

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
              
                map.put(TAG_CMD, commodity);
                map.put(TAG_PRICE, price);
                map.put(TAG_UNIT, unit);
                map.put(TAG_MKT, market);
                map.put(TAG_NAME, name);
             
                // adding HashList to ArrayList
                submissionList.add(map);
                
                //annndddd, our JSON data is up to date same with our array list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
	 * Inserts the parsed data into the listview.
	 */
	private void updateList() { 
		// For a ListActivity we need to set the List Adapter, and in order to do
		//that, we need to create a ListAdapter.  This SimpleAdapter,
		//will utilize our updated Hashmapped ArrayList, 
		//use the single_submission xml template for each item in our list,
		//and place the appropriate info from the list to the
		//correct GUI id.  Order is important here.
		ListAdapter adapter = new SimpleAdapter(this, submissionList,
				R.layout.single_submission, new String[] { TAG_CMD, TAG_PRICE, TAG_UNIT, TAG_MKT, TAG_NAME }, 
				new int[] { R.id.cmmdity, R.id.cprice, R.id.punit, R.id.market, R.id.name });

		// list adapter
		setListAdapter(adapter);
		
		// when the user clicks a list item we could do something.
		ListView lv = getListView();	
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// This method is triggered if an item is click within our
			}
		});
	} 

	//get submissions
    public class LoadSubmissions extends AsyncTask<Void, Void, Boolean> {

    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Submissions.this);
			pDialog.setMessage("Loading Submissions ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
    	
        @Override
        protected Boolean doInBackground(Void... arg0) {
            updateJSONdata();
            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            updateList();
        }
    }
}
