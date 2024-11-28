package com.example.travel_plan.screens.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_plan.R;
import com.example.travel_plan.dtos.ScheduleItem;

import java.util.List;

public class TodayScheduleAdapter extends RecyclerView.Adapter<TodayScheduleAdapter.ScheduleViewHolder> {

    private final List<ScheduleItem> scheduleList;

    public TodayScheduleAdapter(List<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem item = scheduleList.get(position);
        holder.taskTextView.setText(item.getTask());
        holder.timeTextView.setText(item.getStartTime() + " - " + item.getEndTime());
        holder.completedCheckBox.setChecked(item.isCompleted());

        // 체크박스 상태 변경
        holder.completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> item.setCompleted(isChecked));
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView taskTextView;
        TextView timeTextView;
        CheckBox completedCheckBox;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.task_text);
            timeTextView = itemView.findViewById(R.id.time_text);
            completedCheckBox = itemView.findViewById(R.id.completed_checkbox);
        }
    }
}
