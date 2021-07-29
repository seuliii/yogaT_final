package controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import message.MessageDAO;
import message.MessageService;
import message.MessageVO;
import message.PageVO;
import service.InvalidPassowrdException;
import service.MessageNotFoundException;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	MessageService messageService;

	@Autowired
	MessageVO messageVO;

	@Autowired
	PageVO pageVO;
	private static final int MESSAGE_COUNT_PER_PAGE = 3;
	
	
	@RequestMapping("/listMessages.do")
	public String getMessageList(Model model,HttpServletRequest request) {
	
		String pageNumberStr = request.getParameter("pageNum");
		int pageNum = 1;
		if(pageNumberStr != null) {
			pageNum = Integer.parseInt(pageNumberStr);
		}
		
		int currentPageNumber = pageNum;
		
		int messageTotalCount = messageService.countMessage();
		List<MessageVO> messageList = null;
		int firstRow = 0;
		int endRow = 0;
		
		if(messageTotalCount >0) {
			firstRow = (pageNum -1) * MESSAGE_COUNT_PER_PAGE +1;
			endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
			
			messageList = messageService.selectAllMessages(firstRow, endRow);
		}else {
			currentPageNumber = 0;
			messageList = Collections.emptyList();
		}
		pageVO = 
				new PageVO(messageList, messageTotalCount, currentPageNumber, MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("messageList", messageList);
		
		return "MessageListView";

	}
	
	@RequestMapping(value="/writeMessage.do", method = RequestMethod.POST)
	public String writeMessage(@RequestParam(value="guestName")String guestName,@RequestParam(value="password")String password,
			@RequestParam(value="message")String message) {
		messageVO.setGuestName(guestName);
		messageVO.setMessage(message);
		messageVO.setPassword(password);
		messageService.insertMessage(messageVO);
		return "redirect:listMessages.do";
	}
	
	@RequestMapping(value="/deleteMessage.do", method=RequestMethod.POST)
	public String deleteMessage(@RequestParam(value="id") String idstr,@RequestParam(value="password")String password,
												Model model) {
		
		int id = Integer.parseInt(idstr);
		boolean invalidPassword = false;
		try {
			messageService.deleteMessage(id,password);
		}catch (InvalidPassowrdException e) {
			invalidPassword = true;
		}
		
		model.addAttribute("invalidPassword", invalidPassword);
		
		return "deleteMessage";
	}
}
