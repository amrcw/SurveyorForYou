package uk.co.surveyorforyou.surveyorforyou;

import android.content.Intent;

import android.media.MediaCodec;
import android.nfc.Tag;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {


    private EditText rgFname, rgLname, rgEmail,rgUser, rgPass, rgConfirmpass, rgAddress, rgPostcode, rgContact;
    private TextInputLayout inputLayoutFirstname,inputLayoutLastname, inputLayoutEmail, inputLayoutUsername, inputLayoutPassword, inputLayoutConfirmpassword, inputLayoutAddress, inputLayoutPostcode, inputLayoutContact;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputLayoutFirstname = (TextInputLayout) findViewById(R.id.input_layout_firstname);
        inputLayoutLastname = (TextInputLayout) findViewById(R.id.input_layout_lastname);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutUsername = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmpassword = (TextInputLayout) findViewById(R.id.input_layout_passwordConfirm);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_layout_address);
        inputLayoutPostcode = (TextInputLayout) findViewById(R.id.input_layout_postcode);
        inputLayoutContact = (TextInputLayout) findViewById(R.id.input_layout_contact);

        rgFname = (EditText)findViewById(R.id.regFirstname);
        rgLname = (EditText)findViewById(R.id.regLastname);
        rgEmail = (EditText)findViewById(R.id.regEmail);
        rgUser = (EditText)findViewById(R.id.regUsername);
        rgPass = (EditText)findViewById(R.id.regPassword);
        rgContact = (EditText)findViewById(R.id.regContact);
        rgConfirmpass = (EditText)findViewById(R.id.regConfirmpassword);
        rgAddress = (EditText)findViewById(R.id.regAddress);
        rgPostcode = (EditText)findViewById(R.id.regPostcode);
        register = (Button)findViewById(R.id.btnReg);


        rgFname.addTextChangedListener(new MyTextWatcher(rgFname));
        rgEmail.addTextChangedListener(new MyTextWatcher(rgEmail));
        rgPass.addTextChangedListener(new MyTextWatcher(rgPass));
        rgContact.addTextChangedListener(new MyTextWatcher(rgContact));



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(RegisterActivity.this,UploadImage.class);
                //RegisterActivity.this.startActivity(intent);

                submitForm();

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

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validPhone()){
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }


    private boolean validateName() {
        if (rgFname.getText().toString().trim().isEmpty()) {
            inputLayoutFirstname.setError(getString(R.string.err_msg_name));
            requestFocus(rgFname);
            return false;
        } else {
            inputLayoutFirstname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = rgEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(rgEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }



    private boolean validatePassword() {
        if (rgPass.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(rgPass);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validPhone()
    {
        String phone = rgContact.getText().toString().trim();
        String expression = "^[0-9]{10,11}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (!matcher.matches())
        {

            inputLayoutContact.setError(getString(R.string.err_msg_contact));
            requestFocus(rgContact);
            return false;
        }
        else{
            inputLayoutContact.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }




    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.regFirstname:
                    validateName();
                    break;
                case R.id.regEmail:
                    validateEmail();
                    break;
                case R.id.regPassword:
                    validatePassword();
                    break;
                case R.id.regContact:
                    validPhone();
                    break;
            }
        }
    }

}
