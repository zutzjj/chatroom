package server;

import java.io.IOException;
import java.util.List;

import common.ClientUser;
import common.Request;
import common.RequestHead;
import common.User;

/*
 * Server的Thread类
 * 2020.3.23 zjj_zut
 * 
 */


public class ServerThread extends Thread{
	private ClientUser cuser;
    private List<ClientUser> clist;
    private List<User> ulist;
	public ServerThread(ClientUser cuser, List<ClientUser> clist, List<User> ulist) {
		super();
		this.cuser = cuser;
		this.clist = clist;
		this.ulist = ulist;
	}
	public void run() {
		ServerMethod sm =new ServerMethod(cuser,clist,ulist);//sm 为服务器方法类
		Object obj = null;
		 try {
            while ((obj = cuser.getOis().readObject())!=null) {//一直监听client的消息
               
               Request request = (Request) obj;
               RequestHead requesthead = request.getRequesthead(); 
          
                //System.out.println();
                switch (requesthead) {
                    case LOGOUT_REQUEST://登出请求
                        sm.logout_user(request);
                        
                        break;
                    case REGISTER_REQUEST://注册请求
                    	sm.registerUser(request);
                        break;
                    case LOGIN_REQUEST://登录请求
                    	sm.loginUser(request);
                    	break;
                    case SEND_REQUEST://转发消息请求
                    	sm.sendClientToClient(request);
                        break;
                    case REFRESH_ALL_USER_REQUEST://刷新请求
                    	sm.sendAllUserMessage();
                    	break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(cuser.getCname()+"断开");
            sm.remove(cuser);//client若断开就remove clist
        } finally {
            try {
                cuser.getOis().close();
                cuser.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}



