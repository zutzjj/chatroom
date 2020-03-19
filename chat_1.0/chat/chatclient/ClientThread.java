package chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import chatcommon.model.ReceiveMessage;
/*
 * Client的线程
 * 2020.3.17 zjj_zut
 * 
 */
public class ClientThread extends Thread{
	private Socket s;
	private boolean exit = false;//中断线程标志
	private boolean success_login = false;//登陆成功标志
	private boolean success_register = false;//注册成功标志
	private boolean receive_all_user = false;//获取刷新用户信息标志
	private List<ReceiveMessage> receive_message;//用于缓存信息
	private boolean  is_receive = false;//接受信息完成标志
	private int all_user_num = 0;//用户数
	private List<ReceiveMessage> local_receive_message;//本地信息

	public ClientThread(Socket s) {
		super();
		this.s = s;
	}
	public void run() {
		receive_message = new ArrayList<ReceiveMessage>();
		local_receive_message = new ArrayList<ReceiveMessage>();
		 ClientMethod cm = new ClientMethod(s);
		
		 try {
            InputStream inputStream = s.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            try {
            	//一直监听Server的响应
                while (!exit) { 
                    String msg=br.readLine();
                    //System.out.println(msg);
                    String[] str = msg.split("//");//响应头//响应体
                    
                    switch (str[0]) {
                        case "register_success":
                        	success_register = true;
                            System.out.println(str[1]);
                            break;
                        case "login_success":
                        	System.out.println("登录成功");
                        	ClientMain.username=str[1];//用户名
                        	success_login = true;
                        	break;
                        case "paw_error":
                        	System.out.println("密码错误");
                        	break;
                        case "id_error":
                        	System.out.println("账号错误");
                        	break;
                        case "login_clash":
                        	System.out.println("账号冲突");
                        	success_login = false;
                        	exit=true;
                        	s.close();
                        	break;
                        case "receive_message":
                        	//if(receive_message!=null && !receive_message.isEmpty())
                        	cm.saveUserMessage(str);
                        	is_receive = true;
                        	break;
                        case "send_all_user":
                        	all_user_num = ((str.length-1)/3);
                        	cm.getAllUser(str);
                            // System.out.println(all_user_num);
                        	receive_all_user = true;
                        	break;	
                        default:
                            break;
                          }
                 }
               
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		 
    }
	public boolean isReceive_all_user() {
		return receive_all_user;
	}
	public void setReceive_all_user(boolean receive_all_user) {
		this.receive_all_user = receive_all_user;
	}
	public boolean isSuccess_login() {
		return success_login;
	}
	public int getAll_user_num() {
		return all_user_num;
	}
	public void setAll_user_num(int is_online_num) {
		this.all_user_num = is_online_num;
	}
	public List<ReceiveMessage> getReceive_message() {
		return receive_message;
	}
	public void setReceive_message(List<ReceiveMessage> receive_message) {
		this.receive_message = receive_message;
	}
	public boolean is_receive() {
		return is_receive;
	}
	public List<ReceiveMessage> getLocal_receive_message() {
		return local_receive_message;
	}
	public void setLocal_receive_message(List<ReceiveMessage> local_receive_message) {
		this.local_receive_message = local_receive_message;
	}
	public void setIs_receive(boolean is_receive) {
		this.is_receive = is_receive;
	}
	public boolean isSuccess_register() {
		return success_register;
	}
	public void setSuccess_register(boolean success_register) {
		this.success_register = success_register;
	}
	
	
	
	
	

}
