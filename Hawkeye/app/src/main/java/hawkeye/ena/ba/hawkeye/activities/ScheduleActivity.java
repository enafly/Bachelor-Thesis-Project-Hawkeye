package hawkeye.ena.ba.hawkeye.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;
import hawkeye.ena.ba.hawkeye.gui.CustomAdapterTime;
import hawkeye.ena.ba.hawkeye.model.Line;

public class ScheduleActivity extends Activity {

    String name;
    int id;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        TextView textViewName = (TextView) findViewById(R.id.title);

        Intent getPositionIntent = getIntent();
        name = getPositionIntent.getStringExtra("Name");
        textViewName.setText(name);
        textViewName.setTextColor(getPositionIntent.getIntExtra("Color",0));

        id = getPositionIntent.getIntExtra("ID", 0);
        Line line = MainActivity.dataContext.getLineById(id);
        ListView listFrom = (ListView) findViewById(R.id.listFrom);

        CustomAdapterTime customAdapterTimesFrom = new CustomAdapterTime(this, addTimes(line.getLineFrom(),line.getTimesFromRD()));
        listFrom.setAdapter(customAdapterTimesFrom);

        ListView listTo = (ListView) findViewById(R.id.listTo);
        CustomAdapterTime customAdapterTimesTo = new CustomAdapterTime(this, addTimes(line.getLineTo(),line.getTimesToRD()));
        listTo.setAdapter(customAdapterTimesTo);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private ArrayList<String> addTimes(String direction, ArrayList<String> times) {
        ArrayList<String> combine = new ArrayList<>();
        combine.add(direction);
        for(String time: times){
            combine.add(time);
        }
        return combine;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Scheduler Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
