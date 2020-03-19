package chatclient;

import java.io.IOException;
import java.net.Socket;

import org.eclipse.swt.widgets.Display;

import chatclient.clientui.LoginUi;
import chatcommon.model.ClientUser;;
/*
 * Client的Main类
 * 2020.3.17 zjj_zut
 * 
 */
public class ClientMain {
	 public static ClientThread ct;
	 public static Display display;
	 public static String[][] all_user ;
	 public static String username;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
			 
			 Socket s = new Socket("localhost", 9001);//localhost为Server地址，这为本机
			 ct = new ClientThread(s);//开启线程
			 ct.start();
			 display = new Display();
			 
			 LoginUi lgui = new LoginUi(s,display);//登录界面
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	

}
