package hawkeye.ena.ba.hawkeye.gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;

/**
 * Created by ena on 2/16/17.
 * Custom Adapter
 */

public class CustomAdapterTime extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> times;

    public CustomAdapterTime(Activity context, ArrayList<String> itemname) {
        super(context, R.layout.element_liste_time, itemname);

        this.context=context;
        this.times=itemname;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView=inflater.inflate(R.layout.element_liste_time, null,true);

        TextView time = (TextView) rowView.findViewById(R.id.time);
        time.setText(times.get(position));

        return rowView;
    }
}
