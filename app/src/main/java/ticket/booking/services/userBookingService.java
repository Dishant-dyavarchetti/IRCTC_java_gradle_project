package ticket.booking.services;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class userBookingService {
    private User user;

    //    for the user list from the json
    private List<User> userList;

    //for serializing mapping the json with the variable
    //we are going to use object mapper
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    private static final String userPath = System.getProperty("user.dir") +
            "/app/src/main/java/ticket/booking/localDB/users.json";


    public userBookingService() throws IOException{
        loadUser();
    }

    // constructor
    public userBookingService(User user1) throws IOException {
        this.user = user1;

        loadUser();
    }

    public List<User> loadUser() throws IOException {
        File users = new File(userPath);

        // Create folder if not exists
        if (!users.getParentFile().exists()) {
            users.getParentFile().mkdirs();
        }

        // Create file if not exists
        if (!users.exists()) {
            users.createNewFile();
            objectMapper.writeValue(users, new ArrayList<User>()); // initialize with empty list
        }

        // If file is empty (size = 0 bytes), also initialize it
        if (users.length() == 0) {
            objectMapper.writeValue(users, new ArrayList<User>());
        }

        // Safe read (deserialization)
        return userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }


    public Boolean userLogin(){
        Optional<User> checkUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkpassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
//        return checkUser.isPresent();
        if(checkUser.isPresent()){
            this.user = checkUser.get();
            return true;
        }else{
            return false;
        }
    }

    public boolean signUp(User user1) {
        try{
            userList.add(user1);
            saveUserListToFile();
            return true;
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return false;
        }
    }

    // json -> object(class) = deserialization
    // object(class) -> json = serialization
    // below method does the serialization process
    private void saveUserListToFile() throws IOException{
        File userFile = new File(userPath);
        objectMapper.writeValue(userFile, userList);
    }

    public void fetchBooking(){
        try{
            loadUser();
            Optional<User> refresedUser = userList.stream()
                    .filter(u -> u.getUserId().equalsIgnoreCase(user.getUserId()))
                    .findFirst();

            if(refresedUser.isPresent()){
                refresedUser.get().printTicket();
            }else{
                System.out.println("User not found");
            }
        }catch(IOException e){
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }

    public boolean cancelBooking(String ticketId){
        try{
            Optional<User> optionalUser = userList.stream().filter(u -> u.getUserId().equalsIgnoreCase(user.getUserId()))
                    .findFirst();

            if(optionalUser.isEmpty()){
                System.out.println("User not found in the Json file.");
                return false;
            }

            User targetUser = optionalUser.get();

            boolean removed = targetUser.getTicketBooked()
                    .removeIf(ticket ->
                        ticket.getTicketId().equalsIgnoreCase(ticketId)
                    );

            if(!removed){
                System.out.println("Ticket Id not found in the user data");
                return false;
            }

            saveUserListToFile();
            System.out.println("Ticket Successfully removed from the booked tickets");
            return true;
        }
        catch(IOException e){
            return false;
        }
    }

    public List<Train> getTrain(String source, String destination){
        try{
            userTrainService trainService = new userTrainService();
            return trainService.searchTrain(source, destination);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int col, String source, String destination){
        try{
            userTrainService trainService = new userTrainService();
            List<List<Integer>> seats = train.getSeats();
            if(row >= 0 && row < seats.size() && col >= 0 && col < seats.get(row).size()){
                if(seats.get(row).get(col) == 0){
                    seats.get(row).set(col, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);


                    String ticketId = UUID.randomUUID().toString();
                    Ticket ticket = new Ticket(ticketId, user.getUserId(), source, destination, new Date().toString(), train);

//                    add to user and save
                    Optional<User> OptionalUser = userList.stream()
                            .filter(u -> u.getUserId().equalsIgnoreCase(user.getUserId()))
                            .findFirst();

                    if(OptionalUser.isPresent()){
                        User targetUser = OptionalUser.get();
                        if(targetUser.getTicketBooked() == null){
                            targetUser.setTicketBooked(new ArrayList<>());
                        }
                        targetUser.getTicketBooked().add(ticket);
                        saveUserListToFile();
                    }

                    return true;

                }else{
                    return false;
                }
            }else{
                return false;
            }

        } catch (IOException e) {
            return false;
        }
    }
}
