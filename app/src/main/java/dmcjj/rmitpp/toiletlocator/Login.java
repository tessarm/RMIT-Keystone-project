package dmcjj.rmitpp.toiletlocator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.logging.LogRecord;

import dmcjj.rmitpp.toiletlocator.developer.activity.DevToolsActivity;
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


            //Task<AuthResult> t = FirebaseAuth.getInstance().createUserWithEmailAndPassword("", "");



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
    private void login(){
        Intent userAreaIntent = new Intent(Login.this, UserAreaActivity.class);
        startActivity(userAreaIntent);
    }
    private void loginDevTools(){
        Intent devTools = new Intent(Login.this, DevToolsActivity.class);
        startActivity(devTools);
    }


    //CODE TO AUTHENTICATE USER
    private void authenticateUser()
    {
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        FirebaseAuth mAuth;



        Task<AuthResult> r = FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password);
        r.addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.getEmail().contentEquals(Security.EMAIL_SUPERUSER))
                        loginDevTools();
                    else
                        login();
                }
                else{
                    etPassword.setError("Incorrect Password/Email");
                }


            }
        });

    }
}
