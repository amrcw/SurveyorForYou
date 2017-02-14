package uk.co.surveyorforyou.surveyorforyou;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserDashboard extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        setTitle("User Dashboard");

        mToolbar = (Toolbar)findViewById(R.id.nav_action_toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.nav_drawer_open,R.string.nav_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ImageView userProf = (ImageView)findViewById(R.id.imgProfile);
        final TextView dashName = (TextView)findViewById(R.id.dashName);
        final TextView dashEmail = (TextView)findViewById(R.id.dashEmail);
        final TextView dashAdd = (TextView)findViewById(R.id.dashAdd);
        final TextView dashPostcode = (TextView)findViewById(R.id.dashPostcode);
        final TextView dashContact = (TextView)findViewById(R.id.dashContact);



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



        Glide.with(UserDashboard.this).load(photo_path).into(userProf);
        dashName.setText(firstname+ " " +lastname);
        dashAdd.setText(address);
        dashPostcode.setText(postcode);
        dashEmail.setText(email);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
