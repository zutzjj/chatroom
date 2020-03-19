package chatserver;

import java.io.IOException;
import java.util.List;

import chatcommon.model.User;
import chatcommon.model.ClientUser;

/*
 * Server的线程
 * 2020.3.17 zjj_zut
 * 
 */
public class ServerThread extends Thread {
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
		
		 try {
            while (true) {//一直监听client的消息
               
                String msg= cuser.getBr().readLine();
                //System.out.println(msg);
                String[] str = msg.split("//");//消息格式为 请求头//请求体
                switch (str[0]) {
                    case "logout"://登出请求
                        sm.logout_user(cuser.getUid());
                        break;
                    case "register"://注册请求
                    	sm.registerUser(str[1],str[2],str[3]);
                        break;
                    case "login"://登录请求
                    	sm.loginUser(str[1], str[2]);
                    	break;
                    case "send"://转发消息请求
                    	sm.sendClientToClient(str[1], str[2]);
                        break;
                    case "refresh_all_user"://刷新请求
                    	sm.sendAllUserMessage();
                    	break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(cuser.getUid()+"断开");
            sm.remove(cuser);//client若断开就remove clist
        } finally {
            try {
                cuser.getBr().close();
                cuser.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
