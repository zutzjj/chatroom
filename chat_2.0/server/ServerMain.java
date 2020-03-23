
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import common.ClientUser;
import common.User;
/*
 * Server的main类
 * 2020.3.23 zjj_zut
 * 
 */

public class ServerMain {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<User> ulist = new ArrayList<>();
		List<ClientUser> clist = new ArrayList<>();
		User user1 = new User(10001,"zjj1","123456","男");
		User user2 = new User(10002,"zjj2","123456","女");
		User user3 = new User(10003,"zjj3","123456","男");
		ulist.add(user1);
		ulist.add(user2);
		ulist.add(user3);
		
		
		try {
			DataBuffer.ss = new ServerSocket(9001);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("服务器启动");
		int i=0;
		try {
		while(true)
		{
		 
			Socket s = DataBuffer.ss.accept();//等待一个客户端连接，否则堵塞		
			i++;		
			ClientUser cuser = new ClientUser("client"+i,s);		
			clist.add(cuser);//将客户端加入 clist		
			System.out.println(cuser.getCname() + "正在请求");			
			ServerThread sthread = new ServerThread(cuser,clist,ulist);//开始线程监听		
			sthread.start();
		}
		
	}
	  catch (IOException e) {
            e.printStackTrace();
        }
	}

}
