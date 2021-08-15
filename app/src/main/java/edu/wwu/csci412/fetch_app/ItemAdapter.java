package edu.wwu.csci412.fetch_app;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ArrayList<Item> mItemList;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView mIdView;
        public TextView mListIdView;
        public TextView mNameView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mIdView = itemView.findViewById(R.id.idView);
            mListIdView = itemView.findViewById(R.id.listIdView);
            mNameView = itemView.findViewById(R.id.nameView);
        }
    }

    public ItemAdapter(ArrayList<Item> inList){
        mItemList = inList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry, parent, false);
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = mItemList.get(position);
        holder.mIdView.setText("" + currentItem.getId());
        holder.mListIdView.setText("" + currentItem.getListId());
        holder.mNameView.setText("" + currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
