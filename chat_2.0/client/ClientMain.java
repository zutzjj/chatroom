package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;

import client.ui.LoginUi;

/*
 * Client��main��
 * 2020.3.23 zjj_zut
 * 
 */
public class ClientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
			//localhostΪServer��ַ����Ϊ����
			 DataBuffer.s = new Socket(DataBuffer.ip, DataBuffer.port);
			//��ʼ��io��
			 DataBuffer.oos = new ObjectOutputStream(DataBuffer.s.getOutputStream());
			 DataBuffer.ois = new ObjectInputStream(DataBuffer.s.getInputStream());
			 //��ʼ�������û��б�
			 DataBuffer.all_user = new ArrayList<>();
			 //�����߳�
			 DataBuffer.ct = new ClientThread();
			 DataBuffer.ct.start();
			 //����SWT��display,���е�SWT�������һ��display
			 DataBuffer.display = new Display();
			 //������¼����
			 LoginUi lgui = new LoginUi();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
