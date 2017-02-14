package uk.co.surveyorforyou.surveyorforyou;

import android.content.Intent;

import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;



public class RegisterActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText rgFname = (EditText)findViewById(R.id.regFirstname);
        final EditText rgLname = (EditText)findViewById(R.id.regLastname);
        final EditText rgEmail = (EditText)findViewById(R.id.regEmail);
        final EditText rgUser = (EditText)findViewById(R.id.regUsername);
        final EditText rgPass = (EditText)findViewById(R.id.regPassword);
        final EditText rgContact = (EditText)findViewById(R.id.regContact);
        final EditText rgConfirmpass = (EditText)findViewById(R.id.regConfirmpassword);
        final EditText rgAddress = (EditText)findViewById(R.id.regAddress);
        final EditText rgPostcode = (EditText)findViewById(R.id.regPostcode);
        final Button register = (Button)findViewById(R.id.btnReg);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(RegisterActivity.this,UploadImage.class);
                //RegisterActivity.this.startActivity(intent);

                final  String firstname = rgFname.getText().toString();
                final  String lastname = rgLname.getText().toString();
                final  String email = rgEmail.getText().toString();
                final  String contact = rgContact.getText().toString();
                final  String username = rgUser.getText().toString();
                final  String password = rgPass.getText().toString();
                final  String address = rgAddress.getText().toString();
                final  String postcode = rgPostcode.getText().toString();

                Intent i = new Intent(RegisterActivity.this,UploadImage.class);

                i.putExtra("firstname",firstname);
                i.putExtra("lastname",lastname);
                i.putExtra("email",email);
                i.putExtra("contact",contact);
                i.putExtra("username",username);
                i.putExtra("password",password);
                i.putExtra("address",address);
                i.putExtra("postcode",postcode);


                startActivity(i);





                /*Log.d("hi",address);
                Response.Listener<String> responseListner = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                            if(success){
                               // Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                //RegisterActivity.this.startActivity(intent);
                                Intent intent = new Intent(RegisterActivity.this,UploadImage.class);
                                RegisterActivity.this.startActivity(intent);


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest rgRequest = new RegisterRequest(firstname,lastname,email,username,password,address,postcode,responseListner);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(rgRequest);

*/

            }
        });



    }



}
