package bean;

/**
 * @author SuperSun
 * Extend MSG. Used to match the message get from Server
 * IF toM is " textMSG", " grouptextMSG", "ERRORTextMSG", "ERRORFileMSG", "ERRORgroupTextMSG", "ERRORgroupFileMSG",
 * then text is content or ERROR message, link is null
 * IF toM is " fileMSG", " groupfileMSG", text is the name of file, link is the link of the file
 * time is the time when Server just send this message (fommate to String)
 * sendId is the message(content) sender
 */
public class MessageMSG extends MSG{
	private String receiverId;
	private String text;
	private String link;
	private String time;
	private String senderNickName;
	

	/**
	 * Constructor
	 * @param senderId
	 * @param toM
	 * @param receiverId
	 * @param text
	 * @param link
	 * @param time
	 */
	public MessageMSG(String senderId, String toM,String receiverId, String text, String link,
			String time, String senderNickName) {
		super(senderId, toM);
		this.receiverId = receiverId;
		this.text = text;
		this.link = link;
		this.time = time;
		this.senderNickName = senderNickName;
	}

	
	public String getSenderNickName() {
		return senderNickName;
	}


	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}


	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
