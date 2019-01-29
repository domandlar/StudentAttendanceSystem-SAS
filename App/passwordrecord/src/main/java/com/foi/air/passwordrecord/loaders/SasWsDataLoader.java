package com.foi.air.passwordrecord.loaders;

import com.foi.air.core.entities.Aktivnost;
import com.foi.air.core.entities.AktivnostiStudenta;
import com.foi.air.core.entities.Profesor;
import com.foi.air.core.entities.Student;
import com.foi.air.webservice.SasWebServiceCaller;
import com.foi.air.webservice.SasWebServiceHandler;

public class SasWsDataLoader {
    private Boolean opSuccessful;
    private SasWsDataLoadedListener sasWsDataLoadedListener;
    private SasWebServiceCaller Ws;

    public SasWsDataLoader() {
        Ws = new SasWebServiceCaller(responseHandler);
    }


    public void kolegijForProfesor(Profesor profesor, SasWsDataLoadedListener sasWsDataLoadedListener){
        this.sasWsDataLoadedListener = sasWsDataLoadedListener;
        Ws.CallWsForKolegijiProfesora(profesor);
    }

    public void generatePassword(int idAktivnosti, int tjedanNastave, SasWsDataLoadedListener sasWsDataLoadedListener){
        this.sasWsDataLoadedListener = sasWsDataLoadedListener;
        Ws.CallWsForGeneratePassword(idAktivnosti,tjedanNastave);
    }
    public void allAktivnostForStudent(Student student, SasWsDataLoadedListener sasWsDataLoadedListener){
        this.sasWsDataLoadedListener = sasWsDataLoadedListener;
        Ws.CallWsForAllAktivnostiStudenta(student);
    }
    public void zabiljeziLozinkom(Student student,String lozinka, int tjedanNastave, int idAktivnosti, SasWsDataLoadedListener sasWsDataLoadedListener){
        this.sasWsDataLoadedListener = sasWsDataLoadedListener;
        Ws.CallWsForZAbiljeziLozinkom(student, lozinka, tjedanNastave, idAktivnosti);
    }
    public SasWebServiceHandler responseHandler = new SasWebServiceHandler() {
        @Override
        public void onDataArrived(Object message, String stauts, Object data) {
            sasWsDataLoadedListener.onWsDataLoaded(message, stauts, data);
        }
    };


}
