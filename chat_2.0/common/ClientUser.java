package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/*
  * 公有类 客户端对象类
 * 2020.3.23 zjj_zut
 * 
 */
public class ClientUser {
	private String cname;
	private Socket socket;
	private long id;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public ClientUser(String cname, Socket socket) {
		super();
		this.cname = cname;
		this.socket = socket;
		
		 try {
				this.ois = new ObjectInputStream(socket.getInputStream());
				this.oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
