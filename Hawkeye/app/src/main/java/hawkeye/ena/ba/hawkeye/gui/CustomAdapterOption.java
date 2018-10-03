package hawkeye.ena.ba.hawkeye.gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;

/**
 * Created by Ena on 14.02.2017..
 *
 */

public class CustomAdapterOption extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String>  itemname;
    private final Integer[]imgid;

    public CustomAdapterOption(Activity context, ArrayList<String> itemname, Integer[] imgid) {
        super(context, R.layout.element_liste, itemname);

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View rowView=inflater.inflate(R.layout.element_liste, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.itemname);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(itemname.get(position));
        imageView.setImageResource(imgid[position]);
        return rowView;
    }
}
