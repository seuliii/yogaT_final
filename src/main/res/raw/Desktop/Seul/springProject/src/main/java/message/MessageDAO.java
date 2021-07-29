package message;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageDAO {

	@Autowired
	PageVO pageVO;
	private static SqlSessionFactory sqlMapper = null;

	
	public static SqlSessionFactory getInstance() {
		if (sqlMapper == null) {
			try {
				String resource = "sqlMapConfig.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;
	}
	
	public List<MessageVO> selectAllArticles(int firstRow, int endRow){
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		endRow = endRow-firstRow+1;
		firstRow = firstRow-1;
		
		pageVO.setFirstRow(firstRow);
		pageVO.setEndRow(endRow);
		List<MessageVO> messageList = session.selectList("mapper.message.selectAllMessages",pageVO);
		session.close();
		
		
		return messageList;
	}
	
	public int selectCount() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int count = session.selectOne("mapper.message.countMessage");
		session.close();
		return count;
	}
	

	public void insertMessage(MessageVO messageVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		session.insert("mapper.message.insertMessage",messageVO);
		session.commit();
	}
	
	public void deleteMessage(int id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		session.delete("mapper.message.deleteMessage",id);
		session.commit();
		
	}
	
	public MessageVO selectMessage(int id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		MessageVO messageVO = session.selectOne("mapper.message.selectMessage",id);
		session.close();
		return messageVO;
	}
}
