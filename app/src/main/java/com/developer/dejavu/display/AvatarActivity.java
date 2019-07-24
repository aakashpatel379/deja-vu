package com.developer.dejavu.display;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.developer.dejavu.R;
import com.developer.dejavu.adapter.RecyclerViewAdapter;
import com.developer.dejavu.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvatarActivity extends AppCompatActivity
{
    EditText etDisplayName;
    FirebaseAuth mAuth;
    TextView tvUserFullName;
    Button btnChangeAvatar, btnSaveProfile, btnBackToPlayerScreen;
    public static int lastCheckedIndex = -1;
    RecyclerViewAdapter recyclerViewAdapter;
    ImageView ivUserAvatar, ivCheckName, ivCheckAvatar;
    RecyclerView recyclerView;
    private static final String TAG = "AvatarActivity";
    private DatabaseReference databaseReference;
    private GoogleSignInClient mGoogleSignInClient;

    User oldExistingUser =null;
    ArrayList<Integer> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        etDisplayName = (EditText) findViewById(R.id.et_disp_name);
        btnChangeAvatar = (Button) findViewById(R.id.btn_change_avatar);
        btnSaveProfile = (Button) findViewById(R.id.btn_save_profile);
        ivUserAvatar = (ImageView) findViewById(R.id.iv_user_avatar);
        ivUserAvatar.setImageResource(R.drawable.register_ico);
        ivCheckName = (ImageView) findViewById(R.id.iv_check_name);
        ivCheckAvatar = (ImageView)findViewById(R.id.iv_check_avatar);
        tvUserFullName = findViewById(R.id.tv_player_fullname);
        btnBackToPlayerScreen= (Button)findViewById(R.id.btn_back_player_screen);
        ivCheckName.setVisibility(View.INVISIBLE);
        ivCheckAvatar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user.getDisplayName()==null || user.getDisplayName().isEmpty())
        {
            tvUserFullName.setText("New Player");
        }
        else
            tvUserFullName.setText(user.getDisplayName());

        imageList =new ArrayList<>();
        //for loading available 16 default avatars
        for(int i=1; i<=16 ; i++)
        {
            imageList.add(i);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference= database.getReference("Users");
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    Log.d(TAG, "onDataChange: "+dataSnapshot.getValue().toString());
                    oldExistingUser  = (User) dataSnapshot.getValue(User.class);
                    if(oldExistingUser.getDisplayName()!=null)
                    {
                        loadData();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        etDisplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivCheckName.setVisibility(View.INVISIBLE);
                String changedText = etDisplayName.getText().toString();
                if(changedText.isEmpty())
                {
                    etDisplayName.setError("Name can't be blank!");
                }
                else if(changedText.equals("Player 1"))
                {
                    etDisplayName.setError("Change default display name to proceed!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String changedText = etDisplayName.getText().toString();

                if(changedText!="Player 1") {
                    if(changedText.isEmpty())
                    {
                        etDisplayName.setError("Name can't be blank");
                    }
                    else
                        ivCheckName.setVisibility(View.VISIBLE);
                }
                else
                {
                    etDisplayName.setError("Change default display name to proceed!");
                    ivCheckName.setVisibility(View.INVISIBLE);
                }
            }
        });
        btnChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectorDialog();
            }
        });
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
        btnBackToPlayerScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AvatarActivity.this, PlayerActivity.class));
            }
        });
    }

    private void loadData() {
        ivCheckName.setVisibility(View.VISIBLE);
        ivCheckAvatar.setVisibility(View.VISIBLE);
        etDisplayName.setText(oldExistingUser.getDisplayName());
        tvUserFullName.setText(oldExistingUser.getName());
        Resources resources = getApplicationContext().getResources();
        final int resourceId = resources.getIdentifier("item"+ (oldExistingUser.getImgIndex()), "drawable",
                getApplicationContext().getPackageName());
        ivUserAvatar.setImageResource(resourceId);

    }

    private void openSelectorDialog() {
        View selectorView = AvatarActivity.this.getLayoutInflater().inflate(R.layout.layout_avatar_dialog, null);
        final Dialog dialog =new Dialog(AvatarActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(selectorView);
        recyclerView= (RecyclerView) selectorView.findViewById(R.id.rv_avatars);
        Button btnSelect = (Button) selectorView.findViewById(R.id.btn_img_select);
        TextView tvTitle = (TextView) selectorView.findViewById(R.id.lbl_heading);
        recyclerViewAdapter = new RecyclerViewAdapter(this,  imageList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(recyclerViewAdapter);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnChangeAvatar.setError(null);
                if(recyclerViewAdapter.getLastCheckedPosition()==-1)
                {
                    Toast.makeText(AvatarActivity.this, "Please make at least one selection!",Toast.LENGTH_SHORT).show();
                }
                else {
                    lastCheckedIndex= recyclerViewAdapter.getLastCheckedPosition();
                    Resources resources = getApplicationContext().getResources();
                    final int resourceId = resources.getIdentifier("item"+ (lastCheckedIndex+1), "drawable",
                            getApplicationContext().getPackageName());
                    ivUserAvatar.setImageResource(resourceId);
                    ivCheckAvatar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            }
        });

    }

    private void saveProfile()
    {
        if (ivCheckAvatar.getVisibility()==View.VISIBLE && ivCheckName.getVisibility()== View.VISIBLE)
        {
            addModifyUserDetail();
        }
        else{
            Toast.makeText(getApplicationContext(), "Please update the details", Toast.LENGTH_SHORT).show();
            if(ivCheckAvatar.getVisibility()!=View.VISIBLE)
            {
                btnChangeAvatar.setError("Atleast one avatar must be selected!");
                btnChangeAvatar.setFocusable(true);
            }
            if(ivCheckName.getVisibility()!=View.VISIBLE)
            {
                etDisplayName.setError("Change default display name to proceed!");
                etDisplayName.setFocusable(true);
            }
        }
    }

    private void addModifyUserDetail() {
        FirebaseUser authCurrentUser = mAuth.getCurrentUser();
        User user = new User();
        user.setImgIndex(lastCheckedIndex+1);
        user.setDisplayName(etDisplayName.getText().toString());
        user.setName(authCurrentUser.getDisplayName());
        user.setEmail(authCurrentUser.getEmail());
        if(oldExistingUser!=null)
        {
            databaseReference.setValue(user);
            Toast.makeText(AvatarActivity.this,"Data Updated Successfully!", Toast.LENGTH_LONG).show();
        }
        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            databaseReference= database.getReference("Users").child(mAuth.getCurrentUser().getUid());
            databaseReference.setValue(user);
            Toast.makeText(AvatarActivity.this,"Data Inserted Successfully!", Toast.LENGTH_LONG).show();
        }
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

}


