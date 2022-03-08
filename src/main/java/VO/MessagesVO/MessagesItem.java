package VO.MessagesVO;

import lombok.Data;

@Data
public class MessagesItem{
	private int fromUserId;
	private int toUserId;
	private String content;
	private long timestamp;
}
