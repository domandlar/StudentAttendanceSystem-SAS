package com.foi.air.studentattendancesystem.adaptersprofesor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.Seminar;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.uiprofesor.ListOfSeminars;
import com.foi.air.studentattendancesystem.uiprofesor.ScheduleForDayProfesor;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{

    private String[] days;
    private Context mCtx;

    public ScheduleAdapter(Context mCtx, String[] days) {
        this.mCtx = mCtx;
        this.days = days;
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView itemDay;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            itemDay = (TextView) itemView.findViewById(R.id.textViewDay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    switch(position){
                        case 0:
                            Intent intent = new Intent(mCtx, ScheduleForDayProfesor.class);
                            intent.putExtra("day", "Ponedjeljak");
                            mCtx.startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mCtx, ScheduleForDayProfesor.class);
                            intent.putExtra("day", "Utorak");
                            mCtx.startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mCtx, ScheduleForDayProfesor.class);
                            intent.putExtra("day", "Srijeda");
                            mCtx.startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mCtx, ScheduleForDayProfesor.class);
                            intent.putExtra("day", "Četvrtak");
                            mCtx.startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(mCtx, ScheduleForDayProfesor.class);
                            intent.putExtra("day", "Petak");
                            mCtx.startActivity(intent);
                            break;
                    }
                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_schedule_profesor, viewGroup, false);
        ScheduleViewHolder viewHolder = new ScheduleViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder scheduleViewHolder, int i) {
        scheduleViewHolder.itemDay.setText(days[i]);
    }

    @Override
    public int getItemCount() {
        return days.length;
    }


}