package hawkeye.ena.ba.hawkeye.gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;

/**
 * Created by ena on 8/30/17.
 *
 */

public class CustomAdapterArrivals  extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> linije;
    private final ArrayList<String> vrijemeA;
    private final ArrayList<String> vrijemeB;
    private Integer[] iconsList;


    public CustomAdapterArrivals(Activity context, ArrayList<String> itemname, ArrayList<String> listOfTimesA, ArrayList<String> listOfTimesB, Integer[] iconsList) {
        super(context, R.layout.element_liste_arrivals, itemname);

        this.context = context;
        this.linije = itemname;
        this.vrijemeA = listOfTimesA;
        this.vrijemeB = listOfTimesB;
        this.iconsList = iconsList;

    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.element_liste_arrivals, null, true);


        TextView linijaPr = (TextView) rowView.findViewById(R.id.linijaPr);
        TextView vrijemeDo1 = (TextView) rowView.findViewById(R.id.vrijemeDolSt1);
        TextView vrijemeDo2 = (TextView) rowView.findViewById(R.id.vrijemeDolSt2);
        TextView ImeDolSt1 = (TextView) rowView.findViewById(R.id.ImeDolSt1);
        TextView ImeDolSt2 = (TextView) rowView.findViewById(R.id.ImeDolSt2);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

        icon.setImageResource(iconsList[position]);
        linijaPr.setText(linije.get(position));

        String[] splitStringsArray = linije.get(position).split("-");
        String text = rowView.getResources().getString(R.string.tekstOd) +" " + splitStringsArray[0];
        ImeDolSt1.setText(text);
        text = rowView.getResources().getString(R.string.tekstDo) +" " + splitStringsArray[1];
        ImeDolSt2.setText(text);

        text = rowView.getResources().getString(R.string.polazi_u) + vrijemeA.get(position);
        vrijemeDo1.setText(text);
        text = rowView.getResources().getString(R.string.polazi_u) + vrijemeB.get(position) ;
        vrijemeDo2.setText(text);

        return rowView;
    }
}