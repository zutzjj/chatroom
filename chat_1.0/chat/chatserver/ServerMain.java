package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import chatcommon.model.User;
import chatcommon.model.ClientUser;
/*
 * Server的Main类
 * 2020.3.17 zjj_zut
 * 
 */
public class ServerMain {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i=0;
		List<User>  ulist = new ArrayList<User>();
		//List<Integer> user1_friends_id = new ArrayList<Integer>();
		//List<Integer> user2_friends_id = new ArrayList<Integer>();
		List<ClientUser> clist = new ArrayList<ClientUser>();
		
		User user1 = new User(1001,"zjj1","123456","男");
		User user2 = new User(1002,"zjj2","654321","女");
		User user3 = new User(1003,"zjj3","654321","女");
		User user4 = new User(1004,"zjj4","654321","女");
		User user5 = new User(1005,"zjj5","654321","女");
		User user6 = new User(1006,"zjj6","654321","女");
		/*
		 * 初始一下用户
		 */
		
		
		//user1.setFriends_id(user1_friends_id);
		//user2.setFriends_id(user2_friends_id);
		ulist.add(user1);
		ulist.add(user2);
		ulist.add(user3);
		ulist.add(user4);
		ulist.add(user5);
		ulist.add(user6);
		try {
			ServerSocket ss = new ServerSocket(9001);//端口号为 9001 
			System.out.println("服务器启动");
			while(true)
			{
			Socket s = ss.accept();//等待一个客户端连接，否则堵塞
			i++;
			ClientUser cuser = new ClientUser("client"+i,s);
			clist.add(cuser);//将客户端加入 clist
			System.out.println(cuser.getName() + "正在请求");
			ServerThread sthread = new ServerThread(cuser,clist,ulist);//开始线程监听
			sthread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
		

}
