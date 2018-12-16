package com.foi.air.studentattendancesystem.adaptersprofesor;

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

public class ListOfActivitiesAdapter extends RecyclerView.Adapter<ListOfActivitiesAdapter.ActivityViewHolder>{

    private Context mCtx;
    private List<Aktivnost> activityList;

    public ListOfActivitiesAdapter(Context mCtx, List<Aktivnost> seminarList) {
        this.mCtx = mCtx;
        this.activityList = seminarList;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_list_of_activities, null);
        ActivityViewHolder holder = new ActivityViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder activityViewHolder, int i) {
        Aktivnost aktivnost = activityList.get(i);

        activityViewHolder.title.setText(aktivnost.getKolegij());
        activityViewHolder.day.setText(aktivnost.getDanIzvodenja());
        activityViewHolder.time.setText(aktivnost.getPocetak());
        activityViewHolder.classroom.setText(aktivnost.getDvorana());
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView title, day, time, classroom;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            day = itemView.findViewById(R.id.textViewDay);
            time = itemView.findViewById(R.id.textViewTime);
            classroom = itemView.findViewById(R.id.textViewClassroom);


        }
    }
}
