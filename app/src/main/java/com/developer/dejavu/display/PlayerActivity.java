package com.developer.dejavu.display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.dejavu.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class PlayerActivity extends AppCompatActivity  implements View.OnClickListener {

    TextView tvDashboardTitle;
    Button btnSinglePlayer,btnTwoPlayer, btnHelp, btnSignOut, btnLeaderboard, btnAvatar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String TAG ="PlayerActivity";
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        tvDashboardTitle=findViewById(R.id.tv_dashboard_title);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        btnAvatar=findViewById(R.id.btn_avatar);
        btnSinglePlayer=findViewById(R.id.btn_single_player);
        btnLeaderboard=findViewById(R.id.btn_leaderboard);
        btnSignOut=findViewById(R.id.btn_dashboard_signout);
        btnHelp=findViewById(R.id.btn_dashboard_help);
        btnTwoPlayer=findViewById(R.id.btn_two_player);

        btnAvatar.setOnClickListener(this);
        btnSinglePlayer.setOnClickListener(this);
        btnLeaderboard.setOnClickListener(this);
        btnTwoPlayer.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("808106934129-53qft8hm7j3utlasrvc808105an63dee.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);

//        Typeface fontPacifico = Typeface.createFromAsset(getAssets(),  "fonts/DancingScript-Regular.otf");
//        tvDashboardTitle.setTypeface(fontPacifico);
//
//        btnAvatar.setTypeface(fontPacifico);
//        btnSinglePlayer.setTypeface(fontPacifico);
//        btnLeaderboard.setTypeface(fontPacifico);
//        btnTwoPlayer.setTypeface(fontPacifico);
//        btnHelp.setTypeface(fontPacifico);
//        btnSignOut.setTypeface(fontPacifico);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_avatar:

                break;
            case R.id.btn_single_player:
                break;
            case R.id.btn_dashboard_help:
                helpDialog();
                break;
            case R.id.btn_leaderboard:
                Toast.makeText(PlayerActivity.this,"Not implemented yet!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_dashboard_signout:
                mAuth.signOut();
                googleSignOut();
                break;
        }
    }

    private void googleSignOut() {

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(PlayerActivity.this, HomeActivity.class));
                    }
                });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quit")
                .setMessage("Are you sure you want quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAndRemoveTask();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    public void helpDialog(){
        View dialogView = PlayerActivity.this.getLayoutInflater().inflate(R.layout.layout_help_popup, null);
        final Dialog mainDialog =new Dialog(PlayerActivity.this);
        mainDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mainDialog.show();
        mainDialog.setCanceledOnTouchOutside(false);
        mainDialog.setContentView(dialogView);
        ImageView ivCloseSymbol =(ImageView) dialogView.findViewById(R.id.iv_close);
        Button closeButton = (Button) dialogView.findViewById(R.id.btn_Close);
        ivCloseSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDialog.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDialog.dismiss();

            }
        });
    }
}
