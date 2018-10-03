package hawkeye.ena.ba.hawkeye;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ena on 15.02.2017..
 *
 */

public class Options {

    private int option;
    //Lokacija trenutna korisnika
    //Od Lazna lokacija, Jezero
    private LatLng locationFrom;
    //Do Lazna lokacija
    private LatLng locationTo;


    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        Log.i("Options","Pozicija je "+ option);
        this.option = option;
    }

    public LatLng getLocationFrom() {
        //if(option==1)
        return locationFrom;
        //else  return new LatLng(43.8563,18.4131);
    }

    public LatLng getLocationTo() {        return locationTo;    }

    void setLocationTo(LatLng locationTo) {        this.locationTo = locationTo;    }

}
