package uk.co.surveyorforyou.surveyorforyou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText rgname = (EditText)findViewById(R.id.regName);
        final EditText rgemail = (EditText)findViewById(R.id.regEmail);
        final EditText rguser = (EditText)findViewById(R.id.regUsername);
        final EditText rgpass = (EditText)findViewById(R.id.regPass);
        final Button login = (Button)findViewById(R.id.btnReg);
    }
}
