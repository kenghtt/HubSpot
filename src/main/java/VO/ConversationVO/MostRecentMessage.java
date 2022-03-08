package VO.ConversationVO;

import lombok.Data;

@Data
public class MostRecentMessage{
	private int userId;
	private String content;
	private long timestamp;

//	public int getUserId(){
//		return userId;
//	}
//
//	public String getContent(){
//		return content;
//	}
//
//	public long getTimestamp(){
//		return timestamp;
//	}
}
