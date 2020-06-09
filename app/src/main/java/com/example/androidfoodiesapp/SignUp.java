package com.example.androidfoodiesapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidfoodiesapp.Data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    EditText edtDalID,edtPassword,edtName;
    Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtDalID=(EditText)findViewById(R.id.edtDalID);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtName=(EditText)findViewById(R.id.edtName);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);


        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference user=database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this );
                mDialog.setMessage("Loading...");
                mDialog.show();

                user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //to check if the user is already there
                        if(dataSnapshot.child(edtDalID.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "You are already registered!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();
                            User user1= new User(edtName.getText().toString(),edtPassword.getText().toString());
                            user.child(edtDalID.getText().toString()).setValue(user1);
                            Toast.makeText(SignUp.this, "Account Created!!", Toast.LENGTH_SHORT).show();
                            finish();

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
