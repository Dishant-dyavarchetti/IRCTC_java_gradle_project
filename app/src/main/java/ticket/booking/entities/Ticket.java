package ticket.booking.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming (PropertyNamingStrategies.SnakeCaseStrategy.class)

public class Ticket {

    private String ticketId;
    private String userId;

    @JsonProperty("source")
    private String initialSource;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("date_of_travel")
    private String dateOfTravel;

    @JsonProperty("train")
    private Train train;

    public Ticket(){}

    public Ticket(String ticketId, String userId, String source, String destination, String dateOfTravel, Train train){
        this.ticketId = ticketId;
        this.userId = userId;
        this.initialSource = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

    public void getTicketInfo(){
        System.out.println("Ticket ID:" + ticketId);
        System.out.println("User Id: " + userId);
        System.out.println("travelling from: " + initialSource);
        System.out.println("Travelling to: " + destination);
        System.out.println("Travelling on " + dateOfTravel);
        System.out.println("Travelling in train " + train.getTrainId());
    }

    public String getTicketId(){
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSource(){
        return initialSource;
    }

    public void setSource(String source)
    {
        this.initialSource = source;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDestination(){
        return destination;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public String getDateOfTravel(){
        return dateOfTravel;
    }

    public void setDateOfTravel(String dateOfTravel){
        this.dateOfTravel = dateOfTravel;
    }

    public Train getTrain(){
        return train;
    }

    public void setTrain(Train train){
        this.train = train;
    }
}
