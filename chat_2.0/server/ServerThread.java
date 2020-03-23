package server;

import java.io.IOException;
import java.util.List;

import common.ClientUser;
import common.Request;
import common.RequestHead;
import common.User;

/*
 * Server��Thread��
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
		ServerMethod sm =new ServerMethod(cuser,clist,ulist);//sm Ϊ������������
		Object obj = null;
		 try {
            while ((obj = cuser.getOis().readObject())!=null) {//һֱ����client����Ϣ
               
               Request request = (Request) obj;
               RequestHead requesthead = request.getRequesthead(); 
          
                //System.out.println();
                switch (requesthead) {
                    case LOGOUT_REQUEST://�ǳ�����
                        sm.logout_user(request);
                        
                        break;
                    case REGISTER_REQUEST://ע������
                    	sm.registerUser(request);
                        break;
                    case LOGIN_REQUEST://��¼����
                    	sm.loginUser(request);
                    	break;
                    case SEND_REQUEST://ת����Ϣ����
                    	sm.sendClientToClient(request);
                        break;
                    case REFRESH_ALL_USER_REQUEST://ˢ������
                    	sm.sendAllUserMessage();
                    	break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(cuser.getCname()+"�Ͽ�");
            sm.remove(cuser);//client���Ͽ���remove clist
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



