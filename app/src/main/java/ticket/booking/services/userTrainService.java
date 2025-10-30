package ticket.booking.services;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

public class userTrainService {
    private List<Train> trainList;
    private ObjectMapper objectMap = new ObjectMapper();

    private static final String trainFilePath = "app/src/main/java/ticket/booking/localDB/train.json";

//    public userTrainService(){}
    public userTrainService() throws IOException {
        File trainFile = new File(trainFilePath);
        trainList = objectMap.readValue(trainFile, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrain(String src, String destination){
        return trainList.stream().filter(train -> validTrain(train, src, destination)).collect(Collectors.toList());
    }

    public void addTrain(Train newTrain){
//        check if a train already present in the list
        Optional<Train> existingTrain = trainList.stream()
                .filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainNo()))
                .findFirst();

        if(existingTrain.isEmpty()){
            trainList.add(newTrain);
            saveListTofile();
        }else{
            updateTrain(newTrain);
        }
    }

    private void updateTrain(Train newTrain){
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(newTrain.getTrainId()))
                .findFirst();

        if(index.isPresent()){
            trainList.set(index.getAsInt(), newTrain);
            saveListTofile();
        }else{
            addTrain(newTrain);
        }
    }

    private void saveListTofile(){
        try{
            objectMap.writeValue(new File(trainFilePath), trainList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validTrain(Train train, String source, String destination){
        List<String> stationOrder = train.getStations();

        int srcIndex = stationOrder.indexOf(source.toLowerCase());
        int desIndex = stationOrder.indexOf(destination.toLowerCase());

        return srcIndex != -1 && desIndex != -1 && srcIndex < desIndex;
    }
}
