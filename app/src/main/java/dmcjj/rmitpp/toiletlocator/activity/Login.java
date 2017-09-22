package dmcjj.rmitpp.toiletlocator.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

//import com.twitter.sdk.android.Twitter;
// Don't know why the above import chucks a hissy fit
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


/**
 * Created by derrickphung on 10/8/17.
 */



public class Login extends AppCompatActivity
{
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;

    private TwitterLoginButton mLoginButton;

    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
                "IoObxTkmQvAwTLWA6fNOcfvPd",
                "xmU0ATdXfBRh06Zpjn2XRa4F2BzjGnknsWyrTHbFSRW5XzP3lE");

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);


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


        mLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d("login", "twitterLogin:success" + result);
               // handleTwitterSession(result.data);
                FirebaseUser twitterUser = FirebaseAuth.getInstance().getCurrentUser();
                login(twitterUser);
        }

            @Override
            public void failure(TwitterException exception) {
                Log.w("login", "twitterLogin:failure", exception);
                updateUI(null);
            }
        });

    }

    private void handleTwitterSession(TwitterSession data) {

    }

    private void updateUI(Object o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
        mLoginButton.onActivityResult(requestCode, resultCode, data);
// above is code from the firebase/twitter documentation - need help
    }

//    private void handleTwitterSession(TwitterSession session) {
//        Log.d(TAG, "handleTwitterSession:" + session);
//
//        AuthCredential credential = TwitterAuthProvider.getCredential(
//                session.getAuthToken().token,
//                session.getAuthToken().secret);
//
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(TwitterLoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }

    // above is code from the firebase/twitter documentation - need help

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
