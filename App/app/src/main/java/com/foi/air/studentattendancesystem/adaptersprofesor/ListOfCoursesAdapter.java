package com.foi.air.studentattendancesystem.adaptersprofesor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.foi.air.core.entities.Kolegij;
import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.uiprofesor.ChangeCourse;

import java.util.List;

public class ListOfCoursesAdapter extends RecyclerView.Adapter<ListOfCoursesAdapter.CourseViewHolder>{

    private Context mCtx;
    private List<Kolegij> courseList;

    public ListOfCoursesAdapter(Context mCtx, List<Kolegij> courseList) {
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

        courseViewHolder.relative.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mCtx, ChangeCourse.class);
                        intent.putExtra("idKolegija", toString().valueOf(kolegij.getId()));
                        intent.putExtra("naziv", kolegij.getNaziv());
                        intent.putExtra("semestar", toString().valueOf(kolegij.getSemestar()));
                        intent.putExtra("studij", kolegij.getStudij());
                        mCtx.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView title, semestar, studij;
        RelativeLayout relative;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            semestar = itemView.findViewById(R.id.textViewSemestar);
            studij = itemView.findViewById(R.id.textViewStudij);
            relative = itemView.findViewById(R.id.relative);
        }
    }
}
