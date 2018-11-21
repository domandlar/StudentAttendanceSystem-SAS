package com.foi.air.studentattendancesystem.adaptersprofesor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.core.entities.Seminars;
import com.foi.air.studentattendancesystem.R;

import java.util.List;

public class ListOfSeminarsAdapter extends RecyclerView.Adapter<ListOfSeminarsAdapter.SeminarViewHolder>{

    private Context mCtx;
    private List<Seminars> seminarsList;

    public ListOfSeminarsAdapter(Context mCtx, List<Seminars> seminarsList) {
        this.mCtx = mCtx;
        this.seminarsList = seminarsList;
    }

    @NonNull
    @Override
    public SeminarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_list_of_seminars, null);
        SeminarViewHolder holder = new SeminarViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeminarViewHolder seminarViewHolder, int i) {
        Seminars seminar = seminarsList.get(i);

        seminarViewHolder.title.setText(seminar.getTitle());
        seminarViewHolder.day.setText(seminar.getDay());
        seminarViewHolder.time.setText(seminar.getTime());
        seminarViewHolder.classroom.setText(seminar.getClassroom());
    }

    @Override
    public int getItemCount() {
        return seminarsList.size();
    }

    class SeminarViewHolder extends RecyclerView.ViewHolder {
        TextView title, day, time, classroom;

        public SeminarViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            day = itemView.findViewById(R.id.textViewDay);
            time = itemView.findViewById(R.id.textViewTime);
            classroom = itemView.findViewById(R.id.textViewClassroom);


        }
    }
}
