package dmcjj.rmitpp.toiletlocator.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.Database;
import dmcjj.rmitpp.toiletlocator.view.ImageAdapter;

public class RegisterActivity extends AppCompatActivity
{

    private ImageAdapter imageAdapter;

    private EditText etEmail;
    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    //private Button bRegister;


    private OnCompleteListener<Void> emailResult = new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){

            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.actDone:{
                registerUser();
            }break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageAdapter = new ImageAdapter();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);




    }

    private void registerUser(){
        final String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        final String username = etUsername.getText().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();



                    Database.getUserRef().child("profile").child("email").setValue(email);



                    Intent createUserIntent = new Intent(RegisterActivity.this, UserAreaActivity.class);
                    RegisterActivity.this.startActivity(createUserIntent);
                }
                else{
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    etEmail.setError(e.getMessage());
                }
            }
        });



    }
}
