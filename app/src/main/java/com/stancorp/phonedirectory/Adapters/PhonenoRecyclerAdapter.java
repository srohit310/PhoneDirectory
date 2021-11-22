package com.stancorp.phonedirectory.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stancorp.phonedirectory.Classes.PhoneNumber;
import com.stancorp.phonedirectory.R;

import java.util.ArrayList;

public class PhonenoRecyclerAdapter extends RecyclerView.Adapter<PhonenoRecyclerAdapter.PhonenoBaseViewHolder> {

    protected Context BASE_CONTEXT;
    ArrayList<PhoneNumber> phoneNumberArrayList;

    public PhonenoRecyclerAdapter(Context context, ArrayList<PhoneNumber> phoneNumberArrayList) {
        this.BASE_CONTEXT = context;
        this.phoneNumberArrayList = phoneNumberArrayList;
    }

    @Override
    public PhonenoBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phonenolayout,parent,false);
        PhonenoBaseViewHolder phonenoBaseViewHolder = new PhonenoRecyclerAdapter.PhonenoBaseViewHolder(view);
        return phonenoBaseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhonenoRecyclerAdapter.PhonenoBaseViewHolder holder, int position) {
        PhoneNumber phoneNumber = new PhoneNumber();
        if(phoneNumberArrayList != null)
            phoneNumber = phoneNumberArrayList.get(position);

        holder.phonenoTextView.setText(String.valueOf(phoneNumber.getPhoneNumber()));
        holder.phonenoType.setText(phoneNumber.getType());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneNumber phoneNumber = phoneNumberArrayList.get(position);
                phoneNumberArrayList.remove(phoneNumber);
                PhonenoRecyclerAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneNumberArrayList.size();
    }

    public class PhonenoBaseViewHolder extends RecyclerView.ViewHolder{
        TextView phonenoTextView;
        TextView phonenoType;
        Button button;

        public PhonenoBaseViewHolder(@NonNull View itemView) {
            super(itemView);
            phonenoTextView = itemView.findViewById(R.id.PhoneNumber);
            phonenoType = itemView.findViewById(R.id.phonenotypeTextView);
            button = itemView.findViewById(R.id.DeleteButton);
        }
    }
}
