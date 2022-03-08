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

public class HubSpotApplication {

    private static final String GET_API_URL = "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=baa4f01d038a445edb7f280f2a4d";
    private static final String POST_API_URL = "https://candidate.hubteam.com/candidateTest/v3/problem/result?userKey=baa4f01d038a445edb7f280f2a4d";
    private static final Gson gson = new Gson();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpResponse<String> response = makeGetCall();

        // Convert response to Messages Object
        Messages messagesResponse = gson.fromJson(response.body(), Messages.class);
        List<MessagesItem> messages = messagesResponse.getMessages();
        HashMap<Integer, List<MessagesItem>> messageMap = new HashMap<>();
        HashMap<Long, ConversationsItem> timesMap = new HashMap<>();

        int myUserId = messagesResponse.getUserId();

        for (MessagesItem message : messages) {
            int fromUserId = message.getFromUserId();
            int toUserId = message.getToUserId();

            // NOT SAME AS MY USER ID
            if (message.getFromUserId() != myUserId) {
                addToMessageMap(fromUserId, messageMap, message);

            } else {  // SAME AS USER ID
                addToMessageMap(toUserId, messageMap, message);
            }
        }

        List<UsersItem> users = messagesResponse.getUsers();
        List<ConversationsItem> conversations = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();

        // Iterate through users
        for (UsersItem user : users) {
            int userId = user.getId();
            List<MessagesItem> msg = messageMap.get(userId); // get access to all messages for that user

            ConversationsItem conversationsItem = new ConversationsItem();
            conversationsItem.setAvatar(user.getAvatar());
            conversationsItem.setFirstName(user.getFirstName());
            conversationsItem.setLastName(user.getLastName());
            conversationsItem.setTotalMessages(msg.size());
            conversationsItem.setUserId(userId);

            MostRecentMessage mostRecentMessage = new MostRecentMessage();
            long mostRecentTimeStamp = 0;

            // Capture most recent message from that userId
            for (MessagesItem m : msg) {
                if (mostRecentTimeStamp < m.getTimestamp()) {
                    mostRecentTimeStamp = m.getTimestamp();
                    mostRecentMessage.setUserId(m.getFromUserId());
                    mostRecentMessage.setContent(m.getContent());
                    mostRecentMessage.setTimestamp(mostRecentTimeStamp);
                }
            }
            conversationsItem.setMostRecentMessage(mostRecentMessage);

            long recentTime = conversationsItem.getMostRecentMessage().getTimestamp();
            times.add(recentTime);
            timesMap.put(recentTime, conversationsItem);
        }

        // Sort most recent time from Newest to Oldest
        Collections.sort(times);
        Collections.reverse(times);

        // Add based on most recent time
        for (int i = 0; i < times.size(); i++) {
            ConversationsItem conversationsItem = timesMap.get(times.get(i));
            conversations.add(conversationsItem);
        }

        // ADD to Conversation List
        Conversation conversation = new Conversation();
        conversation.setConversations(conversations);

        // Make Post Call
        HttpResponse<String> responsePost = makePostCall(conversation);

        System.out.println("Status Code: " + responsePost.statusCode());
        System.out.println("Response Body: " + responsePost.body());
    }

    static HttpResponse<String> makeGetCall() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(GET_API_URL))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    static HttpResponse<String> makePostCall(Conversation conversation) throws IOException, InterruptedException {
        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create(POST_API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(conversation)))
                .build();
        HttpClient clientPost = HttpClient.newHttpClient();
        return clientPost.send(requestPost, HttpResponse.BodyHandlers.ofString());
    }

    static void addToMessageMap(int userId, HashMap<Integer, List<MessagesItem>> messageMap, MessagesItem message) {
        List<MessagesItem> messageList;
        if (!messageMap.containsKey(userId)) {
            messageList = new ArrayList<>();
        } else {
            messageList = messageMap.get(userId);
        }
        messageList.add(message);
        messageMap.put(userId, messageList);
    }

}
