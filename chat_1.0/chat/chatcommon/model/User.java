package chatcommon.model;

import java.io.Serializable;
import java.util.List;
/*
  * 公有类 账户对象
 * 2020.3.17 zjj_zut
 * 
 */
public class User implements Serializable{
	private static final long serialVersionUID = 720853356094603462L;
	private int userid;
	private String username;
	private String userpaw;
	private String sex;
	private boolean isOnline = false;
	private List<Integer> friends_id;
	public User(int userid, String username, String userpaw, String sex) {
		super();
		this.userid = userid;
		this.username = username;
		this.userpaw = userpaw;
		this.sex = sex;
	}

	public User(int userid, String username, String userpaw, String sex, boolean isOnline, List<Integer> friends_id) {
		super();
		this.userid = userid;
		this.username = username;
		this.userpaw = userpaw;
		this.sex = sex;
		this.isOnline = isOnline;
		this.friends_id = friends_id;
	}

	public List<Integer> getFriends_id() {
		return friends_id;
	}

	public void setFriends_id(List<Integer> friends_id) {
		this.friends_id = friends_id;
	}

	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpaw() {
		return userpaw;
	}
	public void setUserpaw(String userpaw) {
		this.userpaw = userpaw;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", userpaw=" + userpaw + ", sex=" + sex
				+ ", isOnline=" + isOnline + "]";
	}
	
	
	

}
