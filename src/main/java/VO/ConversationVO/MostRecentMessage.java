package VO.ConversationVO;

import lombok.Data;

@Data
public class MostRecentMessage{
	private int userId;
	private String content;
	private long timestamp;

}
