package VO.ConversationVO;

import lombok.Data;

@Data
public class ConversationsItem{
	private String firstName;
	private String lastName;
	private MostRecentMessage mostRecentMessage;
	private int totalMessages;
	private String avatar;
	private int userId;

}
