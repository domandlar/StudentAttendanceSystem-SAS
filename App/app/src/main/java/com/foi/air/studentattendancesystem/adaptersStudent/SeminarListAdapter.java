package com.foi.air.studentattendancesystem.adaptersStudent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.studentattendancesystem.R;

import java.util.List;

public class SeminarListAdapter extends RecyclerView.Adapter<SeminarListAdapter.SeminarViewHolder>{

    private Context mCtx;
    private List<Aktivnost> seminarList;

    public SeminarListAdapter(Context mCtx, List<Aktivnost> seminarList) {
        this.mCtx = mCtx;
        this.seminarList = seminarList;
    }

    @NonNull
    @Override
    public SeminarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_list_of_activities, null);
        SeminarViewHolder holder = new SeminarViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeminarViewHolder seminarViewHolder, int i) {
        Aktivnost aktivnost = seminarList.get(i);

        seminarViewHolder.title.setText(aktivnost.getKolegij());
        seminarViewHolder.day.setText(aktivnost.getDanIzvodenja());
        seminarViewHolder.time.setText(aktivnost.getPocetak());
        seminarViewHolder.classroom.setText(aktivnost.getDvorana());
    }

    @Override
    public int getItemCount() {
        return seminarList.size();
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
