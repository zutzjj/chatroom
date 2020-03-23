package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;

import client.ui.LoginUi;

/*
 * Client的main类
 * 2020.3.23 zjj_zut
 * 
 */
public class ClientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
			//localhost为Server地址，这为本机
			 DataBuffer.s = new Socket(DataBuffer.ip, DataBuffer.port);
			//初始化io流
			 DataBuffer.oos = new ObjectOutputStream(DataBuffer.s.getOutputStream());
			 DataBuffer.ois = new ObjectInputStream(DataBuffer.s.getInputStream());
			 //初始化在线用户列表
			 DataBuffer.all_user = new ArrayList<>();
			 //开启线程
			 DataBuffer.ct = new ClientThread();
			 DataBuffer.ct.start();
			 //开启SWT的display,所有的SWT组件共用一个display
			 DataBuffer.display = new Display();
			 //开启登录界面
			 LoginUi lgui = new LoginUi();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
