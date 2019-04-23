package com.companyname.yash.usersidereceipto;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class allReceiptsPage extends Activity {

    //AUTHENTICATION:
    private FirebaseAuth mAuth;
    //field entries:
    public static String displllay="";
    public Button updater;
    public TextView allReceiptVals;
    public  final FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_receipts_page);


        //handling mAuth

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        allReceiptVals=findViewById(R.id.allReceiptVals);
        updater=findViewById(R.id.updateButton);




        updater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUserReceipts("-"+user.getUid());//this takes the customer id, and gets all receipts of the person and displays details of every single RECEIPT!!!!
                allReceiptVals.setText(displllay);
               // displllay="";
            }
        });
    }










    public void getReceiptValues(String receiptId){

        DatabaseReference mDatabase=database.getReference();


        //generating a unique value by pushing into the receipts
        mDatabase.child("receipts").child(receiptId).child("settingNull").setValue("");//create a new user with username defined here. and values in user class






//LISTENING FOR CHANGES IN REEIPT VALUE IN DATABASE AFTER VALUE ADDITION
        ValueEventListener receiptListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                receipt rNew=dataSnapshot.getValue(receipt.class);
                //user uNew=dataSnapshot.getValue(user.class);


                displllay+="\n\n\nYou just bought:"+rNew.getItems()+"\nyour total expenditure is:"+rNew.getCost()+"\n your custormerID is"+rNew.getCustomerId()+"\nYour Receipt id is :"+rNew.idVal+"\n";
                Log.d("ASDDSADSADAS",displllay);
                //databaseValue.setText("\n\n\nYou just bought:"+rNew.getItems()+"\nyour total expenditure is:"+rNew.getCost()+"\n your custormerID is"+rNew.getCustomerId()+"\n");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        database.getReference().child("receipts").child(receiptId).addValueEventListener(receiptListener);



    }





    public void getUserReceipts(final String userId){

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

                String rx=uNew.receipts.keySet().toString();
/*
               databaseValue.setText(rx);
  */

                //NOW GETTING DATA OF EACH RECEIPT!!
                String total="";
                if(rx.length()>2){
                rx=rx.substring(1,rx.length()-1);
                }
                else{
                    rx="";
                }

                String[] allReceipts=rx.split(",");
                //allReceipts[0]=allReceipts[0].substring(1);
                Log.d("asdwdasqweqweqw",rx);

                for(String x:allReceipts){
                    x=x.replaceAll("\\s+","");

                    Log.d("JDASIJSASJAIDAJISAJD",x);
                    getReceiptValues(x);
                    total+=x;
                }
                allReceiptVals.setText(displllay);

                displllay="";

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        database.getReference().child("users").child(userId).addValueEventListener(userListener);



    }

}
