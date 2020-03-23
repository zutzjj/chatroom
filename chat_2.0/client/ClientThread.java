package client;

import java.io.IOException;


import common.Response;
import common.ResponseHead;

/*
 * Client��thread��
 * 2020.3.23 zjj_zut
 * 
 */
public class ClientThread extends Thread{
	public boolean success_login = false;//��½�ɹ���־
	public boolean receive_all_user = false;//��ȡˢ���û���Ϣ��־
	public boolean success_register = false;//ע��ɹ���־
	public boolean paw_error = false;//��������־
	public boolean id_error = false;//�˺Ŵ����־
	public boolean login_clash = false;//��¼��ͻ��־

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
				//������Ӧͷ
				 switch (head) {
                 case REGISTER_SUCCESS_RESPONSE:
                 	success_register = true;
                 	newid = (String)response.getResponsebody().get("id");
                   // System.out.println(response.getResponsebody().get("id"));
                     break;
                 case LOGIN_SUCCESS_RESPONSE:
                 	System.out.println("��¼�ɹ�");
                 	//�û�����id
                 	DataBuffer.username = (String)response.getResponsebody().get("name");
                 	DataBuffer.userid = (long)response.getResponsebody().get("id");
                 	//��ʼ��������Ϣ�ͻ�����Ϣ
                 	DataBuffer.message = new CacheMessage(DataBuffer.userid);
                 	DataBuffer.localmessage = new CacheMessage(DataBuffer.userid);
                 
                 	success_login = true;
                 	
                 	break;
                 case PAW_ERROR_RESPONSE:
                 	System.out.println("�������");
                 	paw_error = true;
                 	break;
                 case ID_ERROR_RESPONSE:
                 	System.out.println("�˺Ŵ���");
                 	id_error = true;
                 	break;
                 case LOGIN_CLASH_RESPONSE:
                 	System.out.println("�˺ų�ͻ");
                 	success_login = false;
                 	login_clash = true;
                 	DataBuffer.exit=true;
                 	break;
                 case RECEIVE_MESSAGE__RESPONSE:
                 	//�յ���Ϣ
                	 String id = (String)response.getResponsebody().get("id");
                	 String message = (String)response.getResponsebody().get("message");
                	 cm.saveMessage(id, message);
                	 
                	 cm.saveLocalMessage(id, message);
                	 
                 	break;
                 case REFRESH_ALL_USER_RESPONSE:
                 //ˢ���û��б�
                 	cm.receiveAllUser(response.getResponsebody());
                 	receive_all_user = true;
                 	break;
                 	
                 default:
                     break;
                   }
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("�Ͽ�����");
		}
	}
	

}
