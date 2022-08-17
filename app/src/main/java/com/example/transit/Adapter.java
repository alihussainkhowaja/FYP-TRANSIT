package com.example.transit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class
Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ModelClass> userlist;
    public Adapter (List<ModelClass>userlist){
        this .userlist = userlist;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        String name_ = userlist.get(position).getName();
        String stop_ = userlist.get(position).getStop();
        String price_ = userlist.get(position).getPrice();
        String date_ = userlist.get(position).getDate();

        holder.setData(name_, stop_, price_, date_);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;
        private TextView Stop;
        private TextView Price;
        private TextView Date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name=itemView.findViewById(R.id.name);
            Stop=itemView.findViewById(R.id.stop);
            Price=itemView.findViewById(R.id.price);
            Date=itemView.findViewById(R.id.date);
        }

        public void setData(String name_, String stop_, String price_, String date_) {
            Name.setText(name_);
            Stop.setText(stop_);
            Price.setText(price_);
            Date.setText(date_);
        }
    }
}
