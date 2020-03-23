package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/*
  * 公有类 请求对象类（请求头||请求体(map))
 * 2020.3.23 zjj_zut
 * 
 */

public class Request implements Serializable{
	

	private static final long serialVersionUID = -6992146755955409337L;
	private RequestHead requesthead;
	private Map<String, Object> requestbody;
	public Request() {
		super();
		// TODO Auto-generated constructor stub
		this.requestbody = new HashMap<String, Object>();
	}
	public RequestHead getRequesthead() {
		return requesthead;
	}
	public void setRequesthead(RequestHead requesthead) {
		this.requesthead = requesthead;
	}
	public Map<String, Object> getRequestbody() {
		return this.requestbody;
	}
	public void setRequestbody(String body, Object object) {
		this.requestbody.put(body, object);
	}
	public void removeRequestbody(String body)
	{
		this.requestbody.remove(body);
	}
	public void cleanRequestbody()
	{
		this.requestbody.clear();
	}
	@Override
	public String toString() {
		return "Request [requesthead=" + requesthead + ", requestbody=" + requestbody + "]";
	}
	
	

}
