package message;

import java.util.List;

public class PageVO {

	private int messageTotalCount;
	private int currentPageNumber;
	private List<MessageVO> messageList;
	private int pageTotalCount;
	private int messageCountPerPage;
	private int firstRow;
	private int endRow;
	
	public PageVO() {}
	public PageVO( List<MessageVO> messageList, int messageTotalCount, int currentPageNumber,int messageCountPerPage,
			int firstRow, int endRow) {
		super();
		this.messageTotalCount = messageTotalCount;
		this.currentPageNumber = currentPageNumber;
		this.messageList = messageList;
		this.messageCountPerPage = messageCountPerPage;
		this.firstRow = firstRow;
		this.endRow = endRow;
		
		calculatePageTotalCount();
	}
	
	public int getMessageTotalCount() {
		return messageTotalCount;
	}
	public void setMessageTotalCount(int messageTotalCount) {
		this.messageTotalCount = messageTotalCount;
	}
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	public List<MessageVO> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<MessageVO> messageList) {
		this.messageList = messageList;
	}
	public int getPageTotalCount() {
		return pageTotalCount;
	}
	public void setPageTOtalCount(int pageTotalCount) {
		this.pageTotalCount = pageTotalCount;
	}

	public int getMessageCountPerPage() {
		return messageCountPerPage;
	}
	public void setMessageCountPerPage(int messageCountPerPage) {
		this.messageCountPerPage = messageCountPerPage;
	}
	public int getFirstRow() {
		return firstRow;
	}
	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	private void calculatePageTotalCount(){
		if(messageTotalCount == 0) {
			pageTotalCount = 0;
		}else {
			pageTotalCount = messageTotalCount/messageCountPerPage;
			if(messageTotalCount % messageCountPerPage >0 ) {
				pageTotalCount++;

			}
		}
	}
	
	public boolean isEmpty() {
		return messageTotalCount == 0;
	}
}
