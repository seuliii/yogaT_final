package message;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import service.InvalidPassowrdException;
import service.MessageNotFoundException;


public class MessageService {

	@Autowired
	MessageDAO messageDAO;
	
	@Autowired
	MessageVO messageVO;

	
	public List<MessageVO> selectAllMessages(int firstRow, int endRow) {
		
		List<MessageVO> messageList = messageDAO.selectAllArticles(firstRow,endRow);
		
		return messageList;
		
	}
	
	public int countMessage() {
		int count = messageDAO.selectCount();
		return count;
	}
	
	public void insertMessage(MessageVO messageVO) {
		 messageDAO.insertMessage(messageVO);
	}
	
	public void deleteMessage(int id,String password) {
		MessageVO messageVO = messageDAO.selectMessage(id);

		if(messageVO == null){
			throw new MessageNotFoundException("메시지 없음");
		}if(!messageVO.matchPassword(password)) {
			throw new InvalidPassowrdException("bad password");
		}
		messageDAO.deleteMessage(id);
	}
	
}
