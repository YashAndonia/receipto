package com.companyname.yash.shopsidereceipto;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //field entries:
    public EditText companyName;
    public EditText receiptId;
    public EditText cost;
    public EditText customerId;
    public TextView databaseValue;
    public EditText items;
    public Button submit;
    public static String recentlyAddedReceipt;
    public  final FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        companyName=findViewById(R.id.companyName);
        cost=findViewById(R.id.cost);
        customerId=findViewById(R.id.customerId);
        items=findViewById(R.id.items);
        submit=findViewById(R.id.submit);
        databaseValue=findViewById(R.id.databaseValue);
        recentlyAddedReceipt="";

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createNewReceipt(companyName.getText().toString(),1,Integer.parseInt(cost.getText().toString()),"-"+customerId.getText().toString(),items.getText().toString());
                //getReceiptValues("-LbxAdYxqIFV_U8f5VNb");

                Log.d("ASDASDASDA",recentlyAddedReceipt);
                getReceiptValues(recentlyAddedReceipt);

            }
        });


    }





    public void createNewReceipt(String company, int receiptId, final int cost, String customerId, String items){
        receipt r=new receipt(cost,customerId,items,company);

        //setting the reference for this class and at what level the changes are supposed tpo be made.



        //to only push the values of the receipt we do this

        DatabaseReference mDatabase=database.getReference();

        //ENTERING THE COMPANY VALUES AS WELL
        String receiptKey=mDatabase.child("receipts").push().getKey();
        recentlyAddedReceipt=receiptKey;


        //adding in the company details as well:

        mDatabase.child("companies").child(company).child(receiptKey).setValue(r);//create a new user with username defined here. and values in user class


        mDatabase.child("companies").child(company).child(receiptKey).child("idVal").setValue(receiptKey);//setting the receipt value as well!

        mDatabase.child("receipts").child(receiptKey).setValue(r);

        mDatabase.child("receipts").child(receiptKey).child("idVal").setValue(receiptKey);






        mDatabase.child("users").child(customerId).child("receipts").child(receiptKey).setValue(true);

        //double userTotal=mDatabase.child("users").child(Integer.toString(customerId)).get










        //ADDING THE VALUE TO THE USER AS WELL!
        transactionMethod(mDatabase.child("users").child(customerId),r,receiptKey);









//LISTENING FOR CHANGES IN REEIPT VALUE IN DATABASE AFTER VALUE ADDITION
        ValueEventListener receiptListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                receipt rNew=dataSnapshot.getValue(receipt.class);
                //user uNew=dataSnapshot.getValue(user.class);



                // databaseValue.setText("\nYou just bought:"+rNew.getItems()+"\nyour total expenditure is:"+rNew.getCost()+"\n your custormerID is"+rNew.getCustomerId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        //  database.getReference().child("receipts").child(company).child(Integer.toString(receiptId)).addValueEventListener(receiptListener);

//FOR CHANGES IN USER AS WELL:





//LISTENING FOR CHANGES IN REEIPT VALUE IN DATABASE AFTER VALUE ADDITION



        ValueEventListener userListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                user uNew=dataSnapshot.getValue(user.class);
                //String x=uNew.getName();
                //databaseValue.setText(x);
                // databaseValue.setText(uNew.name);
                String y=uNew.keyReader();
                databaseValue.setText(y);

                //databaseValue.setText("\nYou just bought:"+rNew.getItems()+"\nyour total expenditure is:"+rNew.getCost()+"\n your custormerID is"+rNew.getCustomerId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        database.getReference().child("users").child(customerId).addValueEventListener(userListener);






        //THIS PART WILL BE THE UPDATION OF THE CUSTOMER DETAILS AS WELL< DURING THE ADDITION OF A RECEIPT!
        //OKAY, SO ATUALLY THIS ONLY WORKS IF YOU WANT TO POST THE ENTIRE CLASS INTO TE OTHER SUB CHILD AS WELL. DOESNT REALLY HELP NOW, DOES IT.

/*
        DatabaseReference mDatabase=database.getReference();
        String keyForReceiptUpdate=mDatabase.child("/receipts/").child(Integer.toString(receiptId)).getKey();// to use the generated key

        String keyForUserUpdate=mDatabase.child("/users/").child(Integer.toString(customerId)).child("total").getKey();

        Map submittedReceiptValues=r.toMap();
        Map allUpdates=new HashMap();
        allUpdates.put(keyForReceiptUpdate,submittedReceiptValues);
        allUpdates.put(keyForUserUpdate,submittedReceiptValues.get("cost"));



        mDatabase.updateChildren(allUpdates);
*/

    }



    public void transactionMethod(DatabaseReference postRef, final receipt r, final String receiptKey){
        postRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                user u=mutableData.getValue(user.class);
                if(u==null){
                    //this user does not exist at all!
                }
                else {
                    u.total+=r.cost;
                    u.receipts.put(receiptKey,true);
                }

                mutableData.setValue(u);
                return Transaction.success(mutableData);


            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                Log.d("This is the tag","the dadat is "+databaseError);
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

/*
                displllay+="\n\n\nYou just bought:"+rNew.getItems()+"\nyour total expenditure is:"+rNew.getCost()+"\n your custormerID is"+rNew.getCustomerId()+"\n";
                Log.d("ASDDSADSADAS",displllay);*/
databaseValue.setText("");
                databaseValue.setText("\n\n\nReceipt ID:"+recentlyAddedReceipt+"\nReceipt items:"+rNew.getItems()+"\nRecipt Total Cost:"+rNew.getCost()+"\nCustomerID:"+rNew.getCustomerId()+"\n");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        database.getReference().child("receipts").child(receiptId).addValueEventListener(receiptListener);



    }

}
