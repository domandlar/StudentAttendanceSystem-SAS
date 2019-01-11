package com.foi.air.studentattendancesystem.adaptersStudent;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foi.air.core.entities.Kolegij;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.uiprofesor.ChangeCourse;

import java.util.List;

public class ListCoursesAdapter extends RecyclerView.Adapter<ListCoursesAdapter.CourseViewHolder>{

    private Context mCtx;
    private List<Kolegij> courseList;

    public ListCoursesAdapter(Context mCtx, List<Kolegij> courseList) {
        this.mCtx = mCtx;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_list_of_courses, null);
        CourseViewHolder holder = new CourseViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder courseViewHolder, int i) {
        final Kolegij kolegij = courseList.get(i);

        courseViewHolder.title.setText(kolegij.getNaziv());
        courseViewHolder.semestar.setText("Semestar : " + toString().valueOf(kolegij.getSemestar()));
        courseViewHolder.studij.setText("Studij : " + kolegij.getStudij());


    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView title, semestar, studij;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            semestar = itemView.findViewById(R.id.textViewSemestar);
            studij = itemView.findViewById(R.id.textViewStudij);
        }
    }
}
