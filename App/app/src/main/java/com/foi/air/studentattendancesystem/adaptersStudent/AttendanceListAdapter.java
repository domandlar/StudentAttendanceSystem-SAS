package com.foi.air.studentattendancesystem.adaptersStudent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Kolegij;
import com.foi.air.studentattendancesystem.R;

import java.util.List;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.AttendanceViewHolder> {

    private Context mCtx;
    private List<Kolegij> courseList;
    private List<Aktivnost> aktivnostList;

    public AttendanceListAdapter(Context mCtx,List<Kolegij> courseList, List<Aktivnost> aktivnostList){
        this.mCtx = mCtx;
        this.courseList = courseList;
        this.aktivnostList = aktivnostList;
    }



    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_attendance_list_adapter, null);
        AttendanceViewHolder holder = new AttendanceViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder attendanceViewHolder, int i) {
        final Kolegij kolegij = courseList.get(i);
        final Aktivnost aktivnost = aktivnostList.get(i);

        //attendanceViewHolder.title(kolegij.getNaziv());
        //attendanceViewHolder.activity(aktivnost.getTipAktivnosti());

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class AttendanceViewHolder extends RecyclerView.ViewHolder{
        TextView title, activity, day, week, attendance;
        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            activity = itemView.findViewById(R.id.textViewActivity);
            day = itemView.findViewById(R.id.textViewDay);
            week = itemView.findViewById(R.id.textViewWeek);
            attendance = itemView.findViewById(R.id.textViewAttendance);
        }

    }
}
