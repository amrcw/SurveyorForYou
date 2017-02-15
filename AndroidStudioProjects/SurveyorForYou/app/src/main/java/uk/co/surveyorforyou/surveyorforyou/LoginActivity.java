package uk.co.surveyorforyou.surveyorforyou;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       // final EditText lgname = (EditText)findViewById(R.id.nameLogin);
        //final EditText lgpass = (EditText)findViewById(R.id.passLogin);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");

        final Button login = (Button)findViewById(R.id.btn);
        //final Button login = (Button)findViewById(R.id.btnLogin);
        //final TextView linkForget = (TextView)findViewById(R.id.linkForgetton);
        final Button newAccount = (Button)findViewById(R.id.btnCreateAccount);

       // View view = this.getCurrentFocus();
       // if (view != null) {
        //    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //    imm.hideSoftInputFromWindow(lgname.getWindowToken(), 0);
       // }


        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                hideKeyboard();

                String username = usernameWrapper.getEditText().getText().toString();
                String password = usernameWrapper.getEditText().getText().toString();
                if (!validateUsername(username)) {
                    usernameWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    //doLogin();
                //}

                // final String username = lgname.getText().toString();
                //final String password = lgpass.getText().toString();

                Log.d("username", username);
                Log.d("pass", password);

                // Intent intent = new Intent(LoginActivity.this,UserProfilePageActiviy.class);
                // LoginActivity.this.startActivity(intent);


                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                //  Intent intent = new Intent(LoginActivity.this,UserProfilePageActiviy.class);
                                //  LoginActivity.this.startActivity(intent);

                                String firstname = jsonResponse.getString("firstname");
                                firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase();
                                String lastname = jsonResponse.getString("lastname");
                                lastname = lastname.substring(0, 1).toUpperCase() + lastname.substring(1).toLowerCase();
                                String email = jsonResponse.getString("email");
                                String contact = jsonResponse.getString("contact_number");
                                String address = jsonResponse.getString("address");
                                String postcode = jsonResponse.getString("postcode");
                                postcode = postcode.toUpperCase();
                                String photoId = jsonResponse.getString("photo_id");

                                Intent intent = new Intent(LoginActivity.this, UserDashboard.class);
                                intent.putExtra("firstname", firstname);
                                intent.putExtra("lastname", lastname);
                                intent.putExtra("email", email);
                                intent.putExtra("contact", contact);
                                intent.putExtra("address", address);
                                intent.putExtra("postcode", postcode);
                                intent.putExtra("photo_id", photoId);

                                LoginActivity.this.startActivity(intent);
                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Request Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }


                };




                LoginRequest lgRequest = new LoginRequest(username,password,responseListner);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(lgRequest);

            }
            }

            private void hideKeyboard() {
                View view = getCurrentFocus();
                if (view != null) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            public boolean validatePassword(String password) {
                return password.length() > 3;
            }

            public boolean validateUsername(String username) {
                return username.length() > 3;
            }


        });




    }
}
