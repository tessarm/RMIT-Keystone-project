package dmcjj.rmitpp.toiletlocator.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.Database;
import dmcjj.rmitpp.toiletlocator.server_model.LoginMeta;


/**
 * Created by derrickphung on 10/8/17.
 */

public class Login extends AppCompatActivity
{
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null)
            login(currentUser);
        //setView
        setContentView(R.layout.jason_login);
        ButterKnife.bind(this);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterHere);
        final Button bGuest = (Button) findViewById(R.id.bGuest);

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
    private void login(FirebaseUser user){
        Intent userAreaIntent = new Intent(Login.this, MapsActivity.class);
        startActivity(userAreaIntent);

        LoginMeta loginMeta = new LoginMeta(user);
        Database.putLogin(loginMeta);
    }
    //CODE TO AUTHENTICATE USER
    private void authenticateUser()
    {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Task<AuthResult> r = FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password);
        r.addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user  != null)
                        login(user);
                }
                else{
                    etPassword.setError("Incorrect Password/Email");
                }
            }
        });

    }
}
