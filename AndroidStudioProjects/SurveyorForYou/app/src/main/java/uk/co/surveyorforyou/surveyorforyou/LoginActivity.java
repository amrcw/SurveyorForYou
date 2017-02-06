package uk.co.surveyorforyou.surveyorforyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
}
