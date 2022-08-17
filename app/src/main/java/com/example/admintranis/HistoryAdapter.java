package com.example.admintranis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HistoryAdapter extends FirebaseRecyclerAdapter<HistoryModel,HistoryAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HistoryAdapter(@NonNull FirebaseRecyclerOptions<HistoryModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull HistoryModel model) {
        holder.name.setText(model.getName());
        holder.date.setText(model.getDate());
        holder.arrival.setText(model.getArrival());
        holder.departure.setText(model.getDeparture());
        holder.price.setText(String.valueOf(model.getAmount()));

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_design,parent,false);
        return  new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name,departure,date,price,arrival;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.name);
            arrival=(TextView)itemView.findViewById(R.id.arrival);
            price=(TextView)itemView.findViewById(R.id.price);
            departure=(TextView) itemView.findViewById(R.id.departure);
            date=(TextView)itemView.findViewById(R.id.date);
        }
    }

}
