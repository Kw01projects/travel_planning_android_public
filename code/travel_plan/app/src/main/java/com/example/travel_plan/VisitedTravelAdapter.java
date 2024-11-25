package com.example.travel_plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VisitedTravelAdapter extends RecyclerView.Adapter<VisitedTravelAdapter.ViewHolder> {

    private final List<String> travelList;

    public VisitedTravelAdapter(List<String> travelList) {
        this.travelList = travelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.travelName.setText(travelList.get(position));
    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView travelName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            travelName = itemView.findViewById(android.R.id.text1);
        }
    }
}