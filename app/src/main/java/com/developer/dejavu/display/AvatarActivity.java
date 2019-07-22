package com.developer.dejavu.display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.dejavu.R;
import com.developer.dejavu.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class AvatarActivity extends AppCompatActivity implements View.OnClickListener    //, RecyclerViewAdapter.MyViewHolder.AvatarListener {
{
    EditText displayName;
    Button btnChangeAvatar, btnSaveProfile;
    public static int lastCheckedIndex = -1;
    RecyclerViewAdapter recyclerViewAdapter;
    Typeface typeface;
    RecyclerView recyclerView;
    private static final String TAG = "AvatarActivity";
    ArrayList<Integer> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        displayName = (EditText) findViewById(R.id.et_disp_name);
        btnChangeAvatar = (Button) findViewById(R.id.btn_change_avatar);
        btnSaveProfile = (Button) findViewById(R.id.btn_save_profile);
        imageList =new ArrayList<>();
        //for loading available 16 avatars
        for(int i=1; i<=16 ; i++)
        {
            imageList.add(i);
        }
        btnChangeAvatar.setOnClickListener(this);
        btnSaveProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_change_avatar:
                openSelectorDialog();
                break;

            case R.id.btn_save_profile:
                saveProfile();
                break;
        }
    }

    private void openSelectorDialog() {

        View selectorView = AvatarActivity.this.getLayoutInflater().inflate(R.layout.layout_avatar_dialog, null);
        final Dialog dialog =new Dialog(AvatarActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(selectorView);
        recyclerView= (RecyclerView) selectorView.findViewById(R.id.rv_avatars);
        Button btnSelect = (Button) selectorView.findViewById(R.id.btn_Close);
        TextView tvTitle = (TextView) selectorView.findViewById(R.id.lbl_heading);
        recyclerViewAdapter = new RecyclerViewAdapter(this,  imageList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(recyclerViewAdapter);

        //ImageView ivVolumeOff =(ImageView) selectorView.findViewById(R.id.iv_volume_off);
        //ImageView ivVolumeOn = (ImageView)selectorView.findViewById(R.id.iv_volume_on);

    }

    private void saveProfile() {
    }

//    @Override
//    public void onItemChecked(int position) {
//        //imageList.get(position);
//
//        if(lastCheckedIndex==-1)
//            lastCheckedIndex=position;
//        else{
//            resetCheckedIndex(lastCheckedIndex);
//            lastCheckedIndex = position;
//        }
//
//        Log.d(TAG,"Item checked:"+ position);
//    }

//    private void resetCheckedIndex(int lastCheckedIndex) {
//        imageList.set(lastCheckedIndex, imageList.get(lastCheckedIndex));
//        //recyclerViewAdapter.notifyItemChanged(lastCheckedIndex);
//        recyclerView.setAdapter(new RecyclerViewAdapter(this,imageList,this) {
//        });
//        recyclerView.invalidate();
//    }
}


