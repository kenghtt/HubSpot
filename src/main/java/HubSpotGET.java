import VO.ConversationVO.Conversation;
import VO.ConversationVO.ConversationsItem;
import VO.ConversationVO.MostRecentMessage;
import VO.MessagesVO.Messages;
import VO.MessagesVO.MessagesItem;
import VO.MessagesVO.UsersItem;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class HubSpotGET {


    private static final String GET_API_URL = "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=baa4f01d038a445edb7f280f2a4d";

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(GET_API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Messages messagesResponse = gson.fromJson(response.body(), Messages.class);
        List<MessagesItem> messages = messagesResponse.getMessages();

        int lengthOfMessages = messages.size();

        HashMap<Integer, List<MessagesItem>> hashMap = new HashMap<>();
        int myUserId = messagesResponse.getUserId();


        for (int i = 0; i < lengthOfMessages; i++) {

            if (messages.get(i).getFromUserId() != myUserId) {  // NOT SAME AS MY USER ID
                if (!hashMap.containsKey(messages.get(i).getFromUserId())) {
                    List<MessagesItem> messageList = new ArrayList<>();
                    messageList.add(messages.get(i));
                    hashMap.put(messages.get(i).getFromUserId(), messageList);
                } else {
                    List<MessagesItem> messageList = hashMap.get(messages.get(i).getFromUserId());
                    messageList.add(messages.get(i));
                    hashMap.put(messages.get(i).getFromUserId(), messageList);
                }

            } else {  // SAME AS USER ID
                if (!hashMap.containsKey(messages.get(i).getToUserId())) {
                    List<MessagesItem> messageList = new ArrayList<>();
                    messageList.add(messages.get(i));
                    hashMap.put(messages.get(i).getToUserId(), messageList);
                } else {
                    List<MessagesItem> messageList = hashMap.get(messages.get(i).getToUserId());
                    messageList.add(messages.get(i));
                    hashMap.put(messages.get(i).getToUserId(), messageList);
                }
            }
        }


        List<UsersItem> users = messagesResponse.getUsers();

        List<ConversationsItem> conversations = new ArrayList<ConversationsItem>();

        HashMap<Integer, UsersItem> userMap = new HashMap<>();

        for (int j = 0; j < users.size(); j++) {
            userMap.put(users.get(j).getId(), users.get(j));
        }

        ArrayList<Long> times = new ArrayList<>();
        HashMap<Long, ConversationsItem> timesMap = new HashMap<>();

        for (Map.Entry<Integer, List<MessagesItem>> set : hashMap.entrySet()) {

            int userId = set.getKey();
            List<MessagesItem> msg = set.getValue();

            long mostRecentTimeStamp = 0;

            ConversationsItem conversationsItem = new ConversationsItem();
            conversationsItem.setAvatar(userMap.get(userId).getAvatar());
            conversationsItem.setFirstName(userMap.get(userId).getFirstName());
            conversationsItem.setLastName(userMap.get(userId).getLastName());
            conversationsItem.setTotalMessages(msg.size());


            conversationsItem.setUserId(userId);


            MostRecentMessage mostRecentMessage = new MostRecentMessage();
            for(MessagesItem m : msg){
                if( mostRecentTimeStamp < m.getTimestamp()){
                    mostRecentTimeStamp = m.getTimestamp();
                    mostRecentMessage.setUserId(m.getFromUserId());
                    mostRecentMessage.setContent(m.getContent());
                    mostRecentMessage.setTimestamp(mostRecentTimeStamp);
                }
            }

            conversationsItem.setMostRecentMessage(mostRecentMessage); // need to set this still

            long time = conversationsItem.getMostRecentMessage().getTimestamp();

            times.add(time);

            timesMap.put(time, conversationsItem);
//            conversations.add(conversationsItem);

        }



        Collections.sort(times);
        Collections.reverse(times);


        for(int i = 0; i< times.size(); i++){
            ConversationsItem conversationsItem =  timesMap.get(times.get(i));
            conversations.add(conversationsItem);

        }




        Conversation conversation = new Conversation();
        conversation.setConversations(conversations);





        String postEndpoint = "https://candidate.hubteam.com/candidateTest/v3/problem/result?userKey=baa4f01d038a445edb7f280f2a4d";

        var requestPost = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(person.toString()))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(conversation)))
                .build();


        var clientPost = HttpClient.newHttpClient();

        var responsePost = clientPost.send(requestPost, HttpResponse.BodyHandlers.ofString());

        System.out.println(responsePost.statusCode());
        System.out.println(responsePost.body());

        System.out.println("");
    }
}
