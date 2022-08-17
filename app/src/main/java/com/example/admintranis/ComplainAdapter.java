package com.example.admintranis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ComplainAdapter extends FirebaseRecyclerAdapter<ComplainModel,ComplainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ComplainAdapter(@NonNull FirebaseRecyclerOptions<ComplainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ComplainModel model) {

        holder.reason.setText(model.getReason());
        holder.issue.setText(model.getIssue());
        holder.description.setText(model.getDescription());
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_item_design,parent,false);
        return  new ComplainAdapter.myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView reason,issue,description,name,email;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            reason =(TextView)itemView.findViewById(R.id.Freason);
            issue =(TextView)itemView.findViewById(R.id.Fissue);
            description =(TextView)itemView.findViewById(R.id.Fdescription);
            name=(TextView)itemView.findViewById(R.id.name);
            email=(TextView)itemView.findViewById(R.id.email);

        }
    }

}
