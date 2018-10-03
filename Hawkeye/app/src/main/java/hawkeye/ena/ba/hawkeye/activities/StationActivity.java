package hawkeye.ena.ba.hawkeye.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hawkeye.ena.ba.hawkeye.R;
import hawkeye.ena.ba.hawkeye.gui.CustomAdapterArrivals;
import hawkeye.ena.ba.hawkeye.model.*;

public class StationActivity extends Activity {

    ListView list;
    TextView name ,dolasci;
    ArrayList<String> listaLinija = new ArrayList<>();
    ArrayList<ArrayList<String>> timesA = new ArrayList<>();
    ArrayList<ArrayList<String>> timesB = new ArrayList<>();
    int[] transportVehicleIds = new int[1000];
    Integer[] iconsList = new Integer[1000];
    String stationId;
    String option = ListActivity.option;
    ArrayList<String> timeA = new ArrayList<>();
    ArrayList<String> timeB = new ArrayList<>();

    int pos= 1000;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton logo = (ImageButton) toolbar.findViewById(R.id.imageButton);

        logo.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });

        list= (ListView) findViewById(R.id.lista);
        name = (TextView) findViewById(R.id.name);
        dolasci = (TextView) findViewById(R.id.dolasci);
        int color = setColor();

        dolasci.setText(getResources().getString(R.string.dolasci));
        dolasci.setTextColor(color);

        Intent intent = getIntent();
        stationId = intent.getStringExtra("StationId");
        Station station = MainActivity.dataContext.getStationById(stationId);
        name.setText(station.getName());
        name.setTextColor(color);

        setLists(station);
        sortListsByTime();
        setIcons(station);

        CustomAdapterArrivals adapterArrivals = new CustomAdapterArrivals(this,listaLinija, timeA,timeB, iconsList);
        list.setAdapter(adapterArrivals);
    }

    private void setIcons(Station station) {
        int index= 0;
        for(int i = 0; i < listaLinija.size(); i++){
            for(int j=0; j<station.getTransportVehicles().size(); j++){
                if(station.getTransportVehicleName(j).equals(listaLinija.get(i))){
                    iconsList[index] = station.getTransportVehicleIcon(j);
                    index++;
                }
            }
        }
    }

    public int setColor() {
        switch (option){
            case "ALL":
                return getApplication().getResources().getColor(R.color.colorWhite);
            case "A":
                return getApplication().getResources().getColor(R.color.colorPrimaryDark);
            case "M":
                return getApplication().getResources().getColor(R.color.colorMiniBus);
            case "TR":
                return getApplication().getResources().getColor(R.color.colorTrolejbus);
            case "T":
                return getApplication().getResources().getColor(R.color.colorTramvaj);
            default:
                return getApplication().getResources().getColor(R.color.colorWhite);
        }
    }

    private void setLists(Station station) {
        int index= 0;
        for(int i = 0; i < station.getTransportVehicles().size(); i++){
            if(checkOption(station.getTransportVehicles().get(i))){
                listaLinija.add(station.getTransportVehicleName(i));
                transportVehicleIds[index] = station.getTransportVehicleId(i);
                iconsList[index] = station.getTransportVehicleIcon(i);
                index++;
            }
        }
    }

    private void sortListsByTime() {
        ArrayList<Line> lines = MainActivity.lines;
        for(int i = 0; i < listaLinija.size(); i++){
            Line line = getLine(lines, i);
            //Na osnovu dana koja linija dolazi prije !!!
            if(line != null){
                switch(getDay()){
                    case 1:
                        timesA.add(line.getTimesFromRD());
                        timesB.add(line.getTimesToRD());
                        break;
                    case 2:
                        timesA.add(line.getTimesFromSUB());
                        timesB.add(line.getTimesToSUB());
                        break;
                    case 3:
                        timesA.add( line.getTimesFromNEDIPR());
                        timesB.add( line.getTimesToNEDIPR());
                        break;
                }
            }
        }
        getClosestTime();
    }

    private Line getLine(ArrayList<Line> lines, int i) {
        for(Line line: lines){
            if(line.getFullLineName().equals(listaLinija.get(i))){
                return line;
            }
        }
        return null;
    }

    private void getClosestTime() {
        DateFormat dateFormatter = new SimpleDateFormat("kkmm");
        dateFormatter.setLenient(false);
        Date today = new Date();
        String currentTime = dateFormatter.format(today);
        int[] timeDifferences = new int[listaLinija.size()];
        int timeDifferenceA = 1000, timeDifferenceB = 1000;
        for(int i = 0; i<timesA.size(); i++) {
            timeDifferenceA = getTimeDifference(timesA.get(i), currentTime);
            timeA.add(timesA.get(i).get(pos));

            timeDifferenceB = getTimeDifference(timesB.get(i),currentTime);
            timeB.add(timesB.get(i).get(pos));

            timeDifferences[i] = timeDifferenceA + timeDifferenceB;

            Log.i("VRIJEME", " timeA "+ timeA.get(i) + " timeDifferenceA " + timeDifferenceA+ "\n timeB " + timeB.get(i) + " timeDifferenceB " + timeDifferenceB+ "\n timeDifferences "+ timeDifferences[i]);
        }
        //Poredao vremena sada da sorta
        if(timeA.size()>1){
            sortLists(timeDifferences);
        }
    }

    private void sortLists(int[] timeDifferences) {
        for (int i=0; i<timeDifferences.length-1; i++){
            String helper, timeHelperA, timeHelperB;
            if(timeDifferences[i]>timeDifferences[i+1]){
                helper = listaLinija.get(i);
                listaLinija.set(i,listaLinija.get(i+1));
                listaLinija.set(i+1,helper);
                timeHelperA = timeA.get(i);
                timeA.set(i, timeA.get(i+1));
                timeA.set(i+1,timeHelperA);
                timeHelperB = timeB.get(i);
                timeB.set(i, timeB.get(i+1));
                timeB.set(i+1,timeHelperB);
            }
        }
        Log.i("VRIJEME", " listaLinija "+ listaLinija + "\n listOfTimesA " + timeA +"\n listOfTimesB "+ timeB.toString());
    }

    private int getTimeDifference(ArrayList<String> times, String currentTime) {
        String hhC = currentTime.substring(0,2);
        int mmC = Integer.parseInt(currentTime.substring(2,4));
        int add = 0;
        int difference = 1000;
        String [] splitTime;
        for (int i=0; i<times.size();i++){
            int dif = 1001, p=0;
            splitTime = times.get(i).split(":");
            String hh = splitTime[0];
            String mm = splitTime[1];
            if ("0".equals(hhC.substring(0,1))){
                hhC = hhC.substring(1,2);
            }
            if(hh.equals(hhC)){ //jednaki
                //provjeri minute
                if(Integer.parseInt(mm) >= mmC){
                    dif = Integer.parseInt(mm) - mmC;
                    p = i;
                }
            }
            else if(hh.equals(addHHC(hhC,add++))){
                dif  = add*60 + 60 - mmC + Integer.parseInt(mm);
                p = i;
            }
            if(dif <= difference){
                difference = dif;
                pos = p;
            }
        }
        return difference;
    }

    private String addHHC(String hhC, int i) {
        int hhCi = Integer.parseInt(hhC);
        hhCi = hhCi + i;

        return String.valueOf(hhCi);
    }

    private int getDay() {
        Calendar calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                return 2;
            case Calendar.SUNDAY:
                return 3;
            default:
                return 1;
        }
    }

    private boolean checkOption(TransportVehicle vehicle) {
        if("ALL".equals(option))
            return true;
        return option.equals(vehicle.getType());
    }

    private int findIdInList(String itemValue) {
        for(Line line : MainActivity.lines){
            if(itemValue.equals(line.getFullLineName())){
                return line.getId();
            }
        }
        return 0;
    }
}
