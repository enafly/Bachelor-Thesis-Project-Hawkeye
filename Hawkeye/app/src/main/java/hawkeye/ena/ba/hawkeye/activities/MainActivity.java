package hawkeye.ena.ba.hawkeye.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import hawkeye.ena.ba.hawkeye.DataContext;
import hawkeye.ena.ba.hawkeye.R;
import hawkeye.ena.ba.hawkeye.gui.CustomAdapterOption;
import hawkeye.ena.ba.hawkeye.model.*;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    String itemValue;
    ArrayList<String> listaOpcija;

    Integer [] listaSlika= {R.mipmap.all,R.mipmap.stations};

    public static Context appContext;
    public static ArrayList<Station> stations = new ArrayList<>();
    public static ArrayList<Line> lines = new ArrayList<>();
    public static DataContext dataContext = new DataContext();
    public static boolean first = true;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista=(ListView) findViewById(R.id.mainList);
        appContext = this;
        //Lista opcija u listi
        listaOpcija = new ArrayList<>();

        listaOpcija.add(getString(R.string.raspored));
        listaOpcija.add(getString(R.string.stanice));

        CustomAdapterOption adapter = new CustomAdapterOption(this, listaOpcija, listaSlika);
        lista.setAdapter(adapter);


        lista.setOnItemClickListener((adapterView, view, i, l) -> {
            itemValue= (String) lista.getItemAtPosition(i);
            Log.i("Main","Pozicija "+itemValue+" je "+i);
            Intent openList = new Intent(this, ListActivity.class);
            openList.putExtra("List item", i);
            startActivity(openList);
        });

        if(first){
            fillwithLinesData();
            dataContext.setLines(lines);
            fillwithData();
            dataContext.setStations(stations);
            Log.i("Main onCreate:", "Popunjena lista! ");
//            first = false;
        }

    }

    private void fillwithData() {
        InputStream inputStream;
        BufferedReader bufferedReader;
        String lineOfText;
        String[] splitStringsArray;
        inputStream = getResources().openRawResource(R.raw.gtfs);
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((lineOfText = bufferedReader.readLine()) != null) {
                splitStringsArray = lineOfText.split(";");
                Log.i("AAAAAAAAA gtfs",splitStringsArray[0] +" "+splitStringsArray[1] +" "+splitStringsArray[2] +" "+splitStringsArray[3] +" "+splitStringsArray[4] +" "+splitStringsArray[5] +" "+splitStringsArray[6]);
                stations.add(new Station(splitStringsArray[0],splitStringsArray[1],splitStringsArray[2],splitStringsArray[3],splitStringsArray[4],splitStringsArray[5],splitStringsArray[6],this));

            }
        } catch (IOException e) {
            Log.e("Hawkeye", "Main - OnCreate - IOException: ", e);
        }
    }

    private void fillwithLinesData() {
        InputStream inputStream;
        BufferedReader bufferedReader;
        String lineOfText;
        String[] splitStringsArray;
        inputStream = getResources().openRawResource(R.raw.lines);
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int id = 0;
        try {
            while ((lineOfText = bufferedReader.readLine()) != null) {
                splitStringsArray = lineOfText.split(";");
                ArrayList<String> stringList = new ArrayList<>(Arrays.asList(splitStringsArray));
                lines.add(new Line(id,stringList));
                Log.i("AAAAAAAAA lines",splitStringsArray.toString());

                id++;
            }
        } catch (IOException e) {
            Log.e("Hawkeye", "Main - OnCreate - IOException: ", e);
        }
    }

    public static String getFullLineName(String type, String line){
        //kroz lines naci types i line
        for (Line oneLine : lines) {
            if(oneLine.getTypeOfTransportVehicle().equals(type) && oneLine.getLine().equals(line)){
                return oneLine.getFullLineName();
            }
        }
        Log.i("AAAAAAAAA",type+ " linija br "+line);
        return "N/A";
    }


}
