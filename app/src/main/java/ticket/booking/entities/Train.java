package ticket.booking.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder

public class Train {
//    @JsonProperty("train_id")
    private String trainId;

//    @JsonProperty("train_no")
    private String trainNo;

//     2d list of train list
//    @JsonProperty("seats")
    private List<List<Integer>> seats;

    // train city timing
//    @JsonProperty("station_time")
    private Map<String, String> stationTimes;

//    @JsonProperty("stations")
    private List<String> stations;

    public Train(){}

    public Train(String trainId, String trainNo, List<List<Integer>> seats, Map<String, String> stationTimes, List<String> stations){
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public List<String> getStations(){
        return stations;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats){
        this.seats = seats;
    }

    public String getTrainId(){
        return trainId;
    }

    public Map<String, String> getStationTimes(){
        return stationTimes;
    }

    public String getTrainNo(){
        return trainNo;
    }

    public void setTrainNo(String trainNo){
        this.trainNo = trainNo;
    }

    public void setTrainId(String trainId){
        this.trainId = trainId;
    }

    public void setStationTimes(Map<String, String> stationTimes){
        this.stationTimes = stationTimes;
    }

    public void setStations(List<String> stations){
        this.stations = stations;
    }

    public String getTrainInfo(){
        return String.format("Train ID: %s Train No: %s", trainId, trainNo);
    }
}
