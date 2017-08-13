package dmcjj.rmitpp.toiletlocator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

import dmcjj.rmitpp.toiletlocator.security.LocalAuthorizer;
import dmcjj.rmitpp.toiletlocator.security.LoginAuthorizer;
import dmcjj.rmitpp.toiletlocator.security.TestAuthorizer;

/**
 * Created by derrickphung on 10/8/17.
 */

public class Login extends Activity
{
    private EditText username;
    private EditText password;

    private LoginAuthorizer authorizer = new LocalAuthorizer();

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

    }
    public void login(View view){
            String userText = username.getText().toString();
            String passText = password.getText().toString();





        if(authorizer.isAdmin(this, userText, passText)){
            Intent adminIntent = new Intent(this, AdminActivity.class);
            startActivity(adminIntent);
        }
        else if(authorizer.authorizeUser(this, userText, passText))
        {

            //do somthing when user
        }
    }
}
