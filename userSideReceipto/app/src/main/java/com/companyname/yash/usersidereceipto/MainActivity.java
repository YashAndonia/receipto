package com.companyname.yash.usersidereceipto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //debugging:

    private static final String TAG = "error messagfe:";

    //field entries:
    public EditText emailAddress;
    public EditText password;
    public Button submit;
    public Button register;


    //AUTHENTICATION:
    private FirebaseAuth mAuth;

    public final FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        register = findViewById(R.id.register);


// Initialize Firebase Auth

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInAccount(emailAddress.getText().toString(),password.getText().toString());
                //logInAccount("yash2@yash.com", "hello1234");//test credentials: yash2@yash.com,password:hello1234
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signin page reference
                // Intent i= new Intent(getApplicationContext(),)
                createAccount(emailAddress.getText().toString(),password.getText().toString());
            }
        });
    }


    //Signing in an eisting user:
    private void logInAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
/*
                            Toast.makeText(getApplicationContext(), "Hello user!"+user.getEmail()+"\n"+user.getUid(),
                                    Toast.LENGTH_SHORT).show();
*/


                            //updateUI(user);
                            //from here we go to the next page

                            Intent i = new Intent(getApplicationContext(), ProfilePage.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed. Please create an account!",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //mStatusTextView.setText(R.string.auth_failed);
                        }
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    //to register a new user!

    public void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser userAuth = mAuth.getCurrentUser();
                            //createNewUser();
                            //user u=new user();
                            user u = new user(userAuth.getEmail(), "user");
                            //userAuth.updateProfile();


                            //adding this user to the database
                            DatabaseReference mDatabase = database.getReference();
                            mDatabase.child("users").child(userAuth.getUid()).setValue(u);//create a new user with username defined here. and values in user class
                            //databaseValue.setText(userAuth.getUid());
                            Toast.makeText(getApplicationContext(), "Registered!",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), ProfilePage.class);
                            startActivity(i);


                            //updateUI(userAuth);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                            //from here we go to the next page



                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
// [END create_user_with_email]


    }
}