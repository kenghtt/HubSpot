package VO.ConversationVO;

import lombok.Data;

import java.util.List;

@Data
public class Conversation{
	private List<ConversationsItem> conversations;

//	public List<ConversationsItem> getConversations(){
//		return conversations;
//	}
}
