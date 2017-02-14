package uk.co.surveyorforyou.surveyorforyou;

import android.content.Context;
import android.content.Intent;
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



        final EditText lgname = (EditText)findViewById(R.id.nameLogin);
        final EditText lgpass = (EditText)findViewById(R.id.passLogin);
        final Button login = (Button)findViewById(R.id.btnLogin);
        final TextView linkForget = (TextView)findViewById(R.id.linkForgetton);
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

                final String username = lgname.getText().toString();
                final String password = lgpass.getText().toString();

                Log.d("username",username);
                Log.d("pass",password);

               // Intent intent = new Intent(LoginActivity.this,UserProfilePageActiviy.class);
               // LoginActivity.this.startActivity(intent);

                Response.Listener<String> responseListner = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                              //  Intent intent = new Intent(LoginActivity.this,UserProfilePageActiviy.class);
                              //  LoginActivity.this.startActivity(intent);

                                String firstname = jsonResponse.getString("firstname");
                                String lastname = jsonResponse.getString("lastname");
                                String email = jsonResponse.getString("email");
                                String address = jsonResponse.getString("address");
                                String postcode = jsonResponse.getString("postcode");
                                String photoId = jsonResponse.getString("photo_id");

                                Intent intent = new Intent(LoginActivity.this,UserDashboard.class);
                                intent.putExtra("firstname",firstname);
                                intent.putExtra("lastname",lastname);
                                intent.putExtra("email",email);
                                intent.putExtra("address",address);
                                intent.putExtra("postcode",postcode);
                                intent.putExtra("photo_id",photoId);

                                LoginActivity.this.startActivity(intent);
                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Request Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }


                };


                LoginRequest lgRequest = new LoginRequest(username,password,responseListner);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(lgRequest);

            }


        });
    }
}
