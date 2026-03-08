package metroproject;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class Main {

static List<MetroSystem> bookings = new ArrayList<>();

public static void main(String[] args) throws Exception {

HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);

/* SAVE DATA */

server.createContext("/save", (HttpExchange exchange) -> {

exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

if(exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")){
exchange.sendResponseHeaders(204,-1);
return;
}

InputStream is = exchange.getRequestBody();
String data = new String(is.readAllBytes());

System.out.println("Received Booking:");
System.out.println(data);

/* SIMPLE JSON PARSING */

String start = data.split("\"start\":\"")[1].split("\"")[0];
String end = data.split("\"end\":\"")[1].split("\"")[0];
String trip = data.split("\"trip\":\"")[1].split("\"")[0];
String time = data.split("\"time\":\"")[1].split("\"")[0];

MetroSystem booking = new MetroSystem(start,end,trip,time);

/* PASSENGERS */

String namesPart = data.split("\"names\":\\[")[1].split("]")[0];

String[] names = namesPart.replace("\"","").split(",");

for(String n : names){
booking.addPassenger(new Passenger(n));
}

bookings.add(booking);

String response="Saved";

exchange.sendResponseHeaders(200,response.length());

OutputStream os = exchange.getResponseBody();
os.write(response.getBytes());
os.close();

});

/* SHOW SUMMARY */

server.createContext("/summary", (HttpExchange exchange) -> {

exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");

StringBuilder html = new StringBuilder();

html.append("<html><body style='font-family:Arial;padding:30px'>");
html.append("<h1>Metro Booking Records</h1>");

int i=1;

for(MetroSystem b : bookings){

html.append("<h3>Booking ").append(i).append("</h3>");

html.append("Start Station : ").append(b.startStation).append("<br>");
html.append("End Station : ").append(b.endStation).append("<br>");
html.append("Trip Type : ").append(b.tripType).append("<br>");
html.append("Travel Time : ").append(b.travelTime).append("<br>");

html.append("<b>Passengers</b><br>");

for(Passenger p : b.passengers){
html.append(p.getName()).append("<br>");
}

/* TRAFFIC LOGIC */

int count = b.getPassengerCount();

String traffic;

if((b.travelTime.equalsIgnoreCase("Morning") ||
    b.travelTime.equalsIgnoreCase("Evening")) && count >= 3){

traffic = "Peak Hour";

}
else if(count >= 5){

traffic = "Peak Hour";

}
else{

traffic = "Normal Traffic";

}

/* DISPLAY */

html.append("<br><b>Traffic Analysis :</b> ").append(traffic).append("<br>");

html.append("<br><hr>");

i++;
}

html.append("</body></html>");

byte[] resp = html.toString().getBytes();

exchange.sendResponseHeaders(200,resp.length);

OutputStream os = exchange.getResponseBody();
os.write(resp);
os.close();
});

server.start();

System.out.println("Server running at http://localhost:8080");

}
}