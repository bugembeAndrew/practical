package com.test.currentprices;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Submit extends Activity implements OnClickListener, OnItemSelectedListener {
	
	//field variables
	private EditText name, price;
	private Spinner market, commodity, unit;
	private Button submit;
	
	//string values
	private String mkt_value;
	private String cmmdity_value;
	private String unit_value;
	
	// array list for spinner adapter
    private ArrayList<Market> marketList;
    private ArrayList<Commodity> commodityList;
    private ArrayList<Unit> unitList;
	
	 // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
   //testing on Emulator:
    private static final String SUBMIT_URL = "http://10.0.2.2/currentpriceservice/add_submission.php";
    private static final String MARKET_URL = "http://10.0.2.2/currentpriceservice/get_market.php";
    private static final String UNIT_URL = "http://10.0.2.2/currentpriceservice/get_unit.php";
    private static final String COMMODITY_URL = "http://10.0.2.2/currentpriceservice/get_commodity.php";

    //testing from a real server:
	//private static final String SUBMT_URL = "http://currentpriceservice.herobo.com/add_submission.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit);
		
		//drop down items
		marketList = new ArrayList<Market>();
		commodityList = new ArrayList<Commodity>();
		unitList = new ArrayList<Unit>();

		
		//get widget ids
		name = (EditText) findViewById(R.id.name);
		price = (EditText) findViewById(R.id.price);
		
		market = (Spinner) findViewById(R.id.mkt);
		market.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            Object item = parent.getItemIdAtPosition(pos);
	            mkt_value = item.toString();
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub

	        }
	    });
		
		commodity = (Spinner) findViewById(R.id.commodity);
		commodity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            Object item = parent.getItemIdAtPosition(pos);
	            cmmdity_value = item.toString();
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub

	        }
	    });
		
		unit = (Spinner) findViewById(R.id.unit);
		unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            Object item = parent.getItemIdAtPosition(pos);
	            unit_value = item.toString();
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub

	        }
	    });
		
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		
		new GetMarkets().execute();
		//new GetCommodities().execute();
		//new GetUnits().execute();

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new CreateSubmission().execute();
	}
	
	/**
     * Adding spinner data
     * */
    private void populateMarketSpinner() {
        List<String> lables = new ArrayList<String>();
         
        //txtCategory.setText("");
 
        for (int i = 0; i < marketList.size(); i++) {
            lables.add(marketList.get(i).getName());
        }
 
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        market.setAdapter(spinnerAdapter);
        new GetCommodities().execute();
        //commodity.setAdapter(spinnerAdapter);
        //unit.setAdapter(spinnerAdapter);
    }
    
    /**
     * Adding spinner data
     * */
    private void populateCommoditySpinner() {
        List<String> lables = new ArrayList<String>();
         
        //txtCategory.setText("");
 
        for (int i = 0; i < commodityList.size(); i++) {
            lables.add(commodityList.get(i).getName());
        }
 
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        //market.setAdapter(spinnerAdapter);
        commodity.setAdapter(spinnerAdapter);
        new GetUnits().execute();
       // unit.setAdapter(spinnerAdapter);
    }
    
    public void viewSubmissions(View v)
    {
        Intent i = new Intent(Submit.this, Submissions.class);
        startActivity(i);
    }
    
    /**
     * Adding spinner data
     * */
    private void populateUnitSpinner() {
        List<String> lables = new ArrayList<String>();
         
        //txtCategory.setText("");
 
        for (int i = 0; i < unitList.size(); i++) {
            lables.add(unitList.get(i).getName());
        }
 
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        //market.setAdapter(spinnerAdapter);
        //commodity.setAdapter(spinnerAdapter);
        unit.setAdapter(spinnerAdapter);
    }
    
    /**
     * Async task to get all markets
     * */
    private class GetMarkets extends AsyncTask<Void, Void, Void> {
     
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Submit.this);
            pDialog.setMessage("Fetching vendor markets in kampala ...");
            pDialog.setCancelable(false);
            pDialog.show();
     
        }
     
        @Override
        protected Void doInBackground(Void... arg0) {
            JSONParser jsonParser = new JSONParser();
            String json = jsonParser.makeServiceCall(MARKET_URL, JSONParser.GET);
     
            Log.e("Response: ", "> " + json);
     
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray markets = jsonObj.getJSONArray("markets");                       
     
     
                        for (int i = 0; i < markets.length(); i++) {
                            JSONObject catObj = (JSONObject) markets.get(i);
                            Market mkt = new Market(catObj.getInt("market_id"), catObj.getString("market_name"));
                            marketList.add(mkt);
                        }
                    }
     
                } catch (JSONException e) {
                    e.printStackTrace();
                }
     
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
     
            return null;
        }
     
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateMarketSpinner();
        }
     
    }
    
    //get commodities
    private class GetCommodities extends AsyncTask<Void, Void, Void> {
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Submit.this);
            pDialog.setMessage("Fetching vendor commodities ...");
            pDialog.setCancelable(false);
            pDialog.show();
     
        }
     
        @Override
        protected Void doInBackground(Void... arg0) {
            JSONParser jsonParser = new JSONParser();
            String json = jsonParser.makeServiceCall(COMMODITY_URL, JSONParser.GET);
     
            Log.e("Response: ", "> " + json);
     
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray commodities = jsonObj.getJSONArray("commodities");                       
     
     
                        for (int i = 0; i < commodities.length(); i++) {
                            JSONObject catObj = (JSONObject) commodities.get(i);
                            Commodity cmdity = new Commodity(catObj.getInt("commodity_id"), catObj.getString("commodity_name"));
                            commodityList.add(cmdity);
                        }
                    }
     
                } catch (JSONException e) {
                    e.printStackTrace();
                }
     
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
     
            return null;
        }
     
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateCommoditySpinner();
        }
     
    }
    
    //get units
    private class GetUnits extends AsyncTask<Void, Void, Void> {
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Submit.this);
            pDialog.setMessage("Fetching units of sale ...");
            pDialog.setCancelable(false);
            pDialog.show();
     
        }
     
        @Override
        protected Void doInBackground(Void... arg0) {
            JSONParser jsonParser = new JSONParser();
            String json = jsonParser.makeServiceCall(UNIT_URL, JSONParser.GET);
     
            Log.e("Response: ", "> " + json);
     
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray unties = jsonObj.getJSONArray("units");                       
     
     
                        for (int i = 0; i < unties.length(); i++) {
                            JSONObject catObj = (JSONObject) unties.get(i);
                            Unit unty = new Unit(catObj.getInt("unit_id"), catObj.getString("unit_name"));
                            unitList.add(unty);
                        }
                    }
     
                } catch (JSONException e) {
                    e.printStackTrace();
                }
     
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
     
            return null;
        }
     
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateUnitSpinner();
        }
     
    }

	//create submission
	class CreateSubmission extends AsyncTask<String, String, String> {

		 /**
       * Before starting background thread Show Progress Dialog
       * */
		boolean failure = false;

      @Override
      protected void onPreExecute() {
          super.onPreExecute();
          pDialog = new ProgressDialog(Submit.this);
          pDialog.setMessage("Creating Submission...");
          pDialog.setIndeterminate(false);
          pDialog.setCancelable(true);
          pDialog.show();
      }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
          int success;
          String vendorname = name.getText().toString();
          String cprice = price.getText().toString();
         String mkt = mkt_value;
          String cmdity = cmmdity_value;
          String unt = unit_value;
          
          try {
              // Building Parameters
              List<NameValuePair> params = new ArrayList<NameValuePair>();
              params.add(new BasicNameValuePair("name", vendorname));
              params.add(new BasicNameValuePair("price", cprice));
              params.add(new BasicNameValuePair("markets", mkt));
              params.add(new BasicNameValuePair("commodities", cmdity));
              params.add(new BasicNameValuePair("units", unt));

              Log.d("request!", "starting");

              //Posting user data to script
              JSONObject json = jsonParser.makeHttpRequest(SUBMIT_URL, "POST", params);

              // full json response
              Log.d("Submission attempt", json.toString());

              // json success element
              success = json.getInt(TAG_SUCCESS);
              if (success == 1) {
              	Log.d("Submission created!", json.toString());
              	Intent i = new Intent(Submit.this, Submissions.class);//go to submissions
				finish();
				startActivity(i);
              	return json.getString(TAG_MESSAGE);
              }else{
              	Log.d("Submission Failure!", json.getString(TAG_MESSAGE));
              	return json.getString(TAG_MESSAGE);

              }
          } catch (JSONException e) {
              e.printStackTrace();
          }

          return null;

		}
		/**
       * After completing background task Dismiss the progress dialog
       * **/
      protected void onPostExecute(String file_url) {
          // dismiss the dialog once product deleted
          pDialog.dismiss();
          if (file_url != null){
          	Toast.makeText(Submit.this, file_url, Toast.LENGTH_LONG).show();
          }

      }

	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString() + " Selected" , Toast.LENGTH_LONG).show();
    }
 
    public void onNothingSelected(AdapterView<?> arg0) {  
    	
    }

	
}
