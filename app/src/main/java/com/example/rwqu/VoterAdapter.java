package com.example.rwqu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VoterAdapter extends RecyclerView.Adapter<VoterAdapter.ViewHolder> {

    private List<Voter> voterList;

    public VoterAdapter(List<Voter> voterList) {
        this.voterList = voterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Voter voter = voterList.get(position);
        holder.txtVoterName.setText(voter.getVoterName());
        holder.txtVoterNIK.setText(voter.getVoterNIK());
        holder.txtVoterUID.setText(voter.getVoterUID());
    }

    @Override
    public int getItemCount() {
        return voterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtVoterName, txtVoterNIK, txtVoterUID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVoterName = itemView.findViewById(R.id.namaTextView);
            txtVoterNIK = itemView.findViewById(R.id.nikTextView);
            txtVoterUID = itemView.findViewById(R.id.idTagTextView);
        }
    }
}

