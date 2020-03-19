package chatcommon.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/*
  * 公有类 客户端对象
 * 2020.3.17 zjj_zut
 * 
 */
public class ClientUser {
	 private String name;
     private Socket socket;
     private BufferedReader br;
     private PrintWriter pw;
     private int uid;
     
	public ClientUser(String name, final Socket socket) {
		super();
		this.name = name;
		this.socket = socket;
		 try {
			this.br = new BufferedReader(new InputStreamReader(
			         socket.getInputStream()));
			this.pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(final Socket socket) {
		this.socket = socket;
	}
	public BufferedReader getBr() {
		return br;
	}
	public void setBr(BufferedReader br) {
		this.br = br;
	}
	public PrintWriter getPw() {
		return pw;
	}
	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}
     
}
