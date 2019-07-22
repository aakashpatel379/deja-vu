package com.developer.dejavu.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.developer.dejavu.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Integer> imageItemList = new ArrayList<>();
//    private MyViewHolder.AvatarListener avatarListener;
    private static Boolean changed=false;

    public RecyclerViewAdapter(Context mContext, ArrayList<Integer> imageItemList)//, MyViewHolder.AvatarListener avatarListener)
    {
        this.mContext = mContext;
        this.imageItemList = imageItemList;
//        this.avatarListener = avatarListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);  //, avatarListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Resources resources = mContext.getResources();
        final int resourceId = resources.getIdentifier("item"+imageItemList.get(position), "drawable",
                mContext.getPackageName());
       myViewHolder.cardViewItem.setBackground(mContext.getResources().getDrawable(resourceId));

       if(changed)
       {
           myViewHolder.radioBtnItem.setChecked(false);
       }

    }

    @Override
    public int getItemCount() {
        return imageItemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        CardView cardViewItem;
        RadioButton radioBtnItem;
//        AvatarListener avatarListener;

        public MyViewHolder(View itemView)    ///, AvatarListener avatarListener)
        {
            super(itemView);
            cardViewItem = (CardView) itemView.findViewById(R.id.cv_item);
            radioBtnItem = (RadioButton) itemView.findViewById(R.id.rb_image_item);
//            this.avatarListener =avatarListener;
//            radioBtnItem.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v)
//        {
//            avatarListener.onItemChecked(getAdapterPosition());
//            int changedIndex = getAdapterPosition();
//
//            CheckBox cb1=v.getRootView().findViewById(R.id.cb_image_item);
//            cb1.setChecked(true);
//            for(int pos =0; pos<16; pos++)
//            {
//                if(pos!=changedIndex)
//                {
//                    CheckBox cb=v.getRootView().findViewById(R.id.cb_image_item);
//                    cb.setChecked(false);
//                }
//            }
//        }

//        public interface AvatarListener {
//            void onItemChecked(int position);
//        }
    }

}

