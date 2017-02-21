package uk.co.surveyorforyou.surveyorforyou;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class PropertiesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    //ListView lisstV;
    android.widget.SearchView searchView;
    Typeface type;
    ListView searchResults;



    JSONObject jsonObject;
    JSONArray jsonArray;
    PropertiesAdapter propertiesAdapter;

    ListView listView;
    Properties contacts;


    //This arraylist will have data as pulled from server. This will keep cumulating.
    ArrayList<Properties> productResults = new ArrayList<Properties>();
    //Based on the search string, only filtered products will be moved here from productResults
    ArrayList<Properties> filteredProductResults = new ArrayList<Properties>();



    public PropertiesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        //get the context of the HomeScreen Activity
  //      final UserDashboard activity = (UserDashboard) getActivity();

   //     type= Typeface.createFromAsset(activity.getAssets(),"fonts/book.TTF");
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_properties, container, false);

        propertiesAdapter= new PropertiesAdapter(PropertiesFragment.this,R.layout.properties_list_layout);

        listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(propertiesAdapter);


        searchView = (SearchView) rootView.findViewById(R.id.refSearch);
        searchView.setQueryHint("Start typing to search...");


        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(getActivity(), String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //propertiesAdapter.getFilter().filter(newText);
                myAsyncTask m= (myAsyncTask) new myAsyncTask().execute(newText);

                return false;
            }

        });

        String json_string = getArguments().getString("json data");

        try {
            jsonObject = new  JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            String refNumber,clientName,clientPhone,postcode,dateOrdered,dueDate;

            while(count < jsonArray.length()){

                JSONObject jo = jsonArray.getJSONObject(count);
                refNumber  = jo.getString("id");
                clientName  = jo.getString("first_name");
                clientPhone  = jo.getString("phone");
                postcode  = jo.getString("survey_postcode");
                dateOrdered  = jo.getString("date_quoted");
                dueDate  = jo.getString("date_due");

                contacts = new Properties(refNumber,clientName,clientPhone,postcode,dateOrdered,dueDate);
                propertiesAdapter.add(contacts);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;

        //recyclerView = (RecyclerView)findViewById(R.id.rec);

    }


    public void filterProductArray(String newText)
    {

        String pName;

        filteredProductResults.clear();
        for (int i = 0; i < productResults.size(); i++)
        {
            pName = productResults.get(i).getName().toLowerCase();
            if ( pName.contains(newText.toLowerCase()) ||
                    productResults.get(i).getName().contains(newText))
            {
                filteredProductResults.add(productResults.get(i));

            }
        }

    }



    //in this myAsyncTask, we are fetching data from server for the search string entered by user.
    class myAsyncTask extends AsyncTask<String, Void, String>
    {
        Context context;
        ProgressDialog progressDialog;
        String jsonfile = new String();

        String textSearch;

        public myAsyncTask(){}

        public myAsyncTask(Context context){
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setTitle("Please wait...");
//            progressDialog.setMessage("Download in progress...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            jsonfile ="http://www.surveyor-for-you.co.uk/Android/getProperties.php/"+sText[0];
            String returnResult = getProductList(jsonfile);
            this.textSearch = sText[0];
            return returnResult;

        }

        public String getProductList(String urls)
        {

            Properties tempProduct = new Properties();
            String matchFound = "N";
            //productResults is an arraylist with all product details for the search criteria
            //productResults.clear();

            try {
                URL url = new URL(urls);
                HttpURLConnection httpurlcon = (HttpURLConnection) url.openConnection();
                InputStream streaminput = httpurlcon.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streaminput));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null){

                    stringBuilder.append(line+"\n");
                   // Thread.sleep(500);
                }

                String json_data = stringBuilder.toString().trim();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json_data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                //parse date for dateList
                for(int i=0;i<jsonArray.length();i++)
                {
                    tempProduct = new Properties();

                    JSONObject obj=jsonArray.getJSONObject(i);

                    tempProduct.setRefNo(obj.getString("id"));
                    tempProduct.setName(obj.getString("first_name"));
                    tempProduct.setPhone(obj.getString("phone"));
                    tempProduct.setPostcode(obj.getString("survey_postcode"));
                    tempProduct.setDateOrdered(obj.getString("date_quoted"));
                    tempProduct.setDueDate(obj.getString("date_due"));


                    //check if this product is already there in productResults, if yes, then don't add it again.
                    matchFound = "N";

                    for (int j=0; j < productResults.size();j++)
                    {

                        if (productResults.get(j).getRefNo().equals(tempProduct.getName()))
                        {
                            matchFound = "Y";
                        }
                    }

                    if (matchFound == "N")
                    {
                        productResults.add(tempProduct);
                    }

                }

                return ("OK");


        }  catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return "Exception Caught";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.equalsIgnoreCase("Exception Caught"))
            {
                Toast.makeText(getActivity(), "Unable to connect to server,please try later", Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }
            else
            {


                //calling this method to filter the search results from productResults and move them to
                //filteredProductResults
                filterProductArray(textSearch);
                searchResults.setAdapter(new SearchResultsAdapter(getContext(),filteredProductResults));
                progressDialog.dismiss();
            }
        }
    }

}


class SearchResultsAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;

    private ArrayList<Properties> productDetails=new ArrayList<Properties>();
    int count;
    Typeface type;
    Context context;

    //constructor method
    public SearchResultsAdapter(Context context, ArrayList<Properties> product_details) {

        layoutInflater = LayoutInflater.from(context);

        this.productDetails=product_details;
        this.count= product_details.size();
        this.context = context;
       // type= Typeface.createFromAsset(context.getAssets(),"fonts/book.TTF");

    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        return productDetails.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        Properties tempProduct = productDetails.get(position);

        View row;
       // PropertiesAdapter.PropertiesHolder propertiesHolder;

        row = convertView;
        if(row == null){
           // LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.properties_list_layout,null);
            holder = new ViewHolder();
            holder.refNo = (TextView) row.findViewById(R.id.refNo);
            holder.name = (TextView) row.findViewById(R.id.clientName);
            holder.phone = (TextView) row.findViewById(R.id.clientPhone);
            holder.postcode = (TextView) row.findViewById(R.id.surveyPostcode);
            holder.dateOrdered = (TextView) row.findViewById(R.id.dateOrdered);
            holder.dueDate = (TextView) row.findViewById(R.id.dateDue);
            row.setTag(holder);
        }else{

            holder = (ViewHolder) convertView.getTag();
        }

        //Properties properties = (Properties) this.getItem(position);
        holder.refNo.setText(tempProduct.getRefNo());
       // holder.refNo.setTypeface(type);

        holder.name.setText(tempProduct.getName());
       // holder.name.setTypeface(type);

        holder.phone.setText(tempProduct.getPhone());
      //  holder.phone.setTypeface(type);

        holder.postcode.setText(tempProduct.getPostcode());
       // holder.postcode.setTypeface(type);

        holder.dateOrdered.setText(tempProduct.getDateOrdered());
       // holder.dateOrdered.setTypeface(type);

        holder.dueDate.setText(tempProduct.getDueDate());
       // holder.dueDate.setTypeface(type);

        return row;
    }

    static class ViewHolder
    {
        TextView refNo,name,phone,postcode,dateOrdered,dueDate;

    }

}
