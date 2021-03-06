package uk.co.surveyorforyou.surveyorforyou;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserDashboard extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private Spinner mSpinner;
    private Fragment fragment = null;
    private Class fragmentClass = null;
    PropertiesAdapter propertiesAdapter;
    String JSON_STRING;
    String json_string;
    Button viewDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        //mSpinner.setVisibility(View.INVISIBLE);
        new BackgroundTask().execute();

        //PropertiesTask propertiesTask = new PropertiesTask(UserDashboard.this);
        //propertiesTask.execute();




        mSpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(UserDashboard.this,R.layout.custom_spinner_item,getResources().getStringArray(R.array.properties));
        mAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(UserDashboard.this,mSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

                switch ((String) mSpinner.getItemAtPosition(position)) {

                    case "In Progress":
                        //Toast.makeText(UserDashboard.this,mSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                        propertiesAdapter.getFilter().filter(mSpinner.getSelectedItem().toString());
                        break;
                    case "Complete":
                        Toast.makeText(UserDashboard.this,mSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                        break;
                    case "All":
                        Toast.makeText(UserDashboard.this,mSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }

                Log.i("item", (String) mSpinner.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mToolbar = (Toolbar)findViewById(R.id.nav_action_toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("Dashboard");
        setSupportActionBar(mToolbar);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.nav_drawer_open,R.string.nav_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("address");
        String postcode = getIntent().getStringExtra("postcode");
        String photo_path = getIntent().getStringExtra("photo_id");





        NavigationView navigationView = (NavigationView) findViewById(R.id.dash_nav_view);
        View nav_header = LayoutInflater.from(this).inflate(R.layout.dash_nav_header, null);
        ((TextView) nav_header.findViewById(R.id.userEmail)).setText(email);
        final ImageView userPic = (ImageView) nav_header.findViewById(R.id.userImage);
        Glide.with(UserDashboard.this).load(photo_path).into(userPic);
        navigationView.addHeaderView(nav_header);

        setupDrawerContent(navigationView);

        //Glide.with(UserDashboard.this).load(photo_path).into(userProf);
        //dashName.setText(firstname+ " " +lastname);
        //dashAdd.setText(address);
        //dashPostcode.setText(postcode);
        //dashEmail.setText(email);



    }




    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        switch(menuItem.getItemId()) {
            case R.id.nav_camera:
                fragmentClass = CameraFragment.class;
                mSpinner.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_properties:
                fragmentClass = PropertiesFragment.class;
                mSpinner.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_dashboad:
                fragmentClass = DashboardFragment.class;
                mSpinner.setVisibility(View.INVISIBLE);
                break;


        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();

            String firstname = getIntent().getStringExtra("firstname");
            String lastname = getIntent().getStringExtra("lastname");
            String email = getIntent().getStringExtra("email");
            String address = getIntent().getStringExtra("address");
            String postcode = getIntent().getStringExtra("postcode");
            String photo_path = getIntent().getStringExtra("photo_id");



            Bundle bundle = new Bundle();
            bundle.putString("firstname", firstname);
            bundle.putString("lastname", lastname);
            bundle.putString("email", email);
            bundle.putString("address", address);
            bundle.putString("postcode", postcode);
            bundle.putString("photo_path", photo_path);
            if(json_string == null){
                Toast.makeText(getApplicationContext(),"First get JSON",Toast.LENGTH_SHORT).show();
            }else {
                bundle.putString("json data", json_string);
            }
            // set Fragmentclass Arguments
            //DashboardFragment fragobj = new DashboardFragment();
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.dash_main, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class BackgroundTask extends AsyncTask<Void,Void,String> {



        String json_file;

        @Override
        protected void onPreExecute() {
            json_file = "http://www.surveyor-for-you.co.uk/Android/getProperties.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(json_file);
                HttpURLConnection httpurlcon = (HttpURLConnection) url.openConnection();
                InputStream streaminput = httpurlcon.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streaminput));
                StringBuilder stringBuilder = new StringBuilder();
                //String line;

                while ((JSON_STRING = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_STRING+"\n");
                    //Thread.sleep(500);
                }


                bufferedReader.close();
                streaminput.close();
                httpurlcon.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            //TextView txTextView = (TextView)findViewById(R.id.jsontext);
            //txTextView.setText(s);
            json_string = s;
        }

        public void parseJSON(View view){

            if(json_string == null){
                Toast.makeText(getApplicationContext(),"First get JSON",Toast.LENGTH_SHORT).show();
            }

        }


    }


}


