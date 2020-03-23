package client;

import java.io.IOException;
import java.util.Map;

import common.Request;
import common.User;

/*
 * Client的方法类
 * 2020.3.23 zjj_zut
 * 
 */
public class ClientMethod {

	public ClientMethod() {
		super();
		// TODO Auto-generated constructor stub
	}
	//发送请求
	public void sendRequest(Request request)
	{
		try {
			DataBuffer.oos.writeObject(request);
			DataBuffer.oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("服务器异常");
		}
	}
	//保存消息于本地
	public void saveLocalMessage(String id, String message)
	{   
		if(DataBuffer.localmessage.getMessagemap().containsKey(id))
	{
		StringBuilder themessage = new StringBuilder(DataBuffer.localmessage.getMessagemap().get(id)).append(message);
   	    DataBuffer.localmessage.setMessagemap(id, themessage.toString());
	}else
	{
		DataBuffer.localmessage.setMessagemap(id, message.toString());
	}
		
	}
	//保存缓存信息
	public void saveMessage(String id, String message)
	{
	
   	    DataBuffer.message.setMessagemap(id, message);
	}
	//保存并分析在线用户
	public void receiveAllUser(Map<String, Object> map)
	{
		
		int i = 0;
		
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			  i++;
		      //System.out.println(entry.getKey() + ":" + entry.getValue());
		      User user = new User((long)entry.getValue(),entry.getKey());
		      DataBuffer.all_user.add(user);
		    }
		DataBuffer.all_user_num = i;
		
	}
	//关闭线程
	public void closeThread()
	{
		 try {
			    DataBuffer.ois.close();
			    DataBuffer.oos.close();
				DataBuffer.s.close();
			    DataBuffer.exit = true;
				DataBuffer.display.dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 DataBuffer.display.dispose();
	}

}
