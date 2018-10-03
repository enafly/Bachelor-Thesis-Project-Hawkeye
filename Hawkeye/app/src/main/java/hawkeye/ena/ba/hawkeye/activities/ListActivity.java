package hawkeye.ena.ba.hawkeye.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;
import hawkeye.ena.ba.hawkeye.gui.CustomAdapterOption;

public class ListActivity extends AppCompatActivity {

    ListView lista;
    String itemValue;
    ArrayList<String> listaOpcija;
    Integer [] listaSlika= new Integer[5];
    int position = 0;
    int listOption = 0;
    public static String option = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton logo = (ImageButton) toolbar.findViewById(R.id.imageButton);

        logo.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });

        lista=(ListView) findViewById(R.id.lista);

        //Lista opcija u listi
        listaOpcija = new ArrayList<>();
        Intent intent = getIntent();
        listOption = intent.getIntExtra("List item",0);

        if(listOption == 0){
            //Fill list with type of transport Vehicle
            listaOpcija.add(getString(R.string.list_item_1));
            listaOpcija.add(getString(R.string.tramvaj));
            listaOpcija.add(getString(R.string.trolejbus));
            listaOpcija.add(getString(R.string.bus));
            listaOpcija.add(getString(R.string.minibus));
            listaSlika[0] = R.mipmap.all;
            listaSlika[1] = R.mipmap.tramvaj;
            listaSlika[2] = R.mipmap.trolejbus;
            listaSlika[3] = R.mipmap.bus;
            listaSlika[4] = R.mipmap.minibus;
        }
        else{
            //Fill list with nearest stations
            listaOpcija.add(getString(R.string.najblizaStanica));
            listaOpcija.add(getString(R.string.najblizaTramStanica));
            listaOpcija.add(getString(R.string.najblizaTrolStanica));
            listaOpcija.add(getString(R.string.najblizaBusStanica));
            listaOpcija.add(getString(R.string.najblizaMinibusStanica));
            listaSlika[0] = R.mipmap.stations;
            listaSlika[1] = R.mipmap.stanica_tramvaj;
            listaSlika[2] = R.mipmap.stanica_trolejbus;
            listaSlika[3] = R.mipmap.stanica_bus;
            listaSlika[4] = R.mipmap.stanica_minibus;
        }

        CustomAdapterOption adapter = new CustomAdapterOption(this, listaOpcija, listaSlika);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener((adapterView, view, i, l) -> {
            itemValue= (String) lista.getItemAtPosition(i);
            Log.i("Main","Pozicija "+itemValue+" je "+i);
            position = i;
            clickedOnListItem();
            if (listOption==0){
                Intent schedule = new Intent(this, ListSchedulesActivity.class);
                startActivity(schedule);
            }
            else {
                Intent openMap = new Intent(this,MapsActivity.class);
                startActivity(openMap);
            }
        });
    }

    private void clickedOnListItem() {
            switch (position){
                case 0:
                    option = "ALL";
                    break;
                case 1:
                    option = "T";
                    break;
                case 2:
                    option = "TR";
                    break;
                case 3:
                    option = "A";
                    break;
                case 4:
                    option = "M";
                    break;
            }
    }
}
