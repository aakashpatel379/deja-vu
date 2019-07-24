package com.developer.dejavu.adapter;

import android.content.Context;

import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.dejavu.R;
import com.developer.dejavu.cpu.CardViewHolder;
import com.developer.dejavu.cpu.OnItemClickListener;
import com.developer.dejavu.gameplay.Card;


public class CardImageAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private ArraySet<Card> cards;

    public CardImageAdapter(Context context, ArraySet<Card> cards){
        this.context = context;
        this.cards = cards;
    }

    public void setCards(ArraySet<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageView imageView = new ImageView(this.context);
        imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,190));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new CardViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder cardViewHolder, int i) {
        ((ImageView) cardViewHolder.itemView).setImageResource(R.drawable.back);
        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, cardViewHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public Card getCard(int position) {
        return cards.valueAt(position);
    }

    public ArraySet<Card> getCards() {
        return cards;
    }

    public void remove(int idx1, int idx2) {
        Log.e("CardImageAdapter", "Removing Items: "+cards.valueAt(idx1).toString()+", "+cards.valueAt(idx2).toString());
        int maxIdx = Math.max(idx1, idx2);
        int minIdx = Math.min(idx1, idx2);
        cards.removeAt(maxIdx);
        notifyItemRemoved(maxIdx);
        cards.removeAt(minIdx);
        notifyItemRemoved(minIdx);
        Log.d("CardImageAdapter", "Remaining Data: "+cards);
//         notifyDataSetChanged();
    }
}
