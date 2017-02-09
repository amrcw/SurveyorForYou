package uk.co.surveyorforyou.surveyorforyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserProfileAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_acivity);

        final EditText fname = (EditText)findViewById(R.id.txtFname);
        final EditText email  = (EditText)findViewById(R.id.txtEmail);
        final TextView welcome = (TextView)findViewById(R.id.txtWelcome);

        Intent intent = getIntent();
        String firstname = intent.getStringExtra("firstname");
        String emailadd = intent.getStringExtra("email");

        String messsage = "Welcome to your profile"+ firstname;
        welcome.setText(messsage);
        fname.setText(firstname);
        email.setText(emailadd);
    }
}
