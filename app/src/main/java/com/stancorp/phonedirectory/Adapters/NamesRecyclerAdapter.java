package com.stancorp.phonedirectory.Adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.stancorp.phonedirectory.R;

import java.util.ArrayList;

public class NamesRecyclerAdapter extends RecyclerView.Adapter<NamesRecyclerAdapter.NamesBaseViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    protected Context BASE_CONTEXT;
    private ArrayList<Pair<Boolean, String>> namesList;
    OnNoteListner onNoteListner;


    public NamesRecyclerAdapter(Context context, ArrayList<Pair<Boolean, String>> namesList, OnNoteListner onNoteListner){
        this.BASE_CONTEXT = context;
        this.namesList = namesList;
        this.onNoteListner = onNoteListner;
    }

    public void updateDataSet(ArrayList<Pair<Boolean, String>> namesList){
        this.namesList = namesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NamesBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.namesrecyclerlayout,parent,false);
        NamesBaseViewHolder namesBaseViewHolder = new NamesRecyclerAdapter.NamesBaseViewHolder(view,onNoteListner);
        return namesBaseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NamesRecyclerAdapter.NamesBaseViewHolder holder, int position) {
        String name = "";
        if(namesList!=null) {
            Pair<Boolean,String> pair = namesList.get(position);
            if(pair.first){
                holder.contactView.setVisibility(View.GONE);
                holder.header.setVisibility(View.VISIBLE);
                holder.header.setText(pair.second);
            }else {
                name = pair.second;
                holder.contactView.setVisibility(View.VISIBLE);
                holder.header.setVisibility(View.GONE);
                holder.nameTV.setText(name);
            }
        }
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return String.valueOf(namesList.get(position).second.charAt(0));
    }

    public class NamesBaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nameTV;
        OnNoteListner onNoteListner;
        TextView header;
        CardView contactView;

        public NamesBaseViewHolder(@NonNull View itemView, final OnNoteListner onNoteListner) {
            super(itemView);
            this.onNoteListner = onNoteListner;
            nameTV = itemView.findViewById(R.id.ContactName);
            header = itemView.findViewById(R.id.headerView);
            contactView = itemView.findViewById(R.id.contactView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onNoteListner != null && !namesList.get(position).first) {
                this.onNoteListner.OnNoteClick(namesList.get(position).second, position);
            }
        }
    }

    public interface OnNoteListner {
        void OnNoteClick(String string, int position);
    }
}

