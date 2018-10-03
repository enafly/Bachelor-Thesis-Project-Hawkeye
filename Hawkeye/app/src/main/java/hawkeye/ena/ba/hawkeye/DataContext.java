package hawkeye.ena.ba.hawkeye;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.model.Line;
import hawkeye.ena.ba.hawkeye.model.Station;

/**
 * Created by ena on 8/14/17.
 *
 */

public class DataContext {
    ArrayList<Station> stations;
    ArrayList<Line> lines;
    ArrayList<Line> allLinesWithType;

    public DataContext() {
        stations = new ArrayList<>();
        lines = new ArrayList<>();
        allLinesWithType = new ArrayList<>();
    }

    public Line getLineById(int id) {
        for (Line line : lines){
            if(line.getId()==id){
                return line;
            }
        }
        return null;
    }

    public Station getStationById(String stationId) {
        for (Station station : stations){
            if(stationId.equals(station.getId())){
                return station;
            }
        }
        return null;
    }

    public void setAllLinesWithTypes(String[] typesOfVehicles) {
        allLinesWithType = new ArrayList<>();
        for(int i=0; i<typesOfVehicles.length; i++){
            for (Line line: lines){
                if(line.getTypeOfTransportVehicle().equals(typesOfVehicles[i])){
                    allLinesWithType.add(line);
                }
            }
        }
    }

    public String getTypeOfVehicle(String type) {
        for (Line line: allLinesWithType){
            if(type.equals(line.getLine())){
                return line.getTypeOfTransportVehicle();
            }
        }
        return "";
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }
}
