package client;

import java.util.HashMap;
import java.util.Map;
/*
 * Client的消息类
 * 2020.3.23 zjj_zut
 * 
 */
public class CacheMessage {
	private long id;
	private Map<String,String> messagemap;
	public CacheMessage(long id) {
		super();
		this.id = id;
		this.messagemap = new HashMap();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Map<String, String> getMessagemap() {
		return messagemap;
	}
	public void setMessagemap(String id, String message) {
		this.messagemap.put(id, message);
	}
	public void cleanCache (String id)
	{
		this.messagemap.put(id, null);
	}

}
