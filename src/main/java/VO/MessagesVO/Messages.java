package VO.MessagesVO;

import lombok.Data;
import java.util.List;

@Data
public class Messages{
	private List<MessagesItem> messages;
	private int userId;
	private List<UsersItem> users;
}
