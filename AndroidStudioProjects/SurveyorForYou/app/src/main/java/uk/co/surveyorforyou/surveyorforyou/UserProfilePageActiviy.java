package uk.co.surveyorforyou.surveyorforyou;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;



public class UserProfilePageActiviy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_page_activiy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Dashboard");


        final TextView txtfirstname = (TextView)findViewById(R.id.txtNameTitle);
        final TextView txtEmail = (TextView)findViewById(R.id.txtProfEmail);
        final TextView imgPath = (TextView)findViewById(R.id.path);
        final ImageView image = (ImageView)findViewById(R.id.imgProfile);
        final TextView userEmail = (TextView)findViewById(R.id.userEmail);



        Intent intent = getIntent();
        String fistname = intent.getStringExtra("firstname");
        String lastname = intent.getStringExtra("lastname");
        String emailadd = intent.getStringExtra("email");
        String imagePath = intent.getStringExtra("photo_id");

       // Log.d("Image",imagePath);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //initImageLoader(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        //ImageView imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.userImage);
        //ImageLoader.getInstance().displayImage(imagePath, imageView);
       // View header = navigationView.getHeaderView(0);
       // imageView = (ImageView) header.findViewById(R.id.imageView);

        Glide.with(UserProfilePageActiviy.this)
                .load(imagePath)
                .into(image);


       //Picasso.with(UserProfilePageActiviy.this).load(imagePath).into(userHeaderImage);

        txtfirstname.setText(fistname+" "+lastname);
        txtEmail.setText(emailadd);
        imgPath.setText(imagePath);
        //userEmail.setText(emailadd);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile_page_activiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboad) {

           // DashboardFragment dashfrag = DashboardFragment.newInstance("Hello", "Ruwan");
            //FragmentManager manager = getSupportFragmentManager();
           // manager.beginTransaction().replace(R.id.content_user_profile_page_activiy,dashfrag,dashfrag.getTag()).commit();

        }else if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
