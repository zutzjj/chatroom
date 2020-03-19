package chatcommon.model;
/*
  * 公有类 用于客户端缓存信息
 * 2020.3.17 zjj_zut
 * 
 */
public class ReceiveMessage {

	private String uid;
	private String uname;
	private String message;
	public ReceiveMessage(String uid, String uname, String message) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.message = message;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
