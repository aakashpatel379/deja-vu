package com.developer.dejavu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class PlayerActivity extends AppCompatActivity {


    TextView textView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String TAG ="PlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        textView=findViewById(R.id.text);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String str ="";
        str+=user.getDisplayName().toString();

//        str+="\n"+user.getPhoneNumber().toString();
        str+="\n"+user.getUid().toString();
        str+="\n"+user.getProviderId().toString();
        textView.setText(str);
        Log.d(TAG, "displayname:"+user.getDisplayName());
//        Log.d(TAG, "phn:"+user.getPhoneNumber());
        Log.d(TAG, "UID:"+user.getUid());
        Log.d(TAG, "provider:"+user.getProviderId());


        //mDatabase = FirebaseDatabase.getInstance().getReference();

    }
}
