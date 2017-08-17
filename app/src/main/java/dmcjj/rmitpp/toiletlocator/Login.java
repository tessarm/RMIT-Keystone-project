package dmcjj.rmitpp.toiletlocator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by derrickphung on 10/8/17.
 */

public class Login extends AppCompatActivity
{

//    public ArrayList<String> usernames = new ArrayList<String>();
//    public ArrayList<String> passwords = new ArrayList<String>();
//    public ArrayList<String> emails = new ArrayList<String>();
//    public ArrayList<String> names = new ArrayList<String>();
//
//    ArrayList getList(ArrayList list)
//    {
//        return list;
//    }
    int attempts = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
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
                if(etUsername.getText().toString().equals("Admin") && etPassword.getText().toString().equals("password")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent userAreaIntent = new Intent(Login.this, UserAreaActivity.class);
                    Login.this.startActivity(userAreaIntent);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong username or password",Toast.LENGTH_SHORT).show();

//                    tx1.setVisibility(View.VISIBLE);
//                    tx1.setBackgroundColor(Color.RED);
//                    attempts--;
//                    tx1.setText(Integer.toString(attempts));
//
//                    if (attempts == 0) {
//                        bLogin.setEnabled(false);
//                    }
                }
            }
        });
    }
}
