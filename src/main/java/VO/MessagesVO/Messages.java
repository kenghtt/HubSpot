package VO.MessagesVO;

import lombok.Data;

import java.util.List;

@Data
public class Messages{
	private List<MessagesItem> messages;
	private int userId;
	private List<UsersItem> users;

//	public List<MessagesItem> getMessages(){
//		return messages;
//	}
//
//	public int getUserId(){
//		return userId;
//	}
//
//	public List<UsersItem> getUsers(){
//		return users;
//	}
}
