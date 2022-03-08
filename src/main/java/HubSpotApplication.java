//import VO.person.Game;
//import VO.person.Person;
//import com.google.gson.Gson;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class HubSpotApplication {
//
//
//    private static final String GET_API_URL = "https://jsonplaceholder.typicode.com/users";
//
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        Gson gson = new Gson();
//
//        String postEndpoint = "http://localhost:8080/user";
//
//        Person person = new Person();
//        person.setFirstName("Yer");
//        person.setLastName("Thao");
//
//        Game game = new Game();
//        game.setTestTing("The Test Thinggyy");
//        game.setPlayStation("PSX");
//        game.setXBox("XBOX ONE");
//        person.setGame(game);
//
//
//
//
////        String inputJson = "{ \"name\":\"tammy133\", \"salary\":\"5000\", \"age\":\"20\" }";
//
//        var request = HttpRequest.newBuilder()
//                .uri(URI.create(postEndpoint))
//                .header("Content-Type", "application/json")
////                .POST(HttpRequest.BodyPublishers.ofString(person.toString()))
//                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(person)))
//
//                .build();
//
//
//        var client = HttpClient.newHttpClient();
//
//        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.statusCode());
//        System.out.println(response.body());
//
//
//
//    }
//}
