package common;

import java.io.Serializable;
/*
 * 公有类 用户对象类
 * 2020.3.23 zjj_zut
 * 
 */

public class User implements Serializable{

	private static final long serialVersionUID = 5401837601767565259L;
	

	private long id;
	private String name;
	transient private String paw;
	transient private String sex;
	transient private boolean online = false;
	public User(long id, String name, String paw, String sex) {
		super();
		this.id = id;
		this.name = name;
		this.paw = paw;
		this.sex = sex;
	}

	public User(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaw() {
		return paw;
	}

	public void setPaw(String paw) {
		this.paw = paw;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	

}
