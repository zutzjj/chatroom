package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
  * 公有类 响应对象类（响应头||响应体(map))
 * 2020.3.23 zjj_zut
 * 
 */


public class Response implements Serializable{
	
	
	private static final long serialVersionUID = 4680525873650298893L;
	private ResponseHead responsehead;
	private Map<String, Object> responsebody;
	public Response() {
		
		// TODO Auto-generated constructor stub
		
		this.responsebody = new HashMap<String, Object>();
	}
	public ResponseHead getResponsehead() {
		return responsehead;
	}
	public void setResponsehead(ResponseHead responsehead) {
		this.responsehead = responsehead;
	}
	public Map<String, Object> getResponsebody() {
		return responsebody;
	}
	public void setResponsebody(String key, Object object) {
		this.responsebody.put(key, object);
	}
	

	public void removeResponsebody(String key) {
		this.responsebody.remove(key);
	}
	public void cleanResponsebody(String key, Object object) {
		this.responsebody.clear();
	}

}
