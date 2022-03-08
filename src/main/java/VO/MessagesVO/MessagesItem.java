package VO.MessagesVO;

import lombok.Data;

@Data
public class MessagesItem{
	private int fromUserId;
	private int toUserId;
	private String content;
	private long timestamp;

//	public int getFromUserId(){
//		return fromUserId;
//	}
//
//	public int getToUserId(){
//		return toUserId;
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
