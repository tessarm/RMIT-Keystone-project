package dmcjj.rmitpp.toiletlocator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.LogRecord;

import dmcjj.rmitpp.toiletlocator.security.LoginAuthorizer;
import dmcjj.rmitpp.toiletlocator.security.Security;
import dmcjj.rmitpp.toiletlocator.security.UserInfo;


/**
 * Created by derrickphung on 10/8/17.
 */

public class Login extends AppCompatActivity
{
    private EditText etUsername;
    private EditText etPassword;

    int attempts = 3;



    private LoginAuthorizer.Callbacks loginCallbacks = new LoginAuthorizer.Callbacks() {
        @Override
        public void OnUserAuthorized(UserInfo userInfo) {
            Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
            Intent userAreaIntent = new Intent(Login.this, UserAreaActivity.class);
            Login.this.startActivity(userAreaIntent);
        }

        @Override
        public void OnUserDenied() {
            Toast.makeText(getApplicationContext(), "Wrong username or password",Toast.LENGTH_SHORT).show();
            etPassword.setError("Incorrect");
        }
    };
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jason_login);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterHere);
        final Button bGuest = (Button) findViewById(R.id.bGuest);
//        final TextView tx1 = (TextView)findViewById(R.id.textView3);
//        tx1.setVisibility(View.GONE);


        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, RegisterActivity.class);
                Login.this.startActivity(registerIntent);
            }
        });

        bGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guestIntent = new Intent(Login.this, MapsActivity.class);
                Login.this.startActivity(guestIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            authenticateUser();
            }
        });
    }


    //CODE TO AUTHENTICATE USER
    private void authenticateUser()
    {
        Security security = Security.get();
        security.getLoginAuthorizer().authorizeUser(this, etUsername.getText().toString(),
                etPassword.getText().toString(), loginCallbacks);
    }
}
