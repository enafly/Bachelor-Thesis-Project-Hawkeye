package hawkeye.ena.ba.hawkeye.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;
import hawkeye.ena.ba.hawkeye.gui.CustomAdapterOption;
import hawkeye.ena.ba.hawkeye.model.Line;

public class ListSchedulesActivity extends AppCompatActivity {

    ArrayList<String> listaLinija = new ArrayList<String>();
    Integer[] iconsList = new Integer[100];
    String option = ListActivity.option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_schedules);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton logo = (ImageButton) toolbar.findViewById(R.id.imageButton);

        logo.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });

        ListView listOfSchedules = (ListView) findViewById(R.id.listOfSchedules);
        //U odnosu na option popuni listu linija
        ArrayList<Line> lines = MainActivity.lines;
        int index= 0;
        for(int i = 0; i <lines.size(); i++){
            if(checkOption(lines.get(i))){
                listaLinija.add(lines.get(i).getFullLineName());
                iconsList[index] = lines.get(i).getIcon(lines.get(i).getTypeOfTransportVehicle());
                index++;
            }
        }
        CustomAdapterOption adapterOption = new CustomAdapterOption(this,listaLinija, iconsList);
        listOfSchedules.setAdapter(adapterOption);

        listOfSchedules.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent openSchedule= new Intent(this, AllSchedulesActivity.class);
            String itemValue = (String) listOfSchedules.getItemAtPosition(i);
            openSchedule.putExtra("ID", findIdInList(itemValue));
            openSchedule.putExtra("Name",itemValue);
            openSchedule.putExtra("Color",setColor());
            startActivity(openSchedule);
        });
    }

    private int findIdInList(String itemValue) {
        for(Line line : MainActivity.lines){
            if(itemValue.equals(line.getFullLineName())){
                return line.getId();
            }
        }
        return 0;
    }
    private boolean checkOption(Line line) {
        if("ALL".equals(option))
            return true;
        return option.equals(line.getTypeOfTransportVehicle());
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
}
