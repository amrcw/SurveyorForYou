package uk.co.surveyorforyou.surveyorforyou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        final ImageView userProf = (ImageView)rootView.findViewById(R.id.imgProfile);
        final TextView dashName = (TextView)rootView.findViewById(R.id.dashName);
        final TextView dashEmail = (TextView)rootView.findViewById(R.id.dashEmail);
        final TextView dashAdd = (TextView)rootView.findViewById(R.id.dashAdd);
        final TextView dashPostcode = (TextView)rootView.findViewById(R.id.dashPostcode);
        final TextView dashContact = (TextView)rootView.findViewById(R.id.dashContact);


        String firstname = getArguments().getString("firstname");
        String lastname = getArguments().getString("lastname");
        String email = getArguments().getString("email");
        String address = getArguments().getString("address");
        String postcode = getArguments().getString("postcode");
        String photo_path = getArguments().getString("photo_path");

        Glide.with(DashboardFragment.this).load(photo_path).into(userProf);
        dashName.setText(firstname+ " " +lastname);
        dashAdd.setText(address);
        dashPostcode.setText(postcode);
        dashEmail.setText(email);

        return  rootView;
    }



}
