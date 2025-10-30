package ticket.booking.entities;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)

public class User{
//    @JsonProperty("user_id")
    private String userId;

//    @JsonProperty("name")
    private String name;

//    @JsonProperty("password")
    private String password;

//    @JsonProperty("hashed_password")
    private String hashedPassword;

//    @JsonProperty("tickets_booked")
    private List<Ticket> ticketBooked;



    public User(){}

    // constructor
    public User(String userid, String na, String passwo, String hashedPassword, List<Ticket> ticketbook){
        this.userId = userid;
        this.name = na;
        this.password = passwo;
        this.hashedPassword = hashedPassword;
        this.ticketBooked = ticketbook;
    }

    // creating getter methods
    public String getUserId(){
        return userId;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getHashedPassword(){
        return hashedPassword;
    }

    public List<Ticket> getTicketBooked(){
        return ticketBooked;
    }

    // printing ticket booked
    public void printTicket(){
        if(!ticketBooked.isEmpty()){
            for (Ticket ticket : ticketBooked) {
                ticket.getTicketInfo();
            }
        }else{
            System.out.println("There is no ticket booked till yet");
        }

    }

    // setter methods
    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String pass){
        this.password = pass;
    }

    public void setHashedPassword(String hpass){
        this.hashedPassword = hpass;
    }

    public void setTicketBooked(List<Ticket> ticketb){
        this.ticketBooked = ticketb;
    }
}