package uk.co.surveyorforyou.surveyorforyou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class PropertiesFragment extends Fragment implements View.OnClickListener{



    //ListView lisstV;
    EditText searchView;
    EditText searchJobID;
    EditText searchPostcode;
    ListView searchResults;
    Button viewDialog;

    JSONObject jsonObject;
    JSONArray jsonArray;
    PropertiesAdapter propertiesAdapter;

    ListView listView;
    Properties contacts;
    View rootView;



    //This arraylist will have data as pulled from server. This will keep cumulating.
    ArrayList<Properties> productResults = new ArrayList<Properties>();
    //Based on the search string, only filtered products will be moved here from productResults
    ArrayList<Properties> filteredProductResults = new ArrayList<Properties>();



    public PropertiesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

   //     get the context of the HomeScreen Activity
   //      final UserDashboard activity = (UserDashboard) getActivity();

        //     type= Typeface.createFromAsset(activity.getAssets(),"fonts/book.TTF");
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_properties, container, false);
        propertiesAdapter= new PropertiesAdapter(PropertiesFragment.this,R.layout.properties_list_layout);
        listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(propertiesAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // int item = (int) parent.getAdapter().getItem(position);
                showAlertDialog();

            }
        });


        searchView = (EditText) rootView.findViewById(R.id.refSearch);
        searchJobID = (EditText) rootView.findViewById(R.id.refJobNum);
        searchPostcode = (EditText) rootView.findViewById(R.id.refPostcode);
       // searchView.setQueryHint("Start typing to search...");
        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter

                propertiesAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchJobID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                propertiesAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchPostcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                propertiesAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Toast.makeText(getActivity(), String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//        {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // TODO Auto-generated method stub
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                //propertiesAdapter.getFilter().filter(newText);
//                myAsyncTask m= (myAsyncTask) new myAsyncTask().execute(newText);
//
//                return false;
//            }
//
//        });

        String json_string = getArguments().getString("json data");

        try {
            jsonObject = new  JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            String refNumber,clientName,clientPhone,postcode,dateOrdered,dueDate,jobStatus;

            while(count < jsonArray.length()){

                JSONObject jo = jsonArray.getJSONObject(count);
                refNumber  = jo.getString("id");
                clientName  = jo.getString("first_name");
                clientPhone  = jo.getString("phone");
                postcode  = jo.getString("survey_postcode");
                dateOrdered  = jo.getString("date_quoted");
                dueDate  = jo.getString("date_due");
                jobStatus  = jo.getString("active");

                contacts = new Properties(refNumber,clientName,clientPhone,postcode,dateOrdered,dueDate,jobStatus);
                propertiesAdapter.add(contacts);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;

        //recyclerView = (RecyclerView)findViewById(R.id.rec);


    }


    private void showAlertDialog() {
        FragmentManager fm = getChildFragmentManager();
        JobDetailsDialogFragment alertDialog = JobDetailsDialogFragment.newInstance("Job Details");
        alertDialog.show(fm, "fragment_alert");
    }

    @Override
    public void onClick(View v) {
        searchView.setText("");
        searchJobID.setText("");
        searchPostcode.setText("");
    }


//    public void filterProductArray(String newText)
//    {
//
//        String pName;
//
//        filteredProductResults.clear();
//        for (int i = 0; i < productResults.size(); i++)
//        {
//            pName = productResults.get(i).getName().toLowerCase();
//            if ( pName.contains(newText.toLowerCase()) ||
//                    productResults.get(i).getName().contains(newText))
//            {
//                filteredProductResults.add(productResults.get(i));
//
//            }
//        }
//
//    }
//
//
//
//    //in this myAsyncTask, we are fetching data from server for the search string entered by user.
//    class myAsyncTask extends AsyncTask<String, Void, String>
//    {
//        Context context;
//        ProgressDialog progressDialog;
//        String jsonfile = new String();
//        String textSearch;
//
//        public myAsyncTask(){}
//
//        public myAsyncTask(Context context){
//            this.context = context;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progressDialog = new ProgressDialog(context);
////            progressDialog.setIndeterminate(true);
////            progressDialog.setTitle("Please wait...");
////            progressDialog.setMessage("Download in progress...");
////            progressDialog.setCancelable(false);
////            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... sText) {
//
//            jsonfile ="http://www.surveyor-for-you.co.uk/Android/getProperties.php/"+sText[0];
//            String returnResult = getProductList(jsonfile);
//            this.textSearch = sText[0];
//            return returnResult;
//
//        }
//
//        public String getProductList(String urls)
//        {
//
//            Properties tempProduct = new Properties();
//            String matchFound = "N";
//            //productResults is an arraylist with all product details for the search criteria
//            //productResults.clear();
//
//            try {
//                URL url = new URL(urls);
//                HttpURLConnection httpurlcon = (HttpURLConnection) url.openConnection();
//                InputStream streaminput = httpurlcon.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streaminput));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//
//                while ((line = bufferedReader.readLine()) != null){
//
//                    stringBuilder.append(line+"\n");
//                   // Thread.sleep(500);
//                }
//
//                String json_data = stringBuilder.toString().trim();
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(json_data);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
//
//                //parse date for dateList
//                for(int i=0;i<jsonArray.length();i++)
//                {
//                    tempProduct = new Properties();
//
//                    JSONObject obj=jsonArray.getJSONObject(i);
//
//                    tempProduct.setRefNo(obj.getString("id"));
//                    tempProduct.setName(obj.getString("first_name"));
//                    tempProduct.setPhone(obj.getString("phone"));
//                    tempProduct.setPostcode(obj.getString("survey_postcode"));
//                    tempProduct.setDateOrdered(obj.getString("date_quoted"));
//                    tempProduct.setDueDate(obj.getString("date_due"));
//
//
//                    //check if this product is already there in productResults, if yes, then don't add it again.
//                    matchFound = "N";
//
//                    for (int j=0; j < productResults.size();j++)
//                    {
//
//                        if (productResults.get(j).getRefNo().equals(tempProduct.getName()))
//                        {
//                            matchFound = "Y";
//                        }
//                    }
//
//                    if (matchFound == "N")
//                    {
//                        productResults.add(tempProduct);
//                    }
//
//                }
//
//                return ("OK");
//
//
//        }  catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            return "Exception Caught";
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            if(result.equalsIgnoreCase("Exception Caught"))
//            {
//                Toast.makeText(getActivity(), "Unable to connect to server,please try later", Toast.LENGTH_LONG).show();
//
//                progressDialog.dismiss();
//            }
//            else
//            {
//
//
//                //calling this method to filter the search results from productResults and move them to
//                //filteredProductResults
//                filterProductArray(textSearch);
//               // searchResults.setAdapter(new SearchResultsAdapter(getContext(),filteredProductResults));
//               // progressDialog.dismiss();
//            }
//        }
//    }

}



