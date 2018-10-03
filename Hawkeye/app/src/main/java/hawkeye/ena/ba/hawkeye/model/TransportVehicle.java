package hawkeye.ena.ba.hawkeye.model;

import android.content.Context;
import android.util.Log;

import hawkeye.ena.ba.hawkeye.activities.MainActivity;
import hawkeye.ena.ba.hawkeye.R;

/**
 * Created by ena on 8/7/17.
 *
 */

public class TransportVehicle {

    int id;
    private String fullName;
    private String line;
    private String type;
    private Context context;
    private Integer icon;

    TransportVehicle(int id, String type, String line, MainActivity mainActivity) {
        this.id = id;
        this.type = type;
        this.context = mainActivity;
        this.line = line;
        setFullNameAndIcon();
    }

    String getFullLineName() {
        return MainActivity.getFullLineName(type,line);
    }

    public Integer getIcon() {
        return icon;
    }

    private void setFullNameAndIcon() {
        switch(type){
            case "A":
                this.fullName = context.getResources().getString(R.string.bus);
                this.icon = R.mipmap.bus;
                break;
            case "M":
                this.fullName = context.getResources().getString(R.string.minibus);
                this.icon = R.mipmap.minibus;
                break;
            case "TR":
                this.fullName = context.getResources().getString(R.string.trolejbus);
                this.icon = R.mipmap.trolejbus;
                break;
            case "T":
                this.fullName = context.getResources().getString(R.string.tramvaj);
                this.icon = R.mipmap.tramvaj;
                break;
            default:
        }
    }

    public String getType() {        return type;    }

    public int getId() {        return id;    }

}
