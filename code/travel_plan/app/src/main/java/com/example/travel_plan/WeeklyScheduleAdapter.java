package com.example.travel_plan;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeeklyScheduleAdapter extends RecyclerView.Adapter<WeeklyScheduleAdapter.WeeklyScheduleViewHolder> {

    private final List<WeeklyScheduleItem> scheduleList;

    public WeeklyScheduleAdapter(List<WeeklyScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public WeeklyScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weekly_schedule, parent, false);
        return new WeeklyScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyScheduleViewHolder holder, int position) {
        WeeklyScheduleItem item = scheduleList.get(position);

        // 시간 텍스트 설정
        holder.timeText.setText(String.format("%02d:00", item.getHour()));

        // 일정이 있는 경우 배경 색상 설정
        if (!item.getTask().isEmpty()) {
            holder.taskText.setBackgroundColor(Color.parseColor("#FFE0B2"));
            holder.taskText.setText(item.getTask());
        } else {
            holder.taskText.setBackgroundColor(Color.TRANSPARENT);
            holder.taskText.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class WeeklyScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView timeText, taskText;

        public WeeklyScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.time_text);
            taskText = itemView.findViewById(R.id.task_text);
        }
    }
}