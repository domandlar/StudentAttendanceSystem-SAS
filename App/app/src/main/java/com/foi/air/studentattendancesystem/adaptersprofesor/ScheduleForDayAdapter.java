package com.foi.air.studentattendancesystem.adaptersprofesor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.studentattendancesystem.R;

import java.util.List;

public class ScheduleForDayAdapter extends RecyclerView.Adapter<ScheduleForDayAdapter.ScheduleForDayViewHolder>{

    private Context mCtx;
    private List<Kolegij> kolegijList;

    public ScheduleForDayAdapter(Context mCtx, List<Kolegij> kolegijList) {
        this.mCtx = mCtx;
        this.kolegijList = kolegijList;
    }

    @NonNull
    @Override
    public ScheduleForDayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_schedule_for_day_profesor, null);
        ScheduleForDayViewHolder holder = new ScheduleForDayViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleForDayViewHolder seminarViewHolder, int i) {
        Kolegij kolegij = kolegijList.get(i);

        seminarViewHolder.title.setText(kolegij.getNaziv());
        seminarViewHolder.time.setText(kolegij.getSemestar());
        seminarViewHolder.classroom.setText(kolegij.getStudij());
    }

    @Override
    public int getItemCount() {
        return kolegijList.size();
    }

    class ScheduleForDayViewHolder extends RecyclerView.ViewHolder {
        TextView title, day, time, classroom;

        public ScheduleForDayViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            day = itemView.findViewById(R.id.textViewDay);
            time = itemView.findViewById(R.id.textViewTime);
            classroom = itemView.findViewById(R.id.textViewClassroom);


        }
    }
}