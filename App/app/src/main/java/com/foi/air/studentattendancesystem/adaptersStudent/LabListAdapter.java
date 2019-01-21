package com.foi.air.studentattendancesystem.adaptersStudent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.BookedLab;
import com.foi.air.studentattendancesystem.R;

import java.util.List;

public class LabListAdapter extends RecyclerView.Adapter<LabListAdapter.LabViewHolder> {

    private Context mCtx;
    private List<BookedLab> labList;

    public LabListAdapter(Context mCtx, List<BookedLab> labList){
        this.mCtx = mCtx;
        this.labList = labList;
    }

    @NonNull
    @Override
    public LabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_labs_booking, null);
        LabViewHolder holder = new LabViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LabViewHolder labViewHolder, int i) {
        BookedLab lab = labList.get(i);
        labViewHolder.day.setText(lab.getDanIzvodenja());
        labViewHolder.time.setText(lab.getPocetak() + " - " + lab.getKraj());
        labViewHolder.classroom.setText(lab.getDvorana());
        labViewHolder.numberOfReservations.setText(lab.getBrojUpisanih() + "/" + lab.getKapacitet());
    }

    @Override
    public int getItemCount() {
        return labList.size();
    }

    public class LabViewHolder extends RecyclerView.ViewHolder{
        public TextView day, time, classroom, numberOfReservations;
        public CheckBox odabir;

        public LabViewHolder(@NonNull View itemView){
            super(itemView);
            day = itemView.findViewById(R.id.danOdrzavanja);
            time = itemView.findViewById(R.id.textViewVrijeme);
            classroom = itemView.findViewById(R.id.textViewDvorana);
            numberOfReservations = itemView.findViewById(R.id.textViewPredbiljezeno);
            odabir = itemView.findViewById(R.id.chcboxOdabir);
        }
    }
}
