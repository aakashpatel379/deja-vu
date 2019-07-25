package com.developer.dejavu.adapter;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.developer.dejavu.R;
import java.util.ArrayList;
import static com.developer.dejavu.R.id.cv_item;

/**
 * Adapter for rendering avatar Recyclerview.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Integer> imageItemList;
    private int lastCheckedPosition=-1;

    public int getLastCheckedPosition() {
        return lastCheckedPosition;
    }

    public void setLastCheckedPosition(int lastCheckedPosition) {
        this.lastCheckedPosition = lastCheckedPosition;
    }

    public RecyclerViewAdapter(Context mContext, ArrayList<Integer> imageItemList)
    {
        this.mContext = mContext;
        this.imageItemList = imageItemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        Resources resources = mContext.getResources();
        final int resourceId = resources.getIdentifier("item"+imageItemList.get(position), "drawable",
                mContext.getPackageName());
       myViewHolder.cardViewItem.setBackground(mContext.getResources().getDrawable(resourceId));

        myViewHolder.radioBtnItem.setChecked(position==lastCheckedPosition);
        myViewHolder.radioBtnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == lastCheckedPosition)
                {
                    myViewHolder.radioBtnItem.setChecked(false);
                    lastCheckedPosition=-1;
                }
                else
                {
                    lastCheckedPosition=position;
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageItemList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardViewItem;
        RadioButton radioBtnItem;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            cardViewItem = (CardView) itemView.findViewById(cv_item);
            radioBtnItem = (RadioButton) itemView.findViewById(R.id.rb_image_item);
        }

    }

}

