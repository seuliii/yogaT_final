package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.MessageController;
import message.MessageDAO;
import message.MessageService;
import message.MessageVO;
import message.PageVO;

@Configuration
public class ControllerConfig {

	@Bean
	public MessageController messageController() {
		return new MessageController();
	}
	
	@Bean
	public MessageDAO messageDAO() {
		return new MessageDAO();
	}
	
	@Bean
	public MessageVO messageVO() {
		return new MessageVO();
	}
	
	@Bean
	public MessageService messageService() {
		return new MessageService();
	}
	@Bean
	public PageVO pageVO() {
		return new PageVO();
	}
}
