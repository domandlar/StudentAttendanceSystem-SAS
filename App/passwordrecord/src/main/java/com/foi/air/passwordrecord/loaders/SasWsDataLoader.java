package com.foi.air.passwordrecord.loaders;

import com.foi.air.core.entities.Aktivnost;
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

    public SasWebServiceHandler responseHandler = new SasWebServiceHandler() {
        @Override
        public void onDataArrived(Object message, String stauts, Object data) {
            sasWsDataLoadedListener.onWsDataLoaded(message, stauts, data);
        }
    };
}
