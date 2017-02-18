package uk.co.surveyorforyou.surveyorforyou;


import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertiesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    JSONObject jsonObject;
    JSONArray jsonArray;
    PropertiesAdapter propertiesAdapter;
    ListView listView;

    public PropertiesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_properties, container, false);

        propertiesAdapter= new PropertiesAdapter(PropertiesFragment.this,R.layout.properties_list_layout);
        listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(propertiesAdapter);

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

                Properties contacts = new Properties(refNumber,clientName,clientPhone,postcode,dateOrdered,dueDate);
                propertiesAdapter.add(contacts);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;

        //recyclerView = (RecyclerView)findViewById(R.id.rec);
    }

}
