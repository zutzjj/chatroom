package client;

import java.io.IOException;


import common.Response;
import common.ResponseHead;

/*
 * Client的thread类
 * 2020.3.23 zjj_zut
 * 
 */
public class ClientThread extends Thread{
	public boolean success_login = false;//登陆成功标志
	public boolean receive_all_user = false;//获取刷新用户信息标志
	public boolean success_register = false;//注册成功标志
	public boolean paw_error = false;//密码错误标志
	public boolean id_error = false;//账号错误标志
	public boolean login_clash = false;//登录冲突标志

	public String newid = null;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		ClientMethod cm = new ClientMethod();
		
		Object obj = null;
		try {
			while(!DataBuffer.exit)
			{
				obj=DataBuffer.ois.readObject();
				Response response = (Response)obj;
				ResponseHead head = response.getResponsehead();
				//分析响应头
				 switch (head) {
                 case REGISTER_SUCCESS_RESPONSE:
                 	success_register = true;
                 	newid = (String)response.getResponsebody().get("id");
                   // System.out.println(response.getResponsebody().get("id"));
                     break;
                 case LOGIN_SUCCESS_RESPONSE:
                 	System.out.println("登录成功");
                 	//用户名和id
                 	DataBuffer.username = (String)response.getResponsebody().get("name");
                 	DataBuffer.userid = (long)response.getResponsebody().get("id");
                 	//初始化本地消息和缓存消息
                 	DataBuffer.message = new CacheMessage(DataBuffer.userid);
                 	DataBuffer.localmessage = new CacheMessage(DataBuffer.userid);
                 
                 	success_login = true;
                 	
                 	break;
                 case PAW_ERROR_RESPONSE:
                 	System.out.println("密码错误");
                 	paw_error = true;
                 	break;
                 case ID_ERROR_RESPONSE:
                 	System.out.println("账号错误");
                 	id_error = true;
                 	break;
                 case LOGIN_CLASH_RESPONSE:
                 	System.out.println("账号冲突");
                 	success_login = false;
                 	login_clash = true;
                 	DataBuffer.exit=true;
                 	break;
                 case RECEIVE_MESSAGE__RESPONSE:
                 	//收到消息
                	 String id = (String)response.getResponsebody().get("id");
                	 String message = (String)response.getResponsebody().get("message");
                	 cm.saveMessage(id, message);
                	 
                	 cm.saveLocalMessage(id, message);
                	 
                 	break;
                 case REFRESH_ALL_USER_RESPONSE:
                 //刷新用户列表
                 	cm.receiveAllUser(response.getResponsebody());
                 	receive_all_user = true;
                 	break;
                 	
                 default:
                     break;
                   }
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("断开连接");
		}
	}
	

}
