package com.foi.air.studentattendancesystem.adaptersprofesor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Seminar;
import com.foi.air.studentattendancesystem.R;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{

    private Context mCtx;
    private List<Aktivnost> seminarList;

    public ScheduleAdapter(Context mCtx, List<Aktivnost> seminarList) {
        this.mCtx = mCtx;
        this.seminarList = seminarList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_schedule_profesor, null);
        ScheduleViewHolder holder = new ScheduleViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder scheduleViewHolder, int i) {
        Aktivnost aktivnost = seminarList.get(i);

        scheduleViewHolder.title.setText(aktivnost.getKolegij());
        scheduleViewHolder.day.setText(aktivnost.getDanIzvodenja());
        scheduleViewHolder.time.setText(aktivnost.getPocetak());
        scheduleViewHolder.classroom.setText(aktivnost.getDvorana());
    }

    @Override
    public int getItemCount() {
        return seminarList.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView title, day, time, classroom;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            day = itemView.findViewById(R.id.textViewDay);
            time = itemView.findViewById(R.id.textViewTime);
            classroom = itemView.findViewById(R.id.textViewClassroom);


        }
    }
}
