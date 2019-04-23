package com.companyname.yash.usersidereceipto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {
    //AUTHENTICATION:
    private FirebaseAuth mAuth;
    //field entries:
    public EditText emailAddressDetails;
    public EditText userId;
    public TextView userTotalCost;
    public Button refresh;
    public Button logOut;
    public Button allReceipts;
    public  final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        //declarations:
        emailAddressDetails=findViewById(R.id.userEmailAddress);
        userId=findViewById(R.id.userId);
        userTotalCost=findViewById(R.id.userTotalCost);
        refresh=findViewById(R.id.refresh);
        logOut=findViewById(R.id.logOut);
        allReceipts=findViewById(R.id.allReceipts);


        //handling mAuth

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userId.setText(user.getUid());
        Log.d("ASDASDSADAD",user.getEmail());
        emailAddressDetails.setText(user.getEmail());


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserCost("-"+user.getUid());
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutAccount();
            }
        });


        allReceipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we go to the next page from here!
                Intent i=new Intent(getApplicationContext(),allReceiptsPage.class);
                startActivity(i);


            }
        });





        Toast.makeText(getApplicationContext(), "Hello user!"+user.getEmail()+"\n"+user.getUid(),
                Toast.LENGTH_SHORT).show();


    }



    public void getUserCost(final String userId){

        DatabaseReference mDatabase=database.getReference();


        //generating a unique value by pushing into the receipts
        mDatabase.child("users").child(userId).child("settingNull").setValue("");//create a new user with username defined here. and values in user class





//LISTENING FOR CHANGES IN REEIPT VALUE IN DATABASE AFTER VALUE ADDITION
        ValueEventListener userListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //receipt rNew=dataSnapshot.getValue(receipt.class);
                user uNew=dataSnapshot.getValue(user.class);

                //String [] receiptSet =uNew.receipts.keySet().toArray(new String[0]);//all user receipts are here!
                //receiptAdder(receiptSet);
                //receiptAll=receiptSet;




                //DISPLAYING DIRECTLY TO THE SCREEN:

//                String rx=uNew.receipts.keySet().toString();
/*
               databaseValue.setText(rx);
  */

                //NOW GETTING DATA OF EACH RECEIPT!!
               /* String total="";
                rx=rx.substring(1,rx.length()-2);

                String[] allReceipts=rx.split(",");
                //allReceipts[0]=allReceipts[0].substring(1);

                for(String x:allReceipts){
                    Log.d("JDASIJSASJAIDAJISAJD",x);
                    getReceiptValues(x);
                    total+=x;
                }*/
               Double costVal=uNew.total;
                userTotalCost.setText(costVal.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        database.getReference().child("users").child(userId).addValueEventListener(userListener);



    }


    private void signOutAccount(){
        FirebaseAuth.getInstance().signOut();
        //databaseValue.setText("goodbye!");
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }



}
