package com.example.androidfoodiesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidfoodiesapp.Common.Common;

import com.example.androidfoodiesapp.Data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText edtDalID,edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtDalID=(EditText)findViewById(R.id.edtDalID);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);


        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference user=database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this );
                mDialog.setMessage("Loading...");
                mDialog.show();

                user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if user exist
                        if (dataSnapshot.child(edtDalID.getText().toString()).exists()) {


                            //get data from database
                            mDialog.dismiss();

                            User user = dataSnapshot.child(edtDalID.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Intent homeIntent = new Intent(SignIn.this, home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();



                            } else {

                                Toast.makeText(SignIn.this, "Invalid Password", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "Wrong Dal ID", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
