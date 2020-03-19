package chatclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import chatcommon.model.ReceiveMessage;
/*
 * Client的方法类
 * 2020.3.17 zjj_zut
 * 
 */
public class ClientMethod {

	private Socket s;
	
	public ClientMethod(Socket s) {
		super();
		this.s = s;
	}
	//发送请求
	public void sentMessage(String st)
	{
		BufferedWriter bw;
		
        try {
        	
        	bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        	
            InputStream is = new ByteArrayInputStream(st.getBytes());
            
            BufferedReader br =new BufferedReader(new InputStreamReader(is,"UTF-8"));

            String str = br.readLine();
            
            bw.write(str);

            bw.write("\n");

            bw.flush();

       
    }catch(Exception e){
        System.out.println("服务器异常");
    }
	}
	//获得刷新用户
	public void getAllUser(String[] str)
	{
		int t = 1;
		int i=0, j=0;
		
		ClientMain.all_user = new String[ClientMain.ct.getAll_user_num()][3];
		for( i = 0; i <ClientMain.ct.getAll_user_num(); i++)
		{
			for( j = 0; j < 3; j++)
			{
				ClientMain.all_user[i][j] = str[t];
				//System.out.println(ClientMain.all_user[i][j]);
				t++;
			}
		}
		
	}
	//保存缓存信息
	public void saveUserMessage(String[] str)
	{
		boolean exist =false;//判断信息来源用户本地是否存在
		
		for(int i=0; i<ClientMain.ct.getReceive_message().size();i++)
	    {
	    	if(ClientMain.ct.getReceive_message().get(i).getUid().equals(str[1]))
	    	{
	    			ClientMain.ct.getReceive_message().get(i).setMessage(str[3]);
	    			ClientMain.ct.getLocal_receive_message().get(i).setMessage(
	    					ClientMain.ct.getLocal_receive_message().get(i).getMessage()
	    					+ClientMain.ct.getLocal_receive_message().get(i).getUname()
	    					+":"+str[3]+"\n");
	    			exist = true;
	    	}
	    }
		
    	
    	if(exist == false)
    	{
    		ReceiveMessage rm = new ReceiveMessage(str[1],str[2],str[3]);
    		ReceiveMessage lrm = new ReceiveMessage(str[1],str[2],str[2]+":"+str[3]+"\n");
    		ClientMain.ct.getReceive_message().add(rm);
    		ClientMain.ct.getLocal_receive_message().add(lrm);
    		exist = true;
    	}
	}
	//关闭线程
	public void closeThread()
	{
		 try {
         	    ClientMain.ct.stop();//不推荐使用stop,但线程中的readline()用一直堵塞
				s.close();
				ClientMain.display.dispose();
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ClientMain.display.dispose();
	}

	
}
