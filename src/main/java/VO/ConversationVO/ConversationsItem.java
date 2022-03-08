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

//	public String getFirstName(){
//		return firstName;
//	}
//
//	public String getLastName(){
//		return lastName;
//	}
//
//	public MostRecentMessage getMostRecentMessage(){
//		return mostRecentMessage;
//	}
//
//	public int getTotalMessages(){
//		return totalMessages;
//	}
//
//	public String getAvatar(){
//		return avatar;
//	}
//
//	public int getUserId(){
//		return userId;
//	}
}
