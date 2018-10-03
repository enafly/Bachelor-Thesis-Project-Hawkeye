package hawkeye.ena.ba.hawkeye.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.activities.MainActivity;

/**
 * Created by ena on 8/7/17.
 *
 */

public class Station {

    private String id;
    private String name;
    private String numberOfStickers;
    private LatLng coordinates;
    private ArrayList<TransportVehicle> transportVehicles = null;

    public Station(String id, String name, String x, String y, String numberOfStickers, String types, String lines, MainActivity mainActivity){
        this.id = id;
        this.name = name;
        this.coordinates = new LatLng(Double.parseDouble(y),Double.parseDouble(x));
        this.numberOfStickers = numberOfStickers;
        addVehicles(types, lines, mainActivity);
        Log.i("AAAAAA", id + " " + name + " " + coordinates.toString() + " " + numberOfStickers + "\nVozilaaa: " + transportVehicles.toString());
    }

    private void addVehicles(String types, String lines, MainActivity mainActivity) {
        //razdvojiti po tipovima i dodati!!!
        ArrayList<TransportVehicle> transportVehicles = new ArrayList<>();
        String [] typesOfVehicles = types.split(",");
        String [] linesOfVehicles = lines.split(",");
        String [] numberOfLine;
        MainActivity.dataContext.setAllLinesWithTypes(typesOfVehicles);
        int j = 0;
        if(linesOfVehicles.length > 1){
            //odvoji i dodaj i linije kao tip Vehicle
            numberOfLine = lines.split(",");
            for (int i = 0; i < linesOfVehicles.length; i++){
                String typeOfVehicle = MainActivity.dataContext.getTypeOfVehicle(numberOfLine[i]);
                transportVehicles.add(new TransportVehicle(j,typeOfVehicle,numberOfLine[i], mainActivity));
                j++;
            }
        }
        else {
            transportVehicles.add(new TransportVehicle(j, types,lines, mainActivity));
        }
        this.transportVehicles = transportVehicles;
    }

    public LatLng getCoordinates() {        return coordinates;    }

    public ArrayList<TransportVehicle> getTransportVehicles() {        return transportVehicles;    }

    public int getTransportVehicleId(int i)  {  return transportVehicles.get(i).getId();    }

    public String getTransportVehicleName(int i) {        return transportVehicles.get(i).getFullLineName();    }

    public Integer getTransportVehicleIcon(int i) {        return transportVehicles.get(i).getIcon();    }

    public String getName() {        return name;    }

    public String getId() {        return id;    }

}
