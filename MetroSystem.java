package metroproject;
import java.util.ArrayList;
import java.util.List;

public class MetroSystem {

String startStation;
String endStation;
String tripType;
String travelTime;

List<Passenger> passengers = new ArrayList<>();

public MetroSystem(String start,String end,String trip,String time){
this.startStation=start;
this.endStation=end;
this.tripType=trip;
this.travelTime=time;
}

public void addPassenger(Passenger p){
passengers.add(p);
}

public int getPassengerCount(){
return passengers.size();
}

}