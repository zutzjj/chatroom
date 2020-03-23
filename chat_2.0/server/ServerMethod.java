package server;

import java.io.IOException;
import java.util.List;
import common.ClientUser;
import common.Request;
import common.Response;
import common.ResponseHead;
import common.User;

/*
 * Server的方法类
 * 2020.3.23 zjj_zut
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
	public void registerUser(Request request)
	{
		long id;
		if(!ulist.isEmpty())
		{
			id = ulist.get(ulist.size()-1).getId();
			id++;
		}
		else
			id = 10001;
		String name = (String)request.getRequestbody().get("name");
		String paw = (String)request.getRequestbody().get("paw");
		String sex = (String)request.getRequestbody().get("sex");
		User user = new User(id,name,paw,sex);
		ulist.add(user);
		
		System.out.println("添加成功"+user.toString());
		Response response = new Response();
		response.setResponsehead(ResponseHead.REGISTER_SUCCESS_RESPONSE);
		response.setResponsebody("id", "请牢记你的id:"+id);
		sendClient(response);
		
		
	}
	//发送所有用户的状态，用于刷新
	public void sendAllUserMessage()
	{
		
		Response response = new Response();
		response.setResponsehead(ResponseHead.REFRESH_ALL_USER_RESPONSE);
		for(User user:ulist)
		{
			if(user.isOnline()&&user.getId()!=cuser.getId())
			{
				
				response.setResponsebody(user.getName(),user.getId());
				
			}
		}
		
			sendClient(response);
		
		
	}
	
	//用户登录
	public int loginUser(Request request)
	{
		long id = (long)request.getRequestbody().get("id");
		String paw = (String)request.getRequestbody().get("paw");
		Response response = new Response();
		for(User user:ulist)
		{
			if(user.getId() == id)
			{
				if(paw.equals(user.getPaw()))
				{
					if(user.isOnline()==true)
					{
						loginClash(id,user);
						return 0;
					}
					response.setResponsehead(ResponseHead.LOGIN_SUCCESS_RESPONSE);
					response.setResponsebody("name", user.getName());
					response.setResponsebody("id", id);
					sendClient(response);
					System.out.println("successful");
					user.setOnline(true);
					cuser.setId(id);
					cuser.setCname(user.getName());
					sendAllUserMessage();
					return 1;
				}
				else
				{
					
					response.setResponsehead(ResponseHead.PAW_ERROR_RESPONSE);
					sendClient(response);
					return 0;
				}
					
			}
		}
		response.setResponsehead(ResponseHead.ID_ERROR_RESPONSE);
		sendClient(response);
		return 0;
	}
	//检查用户是否登录冲突
	public void loginClash(long id,User user)
	{
		for(ClientUser cuser:clist)
		{
			if(cuser.getId() == id)
			{
				try {
					Response response = new Response();
					response.setResponsehead(ResponseHead.LOGIN_CLASH_RESPONSE);
				    sendClient(response);
				    sendClientByClientUser(response,cuser);
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
	public void sendClient(Response response)
	{
		 try {
            cuser.getOos().writeObject(response); 
            cuser.getOos().flush();
           
             
         } catch (Exception e) {
             e.printStackTrace();
         }
	}
	//用于转发客户端消息
	public void sendClientToClient(Request request)
	{
		
		 try {
			    String the_id = (String) request.getRequestbody().get("id"); 
			    String uid = (String) request.getRequestbody().get("uid"); 
			    long id = Long.parseLong(the_id);
				String message = (String) request.getRequestbody().get("message");
			 for(ClientUser cuser:clist)
			 {
				 if(cuser.getId()==id)
				 {
					 Response response = new Response();
					 response.setResponsehead(ResponseHead.RECEIVE_MESSAGE__RESPONSE);
					 response.setResponsebody("id", uid);
					 
					 response.setResponsebody("message", message);
					 cuser.getOos().writeObject(response);
					 cuser.getOos().flush();
				 }
			 }
             
         } catch (Exception e) {
             e.printStackTrace();
         }
	}
	//用于发送登录冲突
	public void sendClientByClientUser(Response response,ClientUser cuser)
	{

			 try {
				cuser.getOos().writeObject(response);
				cuser.getOos().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
     
	}
	//登出
	public void logout_user(Request request)
	{
		long id = (long)request.getRequestbody().get("id");
		for(User user:ulist)
		{
			if(user.getId()==id)
			{
				user.setOnline(false);
			}
		}
		
		
	}
	public void remove(ClientUser cuser)
	{
		for(User user:ulist)
		{
			if(cuser.getId()==user.getId())
			{
				user.setOnline(false);
				System.out.println(user.toString());
			}
				
		}
		
		clist.remove(cuser);
	}

}
