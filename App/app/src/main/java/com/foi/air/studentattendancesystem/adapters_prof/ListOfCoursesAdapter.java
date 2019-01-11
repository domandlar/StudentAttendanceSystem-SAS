package com.foi.air.studentattendancesystem.adapters_prof;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.studentattendancesystem.R;
import com.foi.air.studentattendancesystem.ui_prof.ListOfCourses;

import java.util.List;

public class ListOfCoursesAdapter extends RecyclerView.Adapter<ListOfCoursesAdapter.CourseViewHolder>{

    private String title, day, startTime, endTime,  classroom;

    private Context mCtx;
    private List<Courses> coursesList;

    public ListOfCoursesAdapter(Context mCtx, List<Courses> coursesList) {
        this.mCtx = mCtx;
        this.coursesList = coursesList;
    }

    public  CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_list_of_courses, null);
        CourseViewHolder holder = new CourseViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfCoursesAdapter.CourseViewHolder courseViewHolder, int i) {
        Courses cours = coursesList.get(i);

        courseViewHolder.title.setText(cours.getTitle());
        courseViewHolder.day.setText(cours.getDay());
        courseViewHolder.time.setText(cours.getStartTime() + "-" + cours.getEndTime());
        courseViewHolder.classroom.setText(cours.getClassroom());
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

     class CourseViewHolder extends RecyclerView.ViewHolder{
        TextView title, day, time, classroom;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            day = itemView.findViewById(R.id.textViewDay);
            time = itemView.findViewById(R.id.textViewTime);
            classroom = itemView.findViewById(R.id.textViewClassroom);
        }
    }
}
