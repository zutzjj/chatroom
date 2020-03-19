package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import chatcommon.model.User;
import chatcommon.model.ClientUser;
/*
 * Server的方法类
 * 2020.3.17 zjj_zut
 * 
 */
public class ServerMethod {
	private ClientUser cuser;
	private List<ClientUser> clist;
	private List<User> ulist;
	public ServerMethod(ClientUser cuser, List<ClientUser> clist, List<User> ulist) {
		super();
		this.cuser = cuser;
		this.clist = clist;
		this.ulist = ulist;
	}
	//注册新用户
	public void registerUser(String name, String paw, String sex)
	{
		int id = ulist.get(ulist.size()-1).getUserid();
		id++;
		User user = new User(id,name,paw,sex);
		ulist.add(user);
		
		//System.out.println("添加成功"+user.toString());
		sendClient("register_success//"+"注册成功你的账号为:"+id+"请牢记!");
		
	}
	//发送所有用户的状态，用于刷新
	public void sendAllUserMessage()
	{
		String st = "send_all_user//";
		for(User user:ulist)
		{
			if(user.getUserid()!=cuser.getUid())
			{
				st+=user.getUserid()+"//"+user.getUsername()+"//"+user.isOnline()+"//";
			}
		}
		//System.out.println(st);
		sendClient(st);
	}
	//用户登录
	public int loginUser(String id, String paw)
	{
		int uid = Integer.parseInt(id); 
		for(User user:ulist)
		{
			if(user.getUserid()==uid)
			{
				if(user.getUserpaw().equals(paw))
				{
					if(user.isOnline()==true)
					{
						loginClash(uid,user);
						return 0;
					}
					sendClient("login_success//"+user.getUsername()+"//");
					user.setOnline(true);
					cuser.setUid(uid);
					cuser.setName(user.getUsername());
					//System.out.println(user.toString());
					sendAllUserMessage();
					return 1;
				}
				else
				{
					sendClient("paw_error//");
					return 0;
				}
					
			}
		}
		sendClient("id_error//");
		return 0;
	}
	//检查用户是否登录冲突
	public void loginClash(int uid,User user)
	{
		for(ClientUser cuser:clist)
		{
			if(cuser.getUid()==uid)
			{
				try {
				    sendClient("login_clash//");
				    sendClientByClientUser("login_clash//",cuser);
					user.setOnline(false);
					cuser.getSocket().close();
					this.cuser.getSocket().close();
					remove(cuser);
					remove(this.cuser);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	//给client发送响应
	public void sendClient(String message)
	{
		 try {
             PrintWriter pw =cuser.getPw();
             pw.println(message);
             pw.flush();
             
         } catch (Exception e) {
             e.printStackTrace();
         }
	}
	//用于转发客户端消息
	public void sendClientToClient(String uid,String message)
	{
		int id = Integer.parseInt(uid); 
		
		 try {
			 for(ClientUser cuser:clist)
			 {
				 if(cuser.getUid()==id)
				 {
					 PrintWriter pw =cuser.getPw();
		             pw.println("receive_message//"+this.cuser.getUid()+"//"+this.cuser.getName()+"//"+message);
		             pw.flush();
				 }
			 }
             
         } catch (Exception e) {
             e.printStackTrace();
         }
	}
	//用于发送登录冲突
	public void sendClientByClientUser(String message,ClientUser cuser)
	{
		 try {
             PrintWriter pw =cuser.getPw();
             pw.println(message);
             pw.flush();
             
         } catch (Exception e) {
             e.printStackTrace();
         }
	}
	//登出
	public void logout_user(int uid)
	{
		for(User user:ulist)
		{
			if(user.getUserid()==uid)
			{
				user.setOnline(false);
			}
		}
	}
	public void remove(ClientUser cuser)
	{
		for(User user:ulist)
		{
			if(cuser.getUid()==user.getUserid())
			{
				user.setOnline(false);
				System.out.println(user.toString());
			}
				
		}
		
		clist.remove(cuser);
	}

}
