package hawkeye.ena.ba.hawkeye.model;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;

/**
 * Created by ena on 8/11/17.
 *
 */
public class Line {

    private int id;
    private String typeOfTransportVehicle;
    private String line;
    private String lineFrom;
    private String lineTo;
    private ArrayList<String> timesFromRD;
    private ArrayList<String> timesToRD;
    private ArrayList<String> timesFromSUB;
    private ArrayList<String> timesToSUB;
    private ArrayList<String> timesFromNEDIPR;
    private ArrayList<String> timesToNEDIPR;



    public Line(int id, ArrayList<String> stringList) {
        this.id = id;
        this.typeOfTransportVehicle = stringList.get(0);
        this.line = stringList.get(1);
        this.lineFrom = stringList.get(2);
        this.lineTo = stringList.get(3);
        getTimes(stringList);
    }

    private void getTimes(ArrayList<String> stringList) {
        int indexOfFrom = stringList.indexOf("Smjer1RD");
        int indexOfTo = stringList.indexOf("Smjer2RD");
        int indexOfFromSub = stringList.indexOf("Smjer1SUB");
        int indexOfToSub = stringList.indexOf("Smjer2SUB");
        int indexOfFromNedIPr = stringList.indexOf("Smjer1NEDIPR");
        int indexOfToNedIPr = stringList.indexOf("Smjer2NEDIPR");

        this.timesFromRD = new ArrayList<>(stringList.subList((indexOfFrom+1),indexOfTo));
        this.timesToRD = new ArrayList<>(stringList.subList((indexOfTo+1),indexOfFromSub));
        this.timesFromSUB = new ArrayList<>(stringList.subList((indexOfFromSub+1),indexOfToSub));
        this.timesToSUB = new ArrayList<>(stringList.subList((indexOfToSub+1),indexOfFromNedIPr));
        this.timesFromNEDIPR = new ArrayList<>(stringList.subList((indexOfFromNedIPr+1),indexOfToNedIPr));
        this.timesToNEDIPR = new ArrayList<>(stringList.subList((indexOfToNedIPr+1),stringList.size()));

    }

    public int getId() {        return id;    }

    public String getFullLineName(){        return line + " " + lineFrom + " - " + lineTo;    }

    public String getTypeOfTransportVehicle() {        return typeOfTransportVehicle;    }

    public String getLine() {        return line;    }

    public ArrayList<String> getTimesFromRD() {        return timesFromRD;    }

    public ArrayList<String> getTimesToRD() {        return timesToRD;    }


    public Integer getIcon(String type) {
        switch(type){
            case "A":
                return R.mipmap.bus;
            case "M":
                return R.mipmap.minibus;
            case "TR":
                return R.mipmap.trolejbus;
            case "T":
                return R.mipmap.tramvaj;
            default:
                return R.mipmap.ic_ciko;
        }
    }


    public String getLineFrom() {        return lineFrom;    }

    public String getLineTo() {        return lineTo;    }

    public ArrayList<String> getTimesFromSUB() {        return timesFromSUB;    }

    public ArrayList<String> getTimesToNEDIPR() {        return timesToNEDIPR;    }

    public ArrayList<String> getTimesFromNEDIPR() {        return timesFromNEDIPR;    }

    public ArrayList<String> getTimesToSUB() {        return timesToSUB;    }


}
